package github.matheusferreiral.fdeliveryapi.api.controller;

import github.matheusferreiral.fdeliveryapi.domain.exception.EntityInUseException;
import github.matheusferreiral.fdeliveryapi.domain.exception.EntityNotFoundException;
import github.matheusferreiral.fdeliveryapi.domain.model.Kitchen;
import github.matheusferreiral.fdeliveryapi.domain.service.KitchenService;
import java.util.List;
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

  @GetMapping("/list")
  public List<Kitchen> list() {
    return kitchenService.list();
  }

  @GetMapping("/{kitchenId}")
  public ResponseEntity<Kitchen> find(@PathVariable("kitchenId") Long id) {
    Kitchen kitchen = kitchenService.find(id);

    if (kitchen == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    return ResponseEntity.status(HttpStatus.OK).body(kitchen);
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody Kitchen newKitchen) {
    newKitchen = kitchenService.save(newKitchen);
    return ResponseEntity.status(HttpStatus.CREATED).body(newKitchen);
  }

  @PutMapping("/{kitchenId}")
  public ResponseEntity<Kitchen> update(
      @PathVariable Long kitchenId, @RequestBody Kitchen kitchen) {
    Kitchen existingKitchen = kitchenService.find(kitchenId);
    if (existingKitchen != null) {
      BeanUtils.copyProperties(
          kitchen, existingKitchen, "id"); // é o mesmo que dar um set em cada uma das propriedades
      existingKitchen = kitchenService.save(existingKitchen);
      return ResponseEntity.status(HttpStatus.OK).body(existingKitchen);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @DeleteMapping("{kitchenId}")
  public ResponseEntity<Kitchen> delete(@PathVariable Long kitchenId) {
    try {
      kitchenService.remove(kitchenId);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (EntityInUseException e) {
      // TODO: devolver corpo de resposta expecífica
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }
}
