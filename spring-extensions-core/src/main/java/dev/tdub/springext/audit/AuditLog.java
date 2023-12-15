package dev.tdub.springext.audit;

import dev.tdub.springext.auth.UserPrincipal;

import java.util.Map;

public interface AuditLog {
  void write(UserPrincipal principal, AuditAction action, String resource, Map<String, Object> details);
  void write(UserPrincipal principal, AuditAction action, String resource);
}
