package github.matheusferreiral.fdeliveryapi.domain.repository;

import github.matheusferreiral.fdeliveryapi.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {}
