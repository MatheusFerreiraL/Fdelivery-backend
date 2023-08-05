package github.matheusferreiral.fdeliveryapi.domain.repository;

import github.matheusferreiral.fdeliveryapi.domain.model.Restaurant;
import java.util.List;

public interface RestaurantRepository {
  List<Restaurant> list();
  Restaurant find(Long id);
  Restaurant save(Restaurant restaurant);
  void remove(Restaurant restaurant);
}
