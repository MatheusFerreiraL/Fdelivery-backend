package github.matheusferreiral.fdeliveryapi.domain.service;

import github.matheusferreiral.fdeliveryapi.domain.exception.EntityInUseException;
import github.matheusferreiral.fdeliveryapi.domain.exception.EntityNotFoundException;
import github.matheusferreiral.fdeliveryapi.domain.model.City;
import github.matheusferreiral.fdeliveryapi.domain.model.State;
import github.matheusferreiral.fdeliveryapi.domain.repository.CityRepository;
import github.matheusferreiral.fdeliveryapi.domain.repository.StateRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CityService {
  @Autowired private CityRepository cityRepository;
  @Autowired private StateRepository stateRepository;

  public List<City> list() {
    return cityRepository.findAll();
  }

  public Optional<City> find(long cityId) {
    return cityRepository.findById(cityId);
  }

  public City save(City newCity) {
    Long stateId = newCity.getState().getId();
    State existingState =
        stateRepository
            .findById(stateId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("Não existe cadastro de estado com o código %d", stateId)));
    newCity.setState(existingState);
    return cityRepository.save(newCity);
  }

  public void remove(Long cityId) {
    try {
      Optional<City> cityExists = this.find(cityId);
      if (cityExists.isEmpty()) {
        throw new EntityNotFoundException(
            String.format("Não existe um cadastro de cidade com código %d", cityId));
      }
      cityRepository.deleteById(cityId);
    } catch (DataIntegrityViolationException e) {
      throw new EntityInUseException(
          String.format("Cidade de código %d não pode ser removida pois está em uso", cityId));
    }
  }
}
