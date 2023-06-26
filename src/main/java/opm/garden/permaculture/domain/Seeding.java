package opm.garden.permaculture.domain;

public final class Seeding extends Vegetable {
  public Seeding(String variety) {
    super(variety);
  }

  public Plant water() {
    return new Plant(variety());
  }

  @Override
  public Integer weight() {
    return DEFAULT_WEIGHT * 2;
  }
}
