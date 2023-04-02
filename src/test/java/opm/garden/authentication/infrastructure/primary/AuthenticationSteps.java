package opm.garden.authentication.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;

import opm.garden.authentication.domain.Role;
import io.cucumber.java.en.Given;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;

public class AuthenticationSteps {

  private final Map<String, User> users;

  @Autowired
  private TestRestTemplate rest;

  public AuthenticationSteps(JwtAuthenticationProperties properties) {
    users = new UsersBuilder(buildKey(properties)).add("admin", Role.ADMIN).add("user", Role.USER).build();
  }

  private SecretKey buildKey(JwtAuthenticationProperties properties) {
    return Keys.hmacShaKeyFor(properties.getJwtBase64Secret().getBytes(StandardCharsets.UTF_8));
  }

  @Given("I am logged in as {string}")
  public void authenticateUser(String username) {
    rest.getRestTemplate().setInterceptors(interceptorsWithAuthentication(username));
  }

  private List<ClientHttpRequestInterceptor> interceptorsWithAuthentication(String user) {
    List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(rest.getRestTemplate().getInterceptors());

    User userToAuthenticate = users.get(user);

    assertThat(userToAuthenticate).as(unknownUserMessage(user)).isNotNull();

    interceptors.add((request, body, execution) -> {
      request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + userToAuthenticate.token());

      return execution.execute(request, body);
    });

    return interceptors;
  }

  @Given("I am logged out")
  public void logout() {
    rest.getRestTemplate().setInterceptors(interceptorsWithoutAuthentication());
  }

  private List<ClientHttpRequestInterceptor> interceptorsWithoutAuthentication() {
    List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(rest.getRestTemplate().getInterceptors());

    interceptors.add((request, body, execution) -> {
      request.getHeaders().remove(HttpHeaders.AUTHORIZATION);

      return execution.execute(request, body);
    });

    return interceptors;
  }

  private String unknownUserMessage(String user) {
    return "Trying to authenticate an unknown user: " + user;
  }

  private static class UsersBuilder {

    private final SecretKey key;
    private final Map<String, User> users = new ConcurrentHashMap<>();

    private UsersBuilder(SecretKey key) {
      this.key = key;
    }

    public UsersBuilder add(String username, Role... roles) {
      users.put(username, new User(key, username, roles));

      return this;
    }

    public Map<String, User> build() {
      return Collections.unmodifiableMap(users);
    }
  }

  private static class User {

    private final SecretKey key;
    private final String username;
    private final String authorities;

    public User(SecretKey key, String username, Role[] roles) {
      this.key = key;
      this.username = username;
      authorities = Stream.of(roles).map(Role::key).collect(Collectors.joining(","));
    }

    private String token() {
      return Jwts
        .builder()
        .setSubject(username)
        .signWith(key)
        .claim("auth", authorities)
        .setExpiration(Date.from(Instant.now().plusSeconds(300)))
        .compact();
    }
  }
}
