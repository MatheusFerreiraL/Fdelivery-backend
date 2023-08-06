package github.matheusferreiral.fdeliveryapi.domain.service;

import github.matheusferreiral.fdeliveryapi.domain.exception.EntityInUseException;
import github.matheusferreiral.fdeliveryapi.domain.exception.EntityNotFoundException;
import github.matheusferreiral.fdeliveryapi.domain.model.City;
import github.matheusferreiral.fdeliveryapi.domain.model.State;
import github.matheusferreiral.fdeliveryapi.domain.repository.CityRepository;
import github.matheusferreiral.fdeliveryapi.domain.repository.StateRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CityService {
  @Autowired private CityRepository cityRepository;
  @Autowired private StateRepository stateRepository;

  public List<City> list() {
    return cityRepository.list();
  }

  public City find(long cityId) {
    return cityRepository.find(cityId);
  }

  public City save(City newCity) {
    Long stateId = newCity.getState().getId();
    State existingState = stateRepository.find(stateId);

    if (existingState == null) {
      throw new EntityNotFoundException(
          String.format("Não existe cadastro de estado com o código %d", stateId));
    }
    newCity.setState(existingState);
    return cityRepository.save(newCity);
  }

  public void remove(Long cityId) {
    try {
      cityRepository.remove(cityId);
    } catch (EmptyResultDataAccessException e) {
      throw new EntityNotFoundException(
          String.format("Não existe um cadastro de cidade com código %d", cityId));
    } catch (DataIntegrityViolationException e) {
      throw new EntityInUseException(
          String.format("Cidade de código %d não pode ser removida pois está em uso", cityId));
    }
  }
}
