package github.matheusferreiral.fdeliveryapi.domain.service;

import github.matheusferreiral.fdeliveryapi.domain.exception.EntityInUseException;
import github.matheusferreiral.fdeliveryapi.domain.exception.EntityNotFoundException;
import github.matheusferreiral.fdeliveryapi.domain.model.Kitchen;
import github.matheusferreiral.fdeliveryapi.domain.repository.KitchenRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class KitchenService {

  @Autowired private KitchenRepository kitchenRepository;

  public List<Kitchen> list() {
    return kitchenRepository.findAll();
  }

  public Optional<Kitchen> find(Long kitchenId) {
    return kitchenRepository.findById(kitchenId);
  }

  public Kitchen save(Kitchen kitchen) {
    return kitchenRepository.save(kitchen);
  }

  public void remove(Long kitchenId) {
    try {
      Optional<Kitchen> kitchenExists = this.find(kitchenId);
      if (kitchenExists.isEmpty()) {
        throw new EntityNotFoundException(
            String.format("Não existe um cadastro de cozinha com o código %d.", kitchenId));
      }
      kitchenRepository.deleteById(kitchenId);
    } catch (DataIntegrityViolationException e) {
      throw new EntityInUseException(
          String.format("Cozinha de código %d não pode ser removida pois está em uso.", kitchenId));
    }
  }
}
