package github.matheusferreiral.fdeliveryapi.domain.repository;

import github.matheusferreiral.fdeliveryapi.domain.model.Kitchen;
import java.util.List;

public interface KitchenRepository {
  List<Kitchen> list();

  Kitchen find(Long id);

  Kitchen save(Kitchen kitchen);

  void remove(Long id);
}
