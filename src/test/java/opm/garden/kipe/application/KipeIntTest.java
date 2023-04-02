package opm.garden.kipe.application;

import static org.assertj.core.api.Assertions.*;

import opm.garden.IntegrationTest;
import opm.garden.kipe.domain.KipeDummy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

@IntegrationTest
class KipeIntTest {

  @Autowired
  private KipeApplicationService service;

  @Test
  void shouldNotBeAbleToMakeUpdateWithoutAuthentication() {
    assertThatThrownBy(() -> service.update(new KipeDummy("value"))).isExactlyInstanceOf(AccessDeniedException.class);
  }

  @Test
  @WithMockUser
  void shouldNotBeAbleToMakeUnauthorizedUpdate() {
    assertThatThrownBy(() -> service.update(new KipeDummy("unauthorized"))).isExactlyInstanceOf(AccessDeniedException.class);
  }

  @Test
  @WithMockUser
  void shouldBeAbleToMakeAuhtorizedUpdate() {
    assertThatCode(() -> service.update(new KipeDummy("authorized"))).doesNotThrowAnyException();
  }
}
