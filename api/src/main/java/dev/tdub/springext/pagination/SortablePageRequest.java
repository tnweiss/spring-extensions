package dev.tdub.springext.pagination;

import java.util.Map;
import java.util.Set;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;

public interface SortablePageRequest extends PageRequest {
  Set<PageOrder> getSort();

  Order[] sort(CriteriaBuilder criteriaBuilder, Root<?> root, Map<String, String> keyMappings);

  Pageable toPageable(Map<String, String> keyMappings);
}
