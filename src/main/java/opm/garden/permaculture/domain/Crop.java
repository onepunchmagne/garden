package opm.garden.permaculture.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import opm.garden.error.domain.Assert;

public final class Crop extends Vegetable {

  //TODO primary.RestCrop to exclude this horrible @JsonProperty in
  // domain, sorry guys for hurting your eyes
  public Crop(@JsonProperty("variety") String variety) {
    super(variety);
    Assert.notNull("variety", variety);
  }

  @Override
  public Integer weight() {
    return DEFAULT_WEIGHT;
  }

  public Seeding sow() {
    return new Seeding(variety());
  }
}
