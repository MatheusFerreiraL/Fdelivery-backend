package github.matheusferreiral.fdeliveryapi.api.controller;

import github.matheusferreiral.fdeliveryapi.domain.exception.EntityInUseException;
import github.matheusferreiral.fdeliveryapi.domain.exception.EntityNotFoundException;
import github.matheusferreiral.fdeliveryapi.domain.model.State;
import github.matheusferreiral.fdeliveryapi.domain.service.StateService;
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
@RequestMapping("/states")
public class StateController {

  @Autowired private StateService stateService;

  @GetMapping("/list")
  public List<State> list() {
    return stateService.list();
  }

  @GetMapping("/{stateId}")
  public ResponseEntity<State> find(@PathVariable long stateId) {
    State existingState = stateService.find(stateId);
    if (existingState != null) {
      return ResponseEntity.status(HttpStatus.OK).body(existingState);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @PostMapping("/register")
  public ResponseEntity<State> register(@RequestBody State newState) {
    newState = stateService.save(newState);
    return ResponseEntity.status(HttpStatus.CREATED).body(newState);
  }

  @PutMapping("/{stateId}")
  public ResponseEntity<?> update(
      @PathVariable("stateId") long id, @RequestBody State updatedState) {
    State existingState = stateService.find(id);

    if (existingState != null) {
      BeanUtils.copyProperties(updatedState, existingState, "id");
      existingState = stateService.save(existingState);
      return ResponseEntity.status(HttpStatus.OK).body(existingState);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @DeleteMapping("/{stateId}")
  public ResponseEntity<State> delete(@PathVariable("stateId") Long id) {
    try {
      stateService.remove(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (EntityInUseException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }
}
