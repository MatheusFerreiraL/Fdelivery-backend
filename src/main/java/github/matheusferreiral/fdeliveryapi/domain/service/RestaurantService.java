package github.matheusferreiral.fdeliveryapi.domain.service;

import github.matheusferreiral.fdeliveryapi.domain.exception.EntityNotFoundException;
import github.matheusferreiral.fdeliveryapi.domain.model.Kitchen;
import github.matheusferreiral.fdeliveryapi.domain.model.Restaurant;
import github.matheusferreiral.fdeliveryapi.domain.repository.KitchenRepository;
import github.matheusferreiral.fdeliveryapi.domain.repository.RestaurantRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

  @Autowired private RestaurantRepository restaurantRepository;
  @Autowired private KitchenRepository kitchenRepository;

  public List<Restaurant> list() {
    return restaurantRepository.list();
  }

  public Restaurant save(Restaurant newRestaurant) {
    Long kitchenId = newRestaurant.getKitchen().getId();
    Kitchen kitchen = kitchenRepository.find(kitchenId);

    if (kitchen == null) {
      throw new EntityNotFoundException(
          String.format("Não existe cadastro de cozinha com código %d", kitchenId));
    }
    newRestaurant.setKitchen(kitchen);
    return restaurantRepository.save(newRestaurant);
  }

  public Restaurant find(long restaurantId) {
    return restaurantRepository.find(restaurantId);
  }
}
