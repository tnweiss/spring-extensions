package dev.tdub.springext.audit;

import dev.tdub.springext.auth.UserPrincipal;

public abstract class AbstractAuditLog implements AuditLog {
  @Override
  public void write(UserPrincipal principal, AuditAction action, String resource) {
    write(principal, action, resource, null);
  }
}
