package opm.garden.dummy.infrastructure.primary.beer;

import static org.assertj.core.api.Assertions.*;

import opm.garden.JsonHelper;
import opm.garden.UnitTest;
import opm.garden.dummy.domain.beer.Beers;
import opm.garden.dummy.domain.beer.BeersFixture;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class RestBeersTest {

  @Test
  void shouldSerializeToJson() {
    assertThat(JsonHelper.writeAsString(RestBeers.from(new Beers(List.of(BeersFixture.beer()))))).isEqualTo(json());
  }

  private String json() {
    return "{\"beers\":[" + RestBeerTest.json() + "]}";
  }
}
