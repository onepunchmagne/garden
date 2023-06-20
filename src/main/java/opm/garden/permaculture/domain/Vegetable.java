package opm.garden.permaculture.domain;

public abstract class Vegetable {
  private final Variety variety;

  protected Vegetable(Variety variety) {
    this.variety = variety;
  }

  public Variety variety() {
    return variety;
  }

  public String getVariety() {
    return variety.get();
  }
}
