package dev.tdub.springext.pagination;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public interface SortablePageRequest extends PageRequest {
  Set<PageOrder> getSort();

  Order[] sort(CriteriaBuilder criteriaBuilder, Root<?> root, Map<String, String> keyMappings);

  <T> Order[] sort(CriteriaBuilder criteriaBuilder, Function<String, Expression<T>> rootAccessor, Map<String, String> keyMappings);

  Pageable toPageable(Map<String, String> keyMappings);
}
