package dev.tdub.springext.lookup;

import com.ora.web.common.dto.user.UserPrincipal;

import dev.tdub.springext.pagination.PageResponse;
import dev.tdub.springext.pagination.SortablePageRequest;

@FunctionalInterface
public interface LookupValueProvider {
  PageResponse<String> queryLookup(UserPrincipal principal, String filter, SortablePageRequest pageable);
}
