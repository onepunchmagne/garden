package opm.garden.permaculture.domain;

public class Plant extends Vegetable {
  public Plant(Variety variety) {
    super(variety);
  }

  public Fruit harvest() {
    return new Fruit(variety());
  }
}
