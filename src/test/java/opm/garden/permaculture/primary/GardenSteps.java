package opm.garden.permaculture.primary;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import opm.garden.cucumber.CucumberRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

import static opm.garden.cucumber.CucumberAssertions.*;

public class GardenSteps {

  private static final String CROP_TEMPLATE = """
    {
      "variety": "{VARIETY}"
    }
    """;

  @Autowired
  private CucumberRestTemplate rest;

  @When("I sow a crop")
  public void sowACrop() {
    String payload = CROP_TEMPLATE.replace("{VARIETY}", "salad");

    rest.post("/api/crops", payload);

    assertThatLastResponse().hasHttpStatus(HttpStatus.CREATED);
  }

  @Then("My garden has a vegetable planted")
  public void shouldHaveASeeding() {
    rest.get("/api/vegetables");

    assertThatLastResponse().hasOkStatus().hasElement("$.vegetables")
      .containingExactly(List.of(Map.of("variety", "salad")));
  }
}
