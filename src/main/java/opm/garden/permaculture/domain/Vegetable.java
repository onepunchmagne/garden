package opm.garden.permaculture.domain;

public abstract class Vegetable {

  public static Integer DEFAULT_WEIGHT = 1;

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

  public abstract Integer weight();

}
