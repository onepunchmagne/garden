package opm.garden.authentication.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;

import ch.qos.logback.classic.Level;
import opm.garden.LogsSpy;
import opm.garden.UnitTest;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@UnitTest
@ExtendWith({ LogsSpy.class, MockitoExtension.class })
class JwtReaderTest {

  private static final SecretKey KEY = Keys.hmacShaKeyFor(
    "fd54a45s65fds737b9aafcb3412e07ed99b267f33413274720ddbb7f6c5e64e9f14075f2d7ed041592f0b7657baf8".getBytes(StandardCharsets.UTF_8)
  );

  private final JwtReader reader;
  private final LogsSpy logs;

  public JwtReaderTest(LogsSpy logs) {
    this.logs = logs;

    reader = new JwtReader(parser());
  }

  private JwtParser parser() {
    return Jwts.parserBuilder().setSigningKey(KEY).build();
  }

  @Test
  void shouldNotAuthenticateUserFromEmptyToken() {
    assertThat(reader.read(null)).isEmpty();
    logs.shouldHave(Level.INFO, "Invalid empty JWT token");
  }

  @Test
  void shouldNotAuthenticateUserFromBlankToken() {
    assertThat(reader.read(" ")).isEmpty();
    logs.shouldHave(Level.INFO, "Invalid empty JWT token");
  }

  @Test
  void shouldNotAutenticateUserFromInvalidToken() {
    assertThat(reader.read("invalid")).isEmpty();
    logs.shouldHave(Level.INFO, "Invalid JWT token");
  }

  @Test
  void shouldGetAuthenticationFromValidToken() {
    Authentication authentication = reader.read(userToken()).get();

    assertThat(((User) authentication.getPrincipal()).getUsername()).isEqualTo("test");
    assertThat(authentication.getAuthorities().stream().map(SimpleGrantedAuthority.class::cast))
      .containsExactly(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
  }

  private String userToken() {
    return Jwts
      .builder()
      .setSubject("test")
      .claim("auth", "ROLE_USER,ROLE_ADMIN,,")
      .signWith(KEY, SignatureAlgorithm.HS512)
      .setExpiration(Date.from(Instant.now().plusSeconds(120)))
      .compact();
  }

  @Test
  void shouldGetAuthenticationFromValidTokenWithoutRoles() {
    Authentication authentication = reader.read(userTokenWithoutRoles()).get();

    assertThat(((User) authentication.getPrincipal()).getUsername()).isEqualTo("test");
    assertThat(authentication.getAuthorities()).isEmpty();
  }

  private String userTokenWithoutRoles() {
    return Jwts
      .builder()
      .setSubject("test")
      .signWith(KEY, SignatureAlgorithm.HS512)
      .setExpiration(Date.from(Instant.now().plusSeconds(120)))
      .compact();
  }
}
