package dev.tdub.springext.pagination;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
          .map(PageOrderDto::new)
          .collect(Collectors.toSet());
    }
  }

  public Order[] sort(CriteriaBuilder criteriaBuilder, Root<?> root, Map<String, String> keyMappings) {
    return sort.stream()
        .map(o -> o.order(criteriaBuilder, root, keyMappings))
        .toArray(Order[]::new);
  }

  @Override
  public <T> Order[] sort(CriteriaBuilder criteriaBuilder, Function<String, Expression<T>> rootAccessor, Map<String, String> keyMappings) {
    return sort.stream()
        .map(o -> o.order(criteriaBuilder, rootAccessor, keyMappings))
        .toArray(Order[]::new);
  }

  @Override
  public Pageable toPageable(Map<String, String> keyMappings) {
    return org.springframework.data.domain.PageRequest.of(
        getPage(), getPageSize(), Sort.by(toSortOrders(keyMappings))
    );
  }

  @Override
  public Pageable toPageable(Map<String, String> keyMappings, PageOrder defaultOrder) {
    Sort.Order[] orders = toSortOrders(keyMappings);

    if (orders.length == 0) {
      orders = new Sort.Order[]{defaultOrder.toSortOrder(keyMappings)};
    }

    return org.springframework.data.domain.PageRequest.of(
        getPage(), getPageSize(), Sort.by(orders)
    );
  }

  private Sort.Order[] toSortOrders(Map<String, String> keyMappings) {
    return sort.stream()
        .map(s -> s.toSortOrder(keyMappings))
        .toArray(Sort.Order[]::new);
  }
}
