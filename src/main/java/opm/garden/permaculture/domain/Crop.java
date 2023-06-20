package opm.garden.permaculture.domain;

import opm.garden.error.domain.Assert;

public final class Crop extends Vegetable {

  public Crop(Variety variety) {
    super(variety);
    Assert.notNull("variety", variety);
  }

  public Seedling sow() {
    return new Seedling(variety());
  }
}
