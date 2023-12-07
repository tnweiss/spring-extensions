package dev.tdub.springext.pagination;

import org.springframework.data.domain.Pageable;

public interface PageRequest {
  int getPage();
  int getPageSize();

  Pageable toPageable();
}
