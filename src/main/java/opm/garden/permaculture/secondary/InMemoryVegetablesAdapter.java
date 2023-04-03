package opm.garden.permaculture.secondary;

import opm.garden.permaculture.domain.Vegetable;
import opm.garden.permaculture.domain.VegetablesPort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Component
public class InMemoryVegetablesAdapter implements VegetablesPort {
  private final Collection<Vegetable> vegetables = new ArrayList<>();

  @Override
  public void save(Vegetable vegetable) {
    vegetables.add(vegetable);
  }

  @Override
  public Collection<Vegetable> all() {
    return Collections.unmodifiableCollection(vegetables);
  }
}
