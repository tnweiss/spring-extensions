package dev.tdub.springext.audit;

import dev.tdub.springext.auth.UserPrincipal;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "springext.audit.logger", havingValue = "log4j2", matchIfMissing = true)
@Log4j2
public class Log4jAuditLog extends AbstractAuditLog {
  @Override
  public void write(UserPrincipal principal, AuditAction action, String resource, Object details) {
    log.info("Audit: Principal={} Action={} Resource={} Details={}", principal, action, resource, details);
  }
}
