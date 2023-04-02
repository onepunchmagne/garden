package opm.garden.dummy.application;

import opm.garden.dummy.domain.beer.BeerToCreate;
import opm.garden.kipe.application.AccessChecker;
import opm.garden.kipe.application.AccessContext;
import opm.garden.kipe.application.GardenAuthorizations;
import org.springframework.stereotype.Component;

@Component
class BeerToCreateAccessChecker implements AccessChecker<BeerToCreate> {

  private final GardenAuthorizations authorizations;

  public BeerToCreateAccessChecker(GardenAuthorizations authorizations) {
    this.authorizations = authorizations;
  }

  @Override
  public boolean can(AccessContext<BeerToCreate> access) {
    return authorizations.allAuthorized(access.authentication(), access.action(), BeerResource.BEERS);
  }
}
