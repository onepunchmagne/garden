package opm.garden.permaculture.domain;

public final class Seedling extends Vegetable {
  public Seedling(Variety variety) {
    super(variety);
  }

  public Plant water() {
    return new Plant(variety());
  }
}
