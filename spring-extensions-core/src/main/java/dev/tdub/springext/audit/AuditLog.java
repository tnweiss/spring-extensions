package dev.tdub.springext.audit;

import dev.tdub.springext.auth.UserPrincipal;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Map;

public interface AuditLog {
  void write(@Nullable UserPrincipal principal, @NonNull AuditAction action, @NonNull String resource, @Nullable Map<String, Object> details);
  void write(@Nullable UserPrincipal principal, @NonNull AuditAction action, @NonNull String resource);

  void write(@NonNull AuditAction action, @NonNull String resource);

  void write(@NonNull AuditAction action, @NonNull String resource, @Nullable Map<String, Object> details);
}
