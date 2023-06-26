package opm.garden.permaculture.domain;

public class Fruit extends Vegetable {
  protected Fruit(String variety) {
    super(variety);
  }

  @Override
  public Integer weight() {
    return DEFAULT_WEIGHT;
  }
}
