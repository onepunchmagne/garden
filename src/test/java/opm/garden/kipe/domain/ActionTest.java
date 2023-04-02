package opm.garden.kipe.domain;

import static org.assertj.core.api.Assertions.*;

import opm.garden.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class ActionTest {

  @Test
  void shouldGetActionAsToString() {
    assertThat(new Action("act")).hasToString("act");
  }
}
