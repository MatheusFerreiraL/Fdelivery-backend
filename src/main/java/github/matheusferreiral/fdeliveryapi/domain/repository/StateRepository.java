package github.matheusferreiral.fdeliveryapi.domain.repository;

import github.matheusferreiral.fdeliveryapi.domain.model.State;
import java.util.List;

public interface StateRepository {
  List<State> list();

  State find(Long id);

  State save(State state);

  void remove(Long id);
}
