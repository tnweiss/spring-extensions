package com.ora.web.common.lookup;

import java.util.List;
import java.util.Map;

import dev.tdub.springext.error.exceptions.AuthorizationException;
import dev.tdub.springext.pagination.PageResponseDto;
import com.ora.web.service.UserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LookupValueTables {
  private final UserService userService;

  public <DAO> LookupValueProvider likeCaseInsensitive(EntityManager em, Class<DAO> clazz, String column, boolean requiresAdmin) {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    Map<String, String> keyMappings = Map.of("value", column);

    return (principal, filter, pagination) -> {
      if (requiresAdmin && !userService.isAdmin(principal.getUserId())) {
        throw new AuthorizationException("Caller is not authorized to access table.");
      }

      CriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);
      Root<DAO> root = query.from(clazz);
      query.select(root.get(column));
      query.distinct(true);
      query.orderBy(pagination.sort(criteriaBuilder, root, keyMappings));

      if (filter != null) {
        query.where(criteriaBuilder.like(criteriaBuilder.lower(root.get(column)), "%" + filter.toLowerCase() + "%"));
      }

      List<String> results = em.createQuery(query)
          .setMaxResults(pagination.getPageSize())
          .setFirstResult(pagination.getPageSize() * pagination.getPage())
          .getResultList();

      if (pagination.getPage() == 0 && results.size() < pagination.getPageSize()) {
        return new PageResponseDto<>(results, pagination);
      }

      Long totalCount = totalCount(em, criteriaBuilder, clazz, column);
      return new PageResponseDto<>(results, pagination, totalCount);
    };
  }

  private <DAO> Long totalCount(EntityManager entityManager,
                                      CriteriaBuilder criteriaBuilder,
                                      Class<DAO> clazz,
                                      String column) {
    CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
    Root<DAO> root = query.from(clazz);
    query.distinct(true);
    query.select(criteriaBuilder.countDistinct(root.get(column)));
    return entityManager.createQuery(query).getResultList().get(0);
  }

//  public static <DAO> LookupValueProvider likeCaseInsensitive(EntityManager em, Class<?> clazz, String column, boolean requiresAdmin) {
//    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//    CriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);
//    Root<?> root = query.from(clazz);
//    CriteriaQuery<?> criteriaQuery = query.select(root.get(column));
//
//    return (principal, filter, pageable) -> {
//
//      query.select(root.get(column))
//          .where(criteriaBuilder.like(criteriaBuilder.lower(root.get(column)), "%" + filter + "%"));
//      return new PageImpl<>(em.createQuery(query).setMaxResults(pageable.getPageSize()).setFirstResult(pageable.getPageSize() * pageable.getPageNumber()).getResultList());
//    };
//  }

//  public static <DAO> LookupValueProvider likeCaseInsensitive(EntityManager em, String column, boolean requiresAdmin) {
//    return (principal, filter, pageable) -> {
//      CriteriaQuery<String> q = em.getCriteriaBuilder().createQuery(String.class);
//      Root<UserDao> c = q.from(UserDao.class);
//      q.select(c.get("column")).where()
//
//      Specification<DAO> spec = (root, query, criteriaBuilder) -> query.select(root.get(column)).where(criteriaBuilder.like(criteriaBuilder.lower(root.get(column)), "%" + filter + "%"));
//      return
//    }
//  }
}
