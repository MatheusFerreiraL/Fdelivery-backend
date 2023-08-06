package github.matheusferreiral.fdeliveryapi.domain.service;

import github.matheusferreiral.fdeliveryapi.domain.exception.EntityInUseException;
import github.matheusferreiral.fdeliveryapi.domain.exception.EntityNotFoundException;
import github.matheusferreiral.fdeliveryapi.domain.model.State;
import github.matheusferreiral.fdeliveryapi.domain.repository.StateRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class StateService {

  @Autowired private StateRepository stateRepository;

  /*
   * list
   * find
   * save
   * update
   * remove
   */
  public List<State> list() {
    return stateRepository.list();
  }

  public State find(Long stateId) {
    return stateRepository.find(stateId);
  }

  public State save(State newState) {
    return stateRepository.save(newState);
  }

  public void remove(Long stateId) {
    try {
      stateRepository.remove(stateId);
    } catch (EmptyResultDataAccessException e) {
      throw new EntityNotFoundException(
          String.format("Não existe cadastro de estado com código %d", stateId));
    } catch (DataIntegrityViolationException e) {
      throw new EntityInUseException(
          String.format("Estado de código %d não pode ser removido pois está em uso", stateId));
    }
  }
}
