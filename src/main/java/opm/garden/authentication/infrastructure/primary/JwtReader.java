package opm.garden.authentication.infrastructure.primary;

import opm.garden.error.domain.Assert;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

class JwtReader implements AuthenticationTokenReader {

  private static final Logger log = LoggerFactory.getLogger(JwtReader.class);

  private final JwtParser parser;

  public JwtReader(JwtParser parser) {
    Assert.notNull("parser", parser);

    this.parser = parser;
  }

  @Override
  public Optional<Authentication> read(String token) {
    if (StringUtils.isBlank(token)) {
      log.info("Invalid empty JWT token");

      return Optional.empty();
    }

    try {
      return parseToken(token);
    } catch (JwtException e) {
      log.info("Invalid JWT token");
      log.trace("Invalid JWT token: {}", e.getMessage(), e);

      return Optional.empty();
    }
  }

  private Optional<Authentication> parseToken(String token) {
    Claims claims = parser.parseClaimsJws(token).getBody();

    List<SimpleGrantedAuthority> authorities = readAuthorities(claims);

    User principal = buildPrincipal(claims, authorities);
    return Optional.of(new UsernamePasswordAuthenticationToken(principal, token, authorities));
  }

  private User buildPrincipal(Claims claims, List<SimpleGrantedAuthority> authorities) {
    return new User(claims.getSubject(), "", authorities);
  }

  private List<SimpleGrantedAuthority> readAuthorities(Claims claims) {
    Object tokenauthorities = claims.get("auth");

    if (tokenauthorities == null) {
      return List.of();
    }

    return Stream.of(tokenauthorities.toString().split(",")).filter(StringUtils::isNotBlank).map(SimpleGrantedAuthority::new).toList();
  }
}
