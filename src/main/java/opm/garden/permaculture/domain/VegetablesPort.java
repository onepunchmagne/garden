package opm.garden.permaculture.domain;

import java.util.Collection;

public interface VegetablesPort {
  void save(Vegetable vegetable);

  Collection<Vegetable> all();
}
