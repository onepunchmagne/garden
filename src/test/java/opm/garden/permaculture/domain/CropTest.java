package opm.garden.permaculture.domain;

import opm.garden.error.domain.MissingMandatoryValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class CropTest {

  @Test
  void shouldNotBuildACropWithoutItsVariety() {
    assertThatThrownBy(() -> new Crop(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("variety");
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {" ", "\t"})
  void shouldNotBuildAnEmptyVariety(String variety) {
    assertThatThrownBy(() -> Variety.of(variety))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("variety");
  }

  @Test
  void shouldGetASeedingWhenSowingACrop() {
    assertThat(garlicCrop().sow())
      .isExactlyInstanceOf(Seeding.class);
  }

  @Test
  void shouldGetAPlantWhenWateringASeeding() {
    assertThat(garlicSeeding().water())
      .isExactlyInstanceOf(Plant.class);
  }

  @Test
  void shouldSeeAPlantVariety() {
    assertThat(new Crop(Variety.of("potato")).sow().water().variety().get())
      .isEqualTo("potato");
  }

  @Test
  void shouldGetAFruitWhenHarvestingAPlant() {
    assertThat(garlicFruit().harvest()).isExactlyInstanceOf(Fruit.class);
  }

  private static Crop garlicCrop() {
    return new Crop(Variety.of("garlic"));
  }

  private static Seeding garlicSeeding() {
    return garlicCrop().sow();
  }

  private static Plant garlicFruit() {
    return garlicSeeding().water();
  }
}
