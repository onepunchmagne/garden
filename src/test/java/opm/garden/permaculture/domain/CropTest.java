package opm.garden.permaculture.domain;

import opm.garden.error.domain.MissingMandatoryValueException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CropTest {

  @Test
  void shouldNotBuildACropWithoutItsVariety() {
    assertThatThrownBy(() -> new Crop(null))
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
    assertThat(new Crop("potato").sow().water().variety())
      .isEqualTo("potato");
  }

  @Test
  void shouldRetrieveTheDefaultWeigthOfACrop() {
    // GIVEN
    Crop potatoCrop = new Crop("potato");

    // WHEN - THEN
    assertThat(potatoCrop.weight()).isEqualTo(1);
  }

  @Test
  void shouldRetrieveTheDefaultWeigthOfOSeeding() {
    // GIVEN
    Crop potatoCrop = new Crop("potato");

    // WHEN - THEN
    assertThat(potatoCrop.sow().weight()).isEqualTo(2);
  }

  @Test
  void shouldGetAFruitWhenHarvestingAPlant() {
    assertThat(garlicFruit().harvest()).isExactlyInstanceOf(Fruit.class);
  }

  private static Crop garlicCrop() {
    return new Crop("garlic");
  }

  private static Seeding garlicSeeding() {
    return garlicCrop().sow();
  }

  private static Plant garlicFruit() {
    return garlicSeeding().water();
  }
}
