package opm.garden.dummy.application;

import opm.garden.dummy.domain.BeerId;
import opm.garden.dummy.domain.beer.Beer;
import opm.garden.dummy.domain.beer.BeerToCreate;
import opm.garden.dummy.domain.beer.Beers;
import opm.garden.dummy.domain.beer.BeersCreator;
import opm.garden.dummy.domain.beer.BeersRemover;
import opm.garden.dummy.domain.beer.BeersRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BeersApplicationService {

  private final BeersRepository beers;
  private final BeersCreator creator;
  private final BeersRemover remover;

  public BeersApplicationService(BeersRepository beers) {
    this.beers = beers;

    creator = new BeersCreator(beers);
    remover = new BeersRemover(beers);
  }

  @Transactional
  @PreAuthorize("can('create', #beerToCreate)")
  public Beer create(BeerToCreate beerToCreate) {
    return creator.create(beerToCreate);
  }
  
  @Transactional
  @PreAuthorize("can('remove', #beer)")
  public void remove(BeerId beer) {
    remover.remove(beer);
  }

  @Transactional(readOnly = true)
  public Beers catalog() {
    return beers.catalog();
  }

}
