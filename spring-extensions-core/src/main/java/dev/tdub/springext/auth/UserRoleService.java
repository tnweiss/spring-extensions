package dev.tdub.springext.auth;

public interface UserRoleService {
  boolean isAdmin(UserPrincipal principal);
}
