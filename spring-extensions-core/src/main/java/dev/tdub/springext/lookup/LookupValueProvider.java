package dev.tdub.springext.lookup;

import dev.tdub.springext.auth.UserPrincipal;
import dev.tdub.springext.pagination.PageResponse;
import dev.tdub.springext.pagination.SortablePageRequest;

@FunctionalInterface
public interface LookupValueProvider {
  PageResponse<String> queryLookup(UserPrincipal principal, String filter, SortablePageRequest pageable);
}
