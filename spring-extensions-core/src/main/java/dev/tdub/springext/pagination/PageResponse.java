package dev.tdub.springext.pagination;

import java.util.List;

public interface PageResponse <T> {
  List<T> getContent();
  int getPage();
  int getPageSize();
  Long getTotalElements();
}
