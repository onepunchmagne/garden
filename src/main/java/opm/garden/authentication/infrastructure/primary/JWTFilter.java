package opm.garden.authentication.infrastructure.primary;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import opm.garden.error.domain.Assert;

class JWTFilter extends GenericFilterBean {

  private static final String TOKEN_PREFIX = "Bearer ";

  private final AuthenticationTokenReader tokens;

  public JWTFilter(AuthenticationTokenReader tokens) {
    Assert.notNull("tokens", tokens);

    this.tokens = tokens;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    readToken(request).flatMap(tokens::read).ifPresent(loadAuthentication());

    chain.doFilter(request, response);
  }

  private Optional<String> readToken(ServletRequest request) {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String authorization = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);

    if (StringUtils.isBlank(authorization) || !authorization.startsWith(TOKEN_PREFIX)) {
      return Optional.empty();
    }

    return Optional.of(authorization.substring(TOKEN_PREFIX.length()));
  }

  private Consumer<Authentication> loadAuthentication() {
    return authentication -> SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
