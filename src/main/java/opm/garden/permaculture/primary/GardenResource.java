package opm.garden.permaculture.primary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import opm.garden.permaculture.application.VegetablesApplicationService;
import opm.garden.permaculture.domain.Crop;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
class GardenResource {

  private final VegetablesApplicationService vegetables;

  public GardenResource(VegetablesApplicationService vegetables) {
    this.vegetables = vegetables;
  }

  @PostMapping("/crops")
  @Operation(summary = "Sow a crop in the garden")
  @ApiResponse(description = "Crop has been sowed", responseCode = "201")
  @ResponseStatus(HttpStatus.CREATED)
  void sowACrop(@Validated @RequestBody Crop cropToSow) {
    vegetables.sow(cropToSow);
  }

  @GetMapping("/vegetables")
  @Operation(summary = "See all vegetables of the garden")
  @ApiResponse(description = "Collection of found vegetables", responseCode = "200")
  ResponseEntity<RestVegetables> getGardenVegetables() {
    return ResponseEntity.ok(RestVegetables.from(vegetables.all()));
  }
}
