package github.matheusferreiral.fdeliveryapi.domain.service;

import github.matheusferreiral.fdeliveryapi.domain.exception.EntityInUseException;
import github.matheusferreiral.fdeliveryapi.domain.exception.EntityNotFoundException;
import github.matheusferreiral.fdeliveryapi.domain.model.Kitchen;
import github.matheusferreiral.fdeliveryapi.domain.repository.KitchenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class KitchenService {

  @Autowired private KitchenRepository kitchenRepository;

  public Kitchen save(Kitchen kitchen) {
    return kitchenRepository.save(kitchen);
  }

  public void remove(Long kitchenId) {
    try {
      kitchenRepository.remove(kitchenId);
    } catch (EmptyResultDataAccessException e) {
      throw new EntityNotFoundException(
          String.format("Não existe um cadastro de cozinha com o código %d.", kitchenId));
    } catch (DataIntegrityViolationException e) {
      throw new EntityInUseException(
          String.format("Cozinha de código %d não pode ser removida pois está em uso.", kitchenId));
    }
  }
}
