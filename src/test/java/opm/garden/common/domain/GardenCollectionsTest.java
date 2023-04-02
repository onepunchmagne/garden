package opm.garden.common.domain;

import static org.assertj.core.api.Assertions.*;

import opm.garden.UnitTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class GardenCollectionsTest {

  @Nested
  @DisplayName("Collections")
  class GardenCollectionsCollectionsTest {

    @Test
    void shouldGetEmptyImmutableCollectionFromNullCollection() {
      Collection<Object> input = null;
      Collection<Object> collection = GardenCollections.immutable(input);

      assertThat(collection).isEmpty();
      assertThatThrownBy(() -> collection.clear()).isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldGetImmutableCollectionFromMutableCollection() {
      Collection<String> input = new ArrayList<>();
      input.add("value");
      Collection<String> collection = GardenCollections.immutable(input);

      assertThat(collection).containsExactly("value");
      assertThatThrownBy(() -> collection.clear()).isExactlyInstanceOf(UnsupportedOperationException.class);
    }
  }

  @Nested
  @DisplayName("Set")
  class GardenCollectionsSetTest {

    @Test
    void shouldGetEmptyImmutableCollectionFromNullCollection() {
      Set<Object> input = null;
      Set<Object> set = GardenCollections.immutable(input);

      assertThat(set).isEmpty();
      assertThatThrownBy(() -> set.clear()).isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldGetImmutableCollectionFromMutableCollection() {
      Set<String> input = new HashSet<>();
      input.add("value");
      Set<String> set = GardenCollections.immutable(input);

      assertThat(set).containsExactly("value");
      assertThatThrownBy(() -> set.clear()).isExactlyInstanceOf(UnsupportedOperationException.class);
    }
  }

  @Nested
  @DisplayName("List")
  class GardenCollectionsListTest {

    @Test
    void shouldGetEmptyImmutableCollectionFromNullCollection() {
      List<Object> input = null;
      List<Object> list = GardenCollections.immutable(input);

      assertThat(list).isEmpty();
      assertThatThrownBy(() -> list.clear()).isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldGetImmutableCollectionFromMutableCollection() {
      List<String> input = new ArrayList<>();
      input.add("value");
      List<String> list = GardenCollections.immutable(input);

      assertThat(list).containsExactly("value");
      assertThatThrownBy(() -> list.clear()).isExactlyInstanceOf(UnsupportedOperationException.class);
    }
  }
}
