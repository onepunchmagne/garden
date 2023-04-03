package opm.garden.permaculture.domain;

public abstract class Vegetable {
  private final String variety;

  protected Vegetable(String variety) {
    this.variety = variety;
  }

  public String variety() {
    return variety;
  }
  public String getVariety() {
    return variety;
  }
}
