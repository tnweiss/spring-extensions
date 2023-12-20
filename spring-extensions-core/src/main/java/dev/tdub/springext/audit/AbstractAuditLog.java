package dev.tdub.springext.audit;

import java.util.Map;

import dev.tdub.springext.auth.UserPrincipal;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public abstract class AbstractAuditLog implements AuditLog {
  @Override
  public void write(@Nullable UserPrincipal principal, @NonNull AuditAction action, @NonNull String resource) {
    write(principal, action, resource, null);
  }

  @Override
  public void write(@NonNull AuditAction action, @NonNull String resource) {
    write(null, action, resource, null);
  }

  public void write(@NonNull AuditAction action, @NonNull String resource, @Nullable Map<String, Object> details) {
    write(null, action, resource, details);
  }
}
