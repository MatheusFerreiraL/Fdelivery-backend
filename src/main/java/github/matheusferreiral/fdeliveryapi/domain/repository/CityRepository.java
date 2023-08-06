package github.matheusferreiral.fdeliveryapi.domain.repository;

import github.matheusferreiral.fdeliveryapi.domain.model.City;
import java.util.List;

public interface CityRepository {

  List<City> list();

  City find(Long id);

  City save(City city);

  void remove(Long id);
}
