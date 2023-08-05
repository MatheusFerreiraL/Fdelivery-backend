package github.matheusferreiral.fdeliveryapi.api.controller;

import github.matheusferreiral.fdeliveryapi.domain.exception.EntityNotFoundException;
import github.matheusferreiral.fdeliveryapi.domain.model.Restaurant;
import github.matheusferreiral.fdeliveryapi.domain.service.RestaurantService;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

  @Autowired private RestaurantService restaurantService;

  @GetMapping("/list")
  public List<Restaurant> list() {
    return restaurantService.list();
  }

  @GetMapping("/{restaurantId}")
  public ResponseEntity<Restaurant> find(@PathVariable long restaurantId) {
    Restaurant existingRestaurant = restaurantService.find(restaurantId);
    if (existingRestaurant != null)
      return ResponseEntity.status(HttpStatus.OK).body(existingRestaurant);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody Restaurant newRestaurant) {
    try {
      newRestaurant = restaurantService.save(newRestaurant);
      return ResponseEntity.status(HttpStatus.CREATED).body(newRestaurant);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PutMapping("/{restaurantId}")
  public ResponseEntity<?> update(
      @RequestBody Restaurant updatedRestaurant, @PathVariable long restaurantId) {
    try {
      Restaurant existingRestaurant = restaurantService.find(restaurantId);
      if (existingRestaurant != null) {
        BeanUtils.copyProperties(updatedRestaurant, existingRestaurant, "id");
        existingRestaurant = restaurantService.save(existingRestaurant);
        return ResponseEntity.status(HttpStatus.OK).body(existingRestaurant);
      }
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}
