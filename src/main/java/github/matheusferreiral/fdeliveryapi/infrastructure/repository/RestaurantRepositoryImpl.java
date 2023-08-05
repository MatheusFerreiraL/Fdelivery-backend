package github.matheusferreiral.fdeliveryapi.infrastructure.repository;

import github.matheusferreiral.fdeliveryapi.domain.model.Restaurant;
import github.matheusferreiral.fdeliveryapi.domain.repository.RestaurantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

  @PersistenceContext private EntityManager manager;
  /**
   * @return - list of restaurants
   */
  @Override
  public List<Restaurant> list() {
    TypedQuery<Restaurant> query = manager.createQuery("from Restaurant", Restaurant.class);
    return query.getResultList();
  }

  /**
   * @param id
   * @return restaurant - searched restaurant by id
   */
  @Override
  public Restaurant find(Long id) {
    return manager.find(Restaurant.class, id);
  }

  /**
   * @param restaurant
   * @return resstaurant - updated instance of the restaurant
   */
  @Transactional
  @Override
  public Restaurant save(Restaurant restaurant) {
    return manager.merge(restaurant);
  }

  /**
   * @param restaurant
   */
  @Transactional
  @Override
  public void remove(Restaurant restaurant) {
    restaurant = find(restaurant.getId());
    manager.remove(restaurant);
  }
}
