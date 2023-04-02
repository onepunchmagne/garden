package opm.garden.dummy.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static opm.garden.dummy.domain.BeersIdentityFixture.*;
import static opm.garden.dummy.domain.beer.BeersFixture.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import opm.garden.IntegrationTest;

@Transactional
@IntegrationTest
class JpaBeersRepositoryIntTest {

  @Autowired
  private JpaBeersRepository beers;

  @Test
  void shouldSaveAndGetBeer() {
    beers.saveAndFlush(BeerEntity.from(beer()));

    assertThat(beers.findById(cloackOfFeathersId().get()).get().toDomain()).usingRecursiveComparison().isEqualTo(beer());
  }
}
