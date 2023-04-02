package opm.garden.authentication.infrastructure.primary;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

@Validated
@ConfigurationProperties("application.security")
class JwtAuthenticationProperties {

  private String contentSecurityPolicy =
    """
    default-src 'self'; frame-src 'self' data:; \
    script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; \
    style-src 'self' 'unsafe-inline' https://fonts.googleapis.com; \
    img-src 'self' data:; \
    font-src 'self' data: https://fonts.gstatic.com;\
    """;

  private String jwtBase64Secret;

  @NotBlank
  public String getContentSecurityPolicy() {
    return contentSecurityPolicy;
  }

  public void setContentSecurityPolicy(String contentSecurityPolicy) {
    this.contentSecurityPolicy = contentSecurityPolicy;
  }

  @NotBlank
  public String getJwtBase64Secret() {
    return jwtBase64Secret;
  }

  public void setJwtBase64Secret(String jwtBase64Secret) {
    this.jwtBase64Secret = jwtBase64Secret;
  }
}
