package opm.garden.authentication.domain;

import static org.assertj.core.api.Assertions.*;

import opm.garden.UnitTest;
import java.util.Set;
import org.junit.jupiter.api.Test;

@UnitTest
class RolesTest {

  @Test
  void shouldNotHaveRoleWithoutRoles() {
    assertThat(new Roles(null).hasRole()).isFalse();
  }

  @Test
  void shouldHaveRoleWithRoles() {
    assertThat(new Roles(Set.of(Role.ADMIN)).hasRole()).isTrue();
  }

  @Test
  void shouldNotHaveNotAffectedRole() {
    assertThat(new Roles(Set.of(Role.ADMIN)).hasRole(Role.USER)).isFalse();
  }

  @Test
  void shouldHaveAffectedRole() {
    assertThat(new Roles(Set.of(Role.ADMIN)).hasRole(Role.ADMIN)).isTrue();
  }

  @Test
  void shouldStreamRoles() {
    assertThat(new Roles(Set.of(Role.ADMIN)).stream()).containsExactly(Role.ADMIN);
  }

  @Test
  void shouldGetRoles() {
    assertThat(new Roles(Set.of(Role.ADMIN)).get()).containsExactly(Role.ADMIN);
  }
}
