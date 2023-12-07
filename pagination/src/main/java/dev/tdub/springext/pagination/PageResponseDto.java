package dev.tylerweiss.springext.pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import dev.tylerweiss.springext.error.exceptions.InternalServerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@RequiredArgsConstructor
public class PageResponseDto<T> implements PageResponse<T> {
  private final List<T> content;
  private final int page;
  private final int pageSize;
  private final Long totalElements;

  public PageResponseDto(List<T> content, PageRequest pagination) {
    if (pagination.getPage() > 0 || content.size() >= pagination.getPageSize()) {
      throw new InternalServerException("Pagination returning content without calculating total elements accurately");
    }

    this.content = content;
    this.page = pagination.getPage();
    this.pageSize = pagination.getPageSize();
    this.totalElements = Integer.valueOf(content.size()).longValue();
  }

  public PageResponseDto(List<T> content, PageRequest pagination, Long totalElements) {
    this.content = content;
    this.page = pagination.getPage();
    this.pageSize = pagination.getPageSize();
    this.totalElements = totalElements;
  }

  public PageResponseDto(Page<T> page) {
    this.content = page.getContent();
    this.page = page.getPageable().getPageNumber();
    this.pageSize = page.getPageable().getPageSize();
    this.totalElements = page.getTotalElements();
  }

  public PageResponseDto(Collection<T> page) {
    this.content = new ArrayList<>(page);
    this.page = 0;
    this.pageSize = page.size() + 1;
    this.totalElements = Integer.valueOf(page.size()).longValue();
  }

  public <D> PageResponseDto<D> map(Function<T, D> mapper) {
    return new PageResponseDto<>(
        content.stream().map(mapper).toList(),
        page,
        pageSize,
        totalElements
    );
  }
}
