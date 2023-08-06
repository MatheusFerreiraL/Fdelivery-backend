package github.matheusferreiral.fdeliveryapi.infrastructure.repository;

import github.matheusferreiral.fdeliveryapi.domain.model.Kitchen;
import github.matheusferreiral.fdeliveryapi.domain.model.State;
import github.matheusferreiral.fdeliveryapi.domain.repository.StateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class StateRepositoryImpl implements StateRepository {

  @PersistenceContext private EntityManager manager;

  /**
   * @return result list with all states
   */
  @Override
  public List<State> list() {
    TypedQuery<State> query = manager.createQuery("from State ", State.class);
    return query.getResultList();
  }

  /**
   * @param id - state id
   * @return state - specif state searched
   */
  @Override
  public State find(Long id) {
    return manager.find(State.class, id);
  }

  /**
   * @param state - state that will be maintained on the database
   * @return updated state
   */
  @Transactional
  @Override
  public State save(State state) {
    return manager.merge(state);
  }

  /**
   * @param id - id of state that will be removed
   */
  @Transactional
  @Override
  public void remove(Long id) {
    State state = find(id);
    if (state == null) {
      throw new EmptyResultDataAccessException(1);
    }
    manager.remove(state);
  }
}
