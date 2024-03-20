package dev.tdub.springext.audit;

import dev.tdub.springext.auth.UserPrincipal;
import jakarta.validation.constraints.Null;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConditionalOnProperty(name = "springext.audit.logger", havingValue = "log4j2", matchIfMissing = true)
@Log4j2
public class Log4jAuditLog extends AbstractAuditLog {
  @Override
  public void write(@Nullable UserPrincipal principal, @NonNull AuditAction action, @NonNull String resource, @NonNull Map<String, Object> details) {
    log.info("Audit: Principal={} Action={} Resource={} Details={}", principal, action, resource, details);
  }
}
