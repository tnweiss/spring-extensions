package dev.tdub.springext.lookup;

import java.util.Map;

import dev.tdub.springext.auth.UserPrincipal;
import dev.tdub.springext.error.exceptions.NotFoundException;
import dev.tdub.springext.pagination.PageResponse;
import dev.tdub.springext.pagination.PageResponseDto;
import dev.tdub.springext.pagination.SortablePageRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class LookupTablesFacade {
  private final Map<String, LookupValueProvider> lookupTables;
  private final PageResponse<String> tables;

  public LookupTablesFacade(Map<String, LookupValueProvider> lookupTables) {
    this.lookupTables = lookupTables;
    this.tables = new PageResponseDto<>(lookupTables.keySet());
  }

  public PageResponse<String> getTables() {
    log.info("Querying lookup tables");
    return tables;
  }

  public PageResponse<String> getTable(UserPrincipal principal,
                                       String table, String filter,
                                       SortablePageRequest pagination) {
    log.info("Querying lookup table '{}' with filter '{}' on page '{}'", table, filter, pagination);
    if (!lookupTables.containsKey(table)) {
      throw new NotFoundException("Table '%s' not found".formatted(table));
    }
    PageResponse<String> values = lookupTables.get(table)
        .queryLookup(principal, filter, pagination);
    log.debug("Lookup results '{}'", values);
    return values;
  }
}
