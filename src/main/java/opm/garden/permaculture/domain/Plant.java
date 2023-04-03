package opm.garden.permaculture.domain;

public class Plant extends Vegetable {
  public Plant(String variety) {
    super(variety);
  }

  public Fruit harvest() {
    return new Fruit(variety());
  }
}
