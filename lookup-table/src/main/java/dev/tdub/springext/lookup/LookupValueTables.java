package dev.tdub.springext.lookup;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import dev.tdub.springext.auth.UserRoleService;
import dev.tdub.springext.error.exceptions.AuthorizationException;
import dev.tdub.springext.pagination.PageResponseDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LookupValueTables {
  private final UserRoleService userRoleService;

  public <DAO> LookupValueProvider likeCaseInsensitive(EntityManager em, Class<DAO> clazz, String column, boolean requiresAdmin) {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    Map<String, String> keyMappings = Map.of("value", column);

    return (principal, filter, pagination) -> {
      if (requiresAdmin && !userRoleService.isAdmin(principal)) {
        throw new AuthorizationException("Caller is not authorized to access table.");
      }

      CriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);
      Root<DAO> root = query.from(clazz);
      query.select(root.get(column));
      query.distinct(true);

      if (filter != null) {
        query.where(criteriaBuilder.like(criteriaBuilder.lower(root.get(column)), "%" + filter.toLowerCase() + "%"));
      }

      Order[] sort = pagination.sort(criteriaBuilder, root, keyMappings);
      if (sort.length != 0) {
        query.orderBy(sort);
      } else {
        query.orderBy(criteriaBuilder.asc(root.get(column)));
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
}
