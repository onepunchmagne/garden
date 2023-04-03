package opm.garden.permaculture.primary;

import opm.garden.permaculture.domain.Vegetable;

import java.util.Collection;

class RestVegetables {

  private final Collection<Vegetable> vegetables;

  private RestVegetables(Collection<Vegetable> vegetables) {
    this.vegetables = vegetables;
  }

  public static RestVegetables from(Collection<Vegetable> vegetables) {
    return new RestVegetables(vegetables);
  }

  public Collection<Vegetable> getVegetables() {
    return vegetables;
  }
}
