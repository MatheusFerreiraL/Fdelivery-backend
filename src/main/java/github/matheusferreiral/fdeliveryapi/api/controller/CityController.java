package github.matheusferreiral.fdeliveryapi.api.controller;

import github.matheusferreiral.fdeliveryapi.domain.exception.EntityInUseException;
import github.matheusferreiral.fdeliveryapi.domain.exception.EntityNotFoundException;
import github.matheusferreiral.fdeliveryapi.domain.model.City;
import github.matheusferreiral.fdeliveryapi.domain.service.CityService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  public ResponseEntity<?> find(@PathVariable Long cityId) {
    Optional<City> existingCity = cityService.find(cityId);

    return existingCity
        .map(value -> ResponseEntity.status(HttpStatus.OK).body(existingCity))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PostMapping("/register")
  public ResponseEntity<?> save(@RequestBody City newCity) {
    try {
      newCity = cityService.save(newCity);
      return ResponseEntity.status(HttpStatus.CREATED).body(newCity);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PutMapping("/{cityId}")
  public ResponseEntity<?> update(@PathVariable("cityId") Long id, @RequestBody City updatedCity) {
    try {
      Optional<City> currentCity = cityService.find(id);
      if (currentCity.isPresent()) {
        BeanUtils.copyProperties(updatedCity, currentCity.get(), "id");
        City savedCity = cityService.save(currentCity.get());
        return ResponseEntity.status(HttpStatus.OK).body(savedCity);
      }
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
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
