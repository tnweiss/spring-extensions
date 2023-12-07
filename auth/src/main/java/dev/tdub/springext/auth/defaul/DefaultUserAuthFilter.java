package dev.tdub.springext.auth.authenticator;

import java.time.Instant;
import java.util.Optional;

import com.ora.web.common.dataaccess.user.UserDao;
import com.ora.web.common.dataaccess.user.UserRepo;
import com.ora.web.common.dto.auth.OraDefaultUserAuthenticationDto;
import com.ora.web.common.dto.user.User;
import com.ora.web.common.security.OraAuthenticator;

import dev.tdub.springext.auth.Authentication;
import dev.tdub.springext.error.exceptions.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1000)
@ConditionalOnProperty(value = "ora.security.authentication.required", havingValue = "false")
@Component
@RequiredArgsConstructor
public class DefaultUserAuthFilter implements OraAuthenticator {
  private static final String EMAIL = "default.user@ora.com";

  private final UserRepo userRepo;

  @Override
  public boolean canAuthenticate(HttpServletRequest request) {
    return true;
  }

  @Override
  public Authentication authenticate(HttpServletRequest request) throws AuthenticationException {
    User defaultUser = createDefaultUser();
    return new OraDefaultUserAuthenticationDto(defaultUser);
  }

  private User createDefaultUser() {
    Optional<UserDao> user = userRepo.findByEmail(EMAIL);
    if (user.isPresent()) {
      return user.get().toUser();
    }

    UserDao defaultUser = new UserDao();
    defaultUser.setFirstName("Default");
    defaultUser.setLastName("User");
    defaultUser.setActive(false);
    defaultUser.setAdmin(true);
    defaultUser.setEmail(EMAIL);
    defaultUser.setPassword("");
    defaultUser.setCreatedAt(Instant.now());
    defaultUser.setLastUpdatedAt(Instant.now());

    return userRepo.save(defaultUser).toUser();
  }
}
