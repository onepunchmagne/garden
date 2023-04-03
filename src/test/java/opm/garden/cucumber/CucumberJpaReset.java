package opm.garden.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public class CucumberJpaReset {

//  @Autowired
  private Collection<JpaRepository<?, ?>> repositories;

  @After
  @Before
  @Transactional
  public void wipeData() {
//    repositories.forEach(JpaRepository::deleteAllInBatch);
  }
}
