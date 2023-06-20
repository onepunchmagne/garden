package opm.garden.permaculture.domain;

import opm.garden.error.domain.Assert;

public final class Crop extends Vegetable {

  public Crop(String variety) {
    super(variety);
    Assert.notNull("variety", variety);
  }

  public Seeding sow() {
    return new Seeding(variety());
  }
}
