package dev.tdub.springext.pagination;

import dev.tdub.springext.error.exceptions.ClientException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.domain.Sort;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
public class PageOrderDto implements PageOrder {
  private static final Predicate<String> SORT_FORMAT =
      Pattern.compile("^[a-zA-Z0-9]+,(ASC|DESC)$").asMatchPredicate();

  private final String column;
  private final PageOrderDirection direction;

  public PageOrderDto(String sortString) {
    if (!SORT_FORMAT.test(sortString)) {
      throw new ClientException("Sort must be formatted as '<COLUMN>,(ASC || DESC)'. Not '%s'".formatted(sortString));
    }

    String[] sortParameters = sortString.split(",");
    this.column = sortParameters[0];
    this.direction = PageOrderDirection.from(sortParameters[1]);
  }

  @Override
  public Order order(CriteriaBuilder builder, Root<?> root) {
    return direction == PageOrderDirection.ASC ? builder.asc(root.get(column)) : builder.desc(root.get(column));
  }

  @Override
  public Order order(CriteriaBuilder builder, Root<?> root, Map<String, String> keyMappings) {
    String dbColumn = getDbColumn(keyMappings);
    return sort().apply(builder, root.get(dbColumn));
  }

  @Override
  public <T> Order order(CriteriaBuilder builder, Function<String, Expression<T>> expressionAccessor,
                         Map<String, String> keyMappings) {
    String dbColumn = getDbColumn(keyMappings);
    return sort().apply(builder, expressionAccessor.apply(dbColumn));
  }

  @Override
  public Sort.Order toSortOrder(Map<String, String> keyMappings) {
    return sortOrderDirection().apply(getDbColumn(keyMappings));
  }

  private BiFunction<CriteriaBuilder, Expression<?>, Order> sort() {
    return switch (direction) {
      case ASC -> CriteriaBuilder::asc;
      case DESC -> CriteriaBuilder::desc;
    };
  }

  private Function<String, Sort.Order> sortOrderDirection() {
    return switch (direction) {
      case ASC -> Sort.Order::asc;
      case DESC -> Sort.Order::desc;
    };
  }

  private String getDbColumn(Map<String, String> keyMappings) {
    if (!keyMappings.containsKey(column)) {
      throw new ClientException("Unknown sort column '%s'. Must be one of %s".formatted(column, keyMappings.keySet()));
    }
    return keyMappings.get(column);
  }
}
