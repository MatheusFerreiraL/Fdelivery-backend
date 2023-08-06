package github.matheusferreiral.fdeliveryapi.api.controller;

import github.matheusferreiral.fdeliveryapi.domain.exception.EntityInUseException;
import github.matheusferreiral.fdeliveryapi.domain.exception.EntityNotFoundException;
import github.matheusferreiral.fdeliveryapi.domain.model.City;
import github.matheusferreiral.fdeliveryapi.domain.service.CityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cities")
public class CityController {

  @Autowired private CityService cityService;

  @GetMapping("/list")
  public List<City> list() {
    return cityService.list();
  }

  @GetMapping("/{cityId}")
  public ResponseEntity<City> find(@PathVariable Long cityId) {
    City existingCity = cityService.find(cityId);
    if (existingCity != null) {
      return ResponseEntity.status(HttpStatus.OK).body(existingCity);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @PostMapping("/register")
  public ResponseEntity<City> save(@RequestBody City newCity) {
    newCity = cityService.save(newCity);
    return ResponseEntity.status(HttpStatus.CREATED).body(newCity);
  }

  @DeleteMapping("/{cityId}")
  public ResponseEntity<City> delete(@PathVariable("cityId") Long id) {
    try {
      cityService.remove(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (EntityInUseException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }
}
