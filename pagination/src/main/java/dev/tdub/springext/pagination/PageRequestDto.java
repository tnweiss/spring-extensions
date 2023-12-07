package dev.tdub.springext.pagination;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class PageRequestDto implements PageRequest {
  private final int page;
  private final int pageSize;

  @JsonCreator
  public PageRequestDto(@JsonProperty("page") Integer page, @JsonProperty("pageSize") Integer pageSize) {
    this.page = Optional.ofNullable(page).orElse(0);
    this.pageSize = Optional.ofNullable(pageSize).orElse(50);
  }

  @Override
  public Pageable toPageable() {
    return org.springframework.data.domain.PageRequest.of(page, pageSize);
  }
}
