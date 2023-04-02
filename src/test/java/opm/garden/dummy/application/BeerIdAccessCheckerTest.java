package opm.garden.dummy.application;

import static opm.garden.dummy.domain.BeersIdentityFixture.*;
import static opm.garden.kipe.application.TestAuthentications.*;
import static org.assertj.core.api.Assertions.*;

import opm.garden.UnitTest;
import opm.garden.kipe.application.AccessContext;
import opm.garden.kipe.application.GardenAuthorizations;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class BeerIdAccessCheckerTest {

  private static final BeerIdAccessChecker checker = new BeerIdAccessChecker(
    new GardenAuthorizations(List.of(new BeersAccessesConfiguration().beersAccesses()))
  );

  @Test
  void shouldNotAuthorizedUnauthorizedAction() {
    assertThat(checker.can(AccessContext.of(admin(), "unauthorized", cloackOfFeathersId()))).isFalse();
  }

  @Test
  void shouldAuthorizedAuthorizedAction() {
    assertThat(checker.can(AccessContext.of(admin(), "create", cloackOfFeathersId()))).isTrue();
  }
}
