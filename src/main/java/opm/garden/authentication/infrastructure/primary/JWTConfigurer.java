package opm.garden.authentication.infrastructure.primary;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final AuthenticationTokenReader jwt;

  public JWTConfigurer(AuthenticationTokenReader jwt) {
    this.jwt = jwt;
  }

  @Override
  public void configure(HttpSecurity http) {
    JWTFilter jwtFilter = new JWTFilter(jwt);

    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
