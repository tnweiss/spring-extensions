package com.ora.web.controller;

import java.util.List;

import com.ora.web.common.dto.auth.OraAuthentication;
import dev.tdub.springext.pagination.PageResponse;
import dev.tdub.springext.pagination.SortablePageRequestDto;
import com.ora.web.facade.LookupTablesFacade;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/lookup-tables")
public class LookupTablesController {
  private final LookupTablesFacade lookupTablesFacade;

  @GetMapping()
  public PageResponse<String> getTables() {
    return lookupTablesFacade.getTables();
  }

  @GetMapping("/{table}")
  public PageResponse<String> getTable(OraAuthentication authentication,
                                       @PathVariable("table") String table,
                                       String filter,
                                       @ParameterObject SortablePageRequestDto pagination) {
    return lookupTablesFacade.getTable(authentication.getPrincipal(), table, filter, pagination);
  }
}
