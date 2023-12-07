package dev.tylerweiss.springext.pagination;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
public class SortablePageRequestDto extends PageRequestDto implements SortablePageRequest {
  private final Set<PageOrder> sort;

  @JsonCreator
  public SortablePageRequestDto(@JsonProperty("page") Integer page,
                                @JsonProperty("pageSize") Integer pageSize,
                                @JsonProperty("sort") List<String> sort) {
    super(page, pageSize);

    if (sort == null) {
      this.sort = Set.of();
    } else {
      this.sort = sort.stream()
          .map(PageOrder::new)
          .collect(Collectors.toSet());
    }
  }

  public Order[] sort(CriteriaBuilder criteriaBuilder, Root<?> root, Map<String, String> keyMappings) {
    return sort.stream()
        .map(o -> o.order(criteriaBuilder, root, keyMappings))
        .toArray(Order[]::new);
  }

  @Override
  public Pageable toPageable(Map<String, String> keyMappings) {
    Sort.Order[] orders = sort.stream()
        .map(s -> s.toSortOrder(keyMappings))
        .toArray(Sort.Order[]::new);

    return org.springframework.data.domain.PageRequest.of(
        getPage(), getPageSize(), Sort.by(orders)
    );
  }
}
