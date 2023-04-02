package opm.garden.dummy.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static opm.garden.dummy.domain.beer.BeersFixture.*;

import org.junit.jupiter.api.Test;
import opm.garden.UnitTest;

@UnitTest
class BeerEntityTest {

  @Test
  void shouldConvertFromAndToDomain() {
    assertThat(BeerEntity.from(beer()).toDomain()).usingRecursiveComparison().isEqualTo(beer());
  }
}
