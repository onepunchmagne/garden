package opm.garden.dummy.domain.order;

import opm.garden.dummy.domain.Amount;
import opm.garden.dummy.domain.BeerId;
import opm.garden.error.domain.Assert;

public record OrderedBeer(BeerId beer, Amount unitPrice) {
  public OrderedBeer {
    Assert.notNull("beer", beer);
    Assert.notNull("unitPrice", unitPrice);
  }
}
