package opm.garden.authentication.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import opm.garden.UnitTest;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

@UnitTest
@ExtendWith(MockitoExtension.class)
class JWTFilterTest {

  @Mock
  private AuthenticationTokenReader tokens;

  @InjectMocks
  private JWTFilter filter;

  @BeforeEach
  @AfterEach
  void resetAuthenticatedUser() {
    SecurityContextHolder.clearContext();
  }

  @Test
  void shouldNotAuthenticateUserWithoutToken() throws IOException, ServletException {
    filter.doFilter(new MockHttpServletRequest(), response(), chain());

    assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
  }

  @Test
  void shouldNotAuthenticateUserWithDummyToken() throws IOException, ServletException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(HttpHeaders.AUTHORIZATION, "dummy");

    filter.doFilter(request, response(), chain());

    assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
  }

  @Test
  void shouldNotAuthenticateUserWithInvalidToken() throws IOException, ServletException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer invalid");

    filter.doFilter(request, response(), chain());

    assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
  }

  @Test
  void shouldAuthenticateUserWithValidToken() throws IOException, ServletException {
    TestingAuthenticationToken authentication = new TestingAuthenticationToken("user", "password");
    when(tokens.read("valid")).thenReturn(Optional.of(authentication));
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer valid");

    filter.doFilter(request, response(), chain());

    assertThat(SecurityContextHolder.getContext().getAuthentication()).isEqualTo(authentication);
  }

  private HttpServletResponse response() {
    return new MockHttpServletResponse();
  }

  private FilterChain chain() {
    return new MockFilterChain();
  }
}
