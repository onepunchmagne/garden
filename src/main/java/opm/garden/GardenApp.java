package opm.garden;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import opm.garden.common.domain.Generated;

@SpringBootApplication
@Generated(reason = "Not testing logs")
public class GardenApp {

  private static final Logger log = LoggerFactory.getLogger(GardenApp.class);

  public static void main(String[] args) {
    Environment env = SpringApplication.run(GardenApp.class, args).getEnvironment();

    if (log.isInfoEnabled()) {
      log.info(ApplicationStartupTraces.of(env));
    }
  }
}
