package opm.garden.permaculture.application;

import opm.garden.permaculture.domain.Crop;
import opm.garden.permaculture.domain.Vegetable;
import opm.garden.permaculture.domain.VegetablesPort;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class VegetablesApplicationService {
  private final VegetablesPort vegetables;

  public VegetablesApplicationService(VegetablesPort vegetables) {
    this.vegetables = vegetables;
  }

  public void sow(Crop cropToSow) {
    vegetables.save(cropToSow.sow());
  }

  public Collection<Vegetable> all() {
    return vegetables.all();
  }
}
