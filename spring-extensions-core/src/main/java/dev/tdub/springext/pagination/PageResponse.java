package dev.tdub.springext.pagination;

import java.util.List;
import java.util.function.Function;

public interface PageResponse <T> {
  List<T> getContent();
  int getPage();
  int getPageSize();
  Long getTotalElements();

  default <R> PageResponse<R> map(Function<T, R> mapper) {
    return new PageResponseDto<>(getContent().stream().map(mapper).toList(),
        getPage(), getPageSize(), getTotalElements());
  }
}
