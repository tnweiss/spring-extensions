package dev.tdub.springext.audit;

import dev.tdub.springext.auth.UserPrincipal;

public interface AuditLog {
  void write(UserPrincipal principal, AuditAction action, String resource, Object details);
  void write(UserPrincipal principal, AuditAction action, String resource);
}
