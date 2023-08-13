package github.matheusferreiral.fdeliveryapi.api.controller;

import github.matheusferreiral.fdeliveryapi.domain.exception.EntityInUseException;
import github.matheusferreiral.fdeliveryapi.domain.exception.EntityNotFoundException;
import github.matheusferreiral.fdeliveryapi.domain.model.Kitchen;
import github.matheusferreiral.fdeliveryapi.domain.repository.KitchenRepository;
import github.matheusferreiral.fdeliveryapi.domain.service.KitchenService;
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
@RequestMapping("/kitchens")
public class KitchenController {

  @Autowired private KitchenService kitchenService;
  @Autowired private KitchenRepository kitchenRepository;

  @GetMapping("/list")
  public List<Kitchen> list() {
    return kitchenRepository.findAll();
  }

  @GetMapping("/{kitchenId}")
  public ResponseEntity<Kitchen> find(@PathVariable("kitchenId") Long id) {
    Optional<Kitchen> kitchen = kitchenRepository.findById(id);

    return kitchen
        .map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody Kitchen newKitchen) {
    newKitchen = kitchenService.save(newKitchen);
    return ResponseEntity.status(HttpStatus.CREATED).body(newKitchen);
  }

  @PutMapping("/{kitchenId}")
  public ResponseEntity<Kitchen> update(
      @PathVariable Long kitchenId, @RequestBody Kitchen kitchen) {

    Optional<Kitchen> existingKitchen = kitchenRepository.findById(kitchenId);
    if (existingKitchen.isPresent()) {
      BeanUtils.copyProperties(kitchen, existingKitchen.get(), "id");
      Kitchen savedKitchen = kitchenService.save(existingKitchen.get());
      return ResponseEntity.status(HttpStatus.OK).body(savedKitchen);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @DeleteMapping("/{kitchenId}")
  public ResponseEntity<?> delete(@PathVariable Long kitchenId) {
    try {
      kitchenService.remove(kitchenId);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (EntityInUseException e) {
      // TODO: devolver corpo de resposta expecífica
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
  }
}
