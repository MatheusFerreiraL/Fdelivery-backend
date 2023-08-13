package github.matheusferreiral.fdeliveryapi.domain.service;

import github.matheusferreiral.fdeliveryapi.domain.exception.EntityInUseException;
import github.matheusferreiral.fdeliveryapi.domain.exception.EntityNotFoundException;
import github.matheusferreiral.fdeliveryapi.domain.model.State;
import github.matheusferreiral.fdeliveryapi.domain.repository.StateRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class StateService {

  @Autowired private StateRepository stateRepository;

  public List<State> list() {
    return stateRepository.findAll();
  }

  public Optional<State> find(Long stateId) {
    return stateRepository.findById(stateId);
  }

  public State save(State newState) {
    return stateRepository.save(newState);
  }

  public void remove(Long stateId) {
    try {
      Optional<State> existingState = this.find(stateId);
      if (existingState.isEmpty()) {
        throw new EntityNotFoundException(
            String.format("Não existe cadastro de estado com código %d", stateId));
      }
      stateRepository.deleteById(stateId);
    } catch (DataIntegrityViolationException e) {
      throw new EntityInUseException(
          String.format("Estado de código %d não pode ser removido pois está em uso", stateId));
    }
  }
}
