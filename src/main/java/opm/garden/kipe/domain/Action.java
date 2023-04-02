package opm.garden.kipe.domain;

import opm.garden.error.domain.Assert;

public record Action(String action) {
  public Action {
    Assert.notBlank("action", action);
  }
  
  @Override
  public String toString() {
    return action();
  }
}
