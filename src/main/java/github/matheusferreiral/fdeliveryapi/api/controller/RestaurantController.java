package github.matheusferreiral.fdeliveryapi.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.matheusferreiral.fdeliveryapi.domain.exception.EntityNotFoundException;
import github.matheusferreiral.fdeliveryapi.domain.model.Restaurant;
import github.matheusferreiral.fdeliveryapi.domain.service.RestaurantService;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
  public List<Restaurant> listRestaurant() {
    return restaurantService.list();
  }

  @GetMapping("/{restaurantId}")
  public ResponseEntity<Restaurant> findRestaurant(@PathVariable long restaurantId) {
    Restaurant existingRestaurant = restaurantService.find(restaurantId);
    if (existingRestaurant != null)
      return ResponseEntity.status(HttpStatus.OK).body(existingRestaurant);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerRestaurant(@RequestBody Restaurant newRestaurant) {
    try {
      newRestaurant = restaurantService.save(newRestaurant);
      return ResponseEntity.status(HttpStatus.CREATED).body(newRestaurant);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PutMapping("/{restaurantId}")
  public ResponseEntity<?> updateRestaurant(
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

  @PatchMapping("/{restaurantId}")
  public ResponseEntity<?> partialUpdate(
      @PathVariable("restaurantId") Long id, @RequestBody Map<String, Object> fields) {

    Restaurant existingRestaurant = restaurantService.find(id);

    if (existingRestaurant == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    merge(fields, existingRestaurant);
    return updateRestaurant(existingRestaurant, id);
  }

  private static void merge(Map<String, Object> originFields, Restaurant destinationRestaurant) {

    ObjectMapper objectMapper = new ObjectMapper();
    Restaurant originRestaurant = objectMapper.convertValue(originFields, Restaurant.class);

    originFields.forEach(
        (propertyName, propertyValue) -> {
          Field field = ReflectionUtils.findField(Restaurant.class, propertyName);
          field.setAccessible(true);

          Object newValue = ReflectionUtils.getField(field, originRestaurant);

          ReflectionUtils.setField(field, destinationRestaurant, newValue);
        });
  }
}
