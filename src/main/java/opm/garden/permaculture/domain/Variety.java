package opm.garden.permaculture.domain;

import opm.garden.error.domain.Assert;

public class Variety {
  private final String value;

  private Variety(String variety) {
    Assert.notBlank("variety", variety);
    this.value = variety;
  }

  public static Variety of(String variety) {
    return new Variety(variety);
  }

  public String get() {
    return value;
  }
}
