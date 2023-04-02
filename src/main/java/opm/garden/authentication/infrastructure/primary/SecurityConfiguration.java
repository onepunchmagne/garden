package opm.garden.authentication.infrastructure.primary;

import opm.garden.authentication.domain.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JwtAuthenticationProperties.class)
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfiguration {

  private final JwtAuthenticationProperties properties;
  private final CorsFilter corsFilter;

  public SecurityConfiguration(JwtAuthenticationProperties properties, CorsFilter corsFilter) {
    this.properties = properties;
    this.corsFilter = corsFilter;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web ->
      web
        .ignoring()
        .requestMatchers(HttpMethod.OPTIONS, "/**")
        .requestMatchers("/app/**")
        .requestMatchers("/i18n/**")
        .requestMatchers("/content/**")
        .requestMatchers("/swagger-ui/**")
        .requestMatchers("/swagger-ui.html")
        .requestMatchers("/v3/api-docs/**")
        .requestMatchers("/test/**");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // @formatter:off
    return http
        .csrf()
        .disable()
        .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
        .headers()
          .contentSecurityPolicy(properties.getContentSecurityPolicy())
          .and()
        .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
          .and()
        .permissionsPolicy().policy("camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()")
          .and()
        .frameOptions()
          .deny()
          .and()
        .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
      .authorizeHttpRequests()
        .requestMatchers("/api/authenticate").permitAll()
        .requestMatchers("/api/register").permitAll()
        .requestMatchers("/api/activate").permitAll()
        .requestMatchers("/api/account/reset-password/init").permitAll()
        .requestMatchers("/api/account/reset-password/finish").permitAll()
        .requestMatchers("/api/admin/**").hasAuthority(Role.ADMIN.key())
        .requestMatchers("/api/**").authenticated()
        .requestMatchers("/management/health").permitAll()
        .requestMatchers("/management/health/**").permitAll()
        .requestMatchers("/management/info").permitAll()
        .requestMatchers("/management/prometheus").permitAll()
        .requestMatchers("/management/**").hasAuthority(Role.ADMIN.key())
        .anyRequest().authenticated()
        .and()
      .httpBasic()
        .and()
        .apply(new JWTConfigurer(authenticationTokenReader()))
        .and()
      .build();
    // @formatter:on
  }

  @Bean
  @ConditionalOnMissingBean
  AuthenticationTokenReader authenticationTokenReader() {
    return new JwtReader(Jwts.parserBuilder().setSigningKey(signingKey()).build());
  }

  private SecretKey signingKey() {
    return Keys.hmacShaKeyFor(properties.getJwtBase64Secret().getBytes(StandardCharsets.UTF_8));
  }
}
