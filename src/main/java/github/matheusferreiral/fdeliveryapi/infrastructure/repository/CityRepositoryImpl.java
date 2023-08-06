package github.matheusferreiral.fdeliveryapi.infrastructure.repository;

import github.matheusferreiral.fdeliveryapi.domain.model.City;
import github.matheusferreiral.fdeliveryapi.domain.model.State;
import github.matheusferreiral.fdeliveryapi.domain.repository.CityRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class CityRepositoryImpl implements CityRepository {

  @PersistenceContext private EntityManager manager;

  /**
   * @return result list with all cities
   */
  @Override
  public List<City> list() {
    TypedQuery<City> query = manager.createQuery("from City ", City.class);
    return query.getResultList();
  }

  /**
   * @param id - state id
   * @return city - specif city searched
   */
  @Override
  public City find(Long id) {
    return manager.find(City.class, id);
  }

  /**
   * @param city - city that will be saved on the database
   * @return updated city
   */
  @Transactional
  @Override
  public City save(City city) {
    return manager.merge(city);
  }

  /**
   * @param id - id of the city that will be removed
   */
  @Transactional
  @Override
  public void remove(Long id) {
    City city = find(id);
    if (city == null) {
      throw new EmptyResultDataAccessException(1);
    }
    manager.remove(city);
  }
}
