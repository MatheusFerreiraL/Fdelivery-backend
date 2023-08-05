package github.matheusferreiral.fdeliveryapi.infrastructure.repository;

import github.matheusferreiral.fdeliveryapi.domain.model.Kitchen;
import github.matheusferreiral.fdeliveryapi.domain.repository.KitchenRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class KitchenRepositoryImpl implements KitchenRepository {

  @PersistenceContext private EntityManager manager;

  /**
   * @return list of kitchens
   */
  @Override
  public List<Kitchen> list() {
    TypedQuery<Kitchen> query = manager.createQuery("from Kitchen", Kitchen.class);
    return query.getResultList();
  }

  /**
   * @param id - kitchen id
   * @return null
   */
  @Override
  public Kitchen find(Long id) {
    return manager.find(Kitchen.class, id);
  }

  /**
   * @param kitchen - kitchen object
   * @return null
   */
  @Transactional
  @Override
  public Kitchen save(Kitchen kitchen) {
    return manager.merge(kitchen);
  }

  /**
   * @param id - kitchen id
   */
  @Transactional
  @Override
  public void remove(Long id) {
    Kitchen kitchen = find(id);
    if (kitchen == null) {
      throw new EmptyResultDataAccessException(1);
    }
    manager.remove(kitchen);
  }
}
