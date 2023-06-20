package opm.garden.permaculture.primary;

import io.swagger.v3.oas.annotations.media.Schema;
import opm.garden.permaculture.domain.Crop;

@Schema(name = "Crop", description = "This is you crop, sow it with pleasure!")
public class HttpCrop {

  @Schema(description = "Is it garlic or tomato? Which variety would you like to sow?")
  private String variety;

  public void setVariety(String variety) {
    this.variety = variety;
  }

  public Crop toDomain() {
    return new Crop(variety);
  }
}
