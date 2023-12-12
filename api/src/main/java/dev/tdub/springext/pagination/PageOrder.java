package dev.tdub.springext.pagination;

import java.util.Map;
import java.util.function.Function;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Sort;

public interface PageOrder {
  Order order(CriteriaBuilder builder, Root<?> root);
  Order order(CriteriaBuilder builder, Root<?> root, Map<String, String> keyMappings);
  <T> Order order(CriteriaBuilder builder, Function<String, Expression<T>> expressionAccessor, Map<String, String> keyMappings);
  Sort.Order toSortOrder(Map<String, String> keyMappings);
}
