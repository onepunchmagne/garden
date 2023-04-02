package opm.garden.dummy.application;

import opm.garden.dummy.domain.BeerId;
import opm.garden.kipe.application.AccessChecker;
import opm.garden.kipe.application.AccessContext;
import opm.garden.kipe.application.GardenAuthorizations;
import org.springframework.stereotype.Component;

@Component
class BeerIdAccessChecker implements AccessChecker<BeerId> {

  private final GardenAuthorizations authorizations;

  public BeerIdAccessChecker(GardenAuthorizations authorizations) {
    this.authorizations = authorizations;
  }

  @Override
  public boolean can(AccessContext<BeerId> access) {
    return authorizations.allAuthorized(access.authentication(), access.action(), BeerResource.BEERS);
  }
}
