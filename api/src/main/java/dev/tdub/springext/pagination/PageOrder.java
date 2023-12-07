package dev.tdub.springext.pagination;

import java.util.Map;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Sort;

public interface PageOrder {
  Order order(CriteriaBuilder builder, Root<?> root);
  Order order(CriteriaBuilder builder, Root<?> root, Map<String, String> keyMappings);
  Sort.Order toSortOrder(Map<String, String> keyMappings);
}
