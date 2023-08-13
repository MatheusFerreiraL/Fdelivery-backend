package github.matheusferreiral.fdeliveryapi.domain.repository;

import github.matheusferreiral.fdeliveryapi.domain.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {}
