package github.matheusferreiral.fdeliveryapi.infrastructure.repository;

import github.matheusferreiral.fdeliveryapi.domain.model.Permission;
import github.matheusferreiral.fdeliveryapi.domain.repository.PermissionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionRepositoryImpl implements PermissionRepository {

  @PersistenceContext private EntityManager manager;

  /**
   * @return result list with all permissions
   */
  @Override
  public List<Permission> list() {
    TypedQuery<Permission> query = manager.createQuery("from Permission ", Permission.class);
    return query.getResultList();
  }

  /**
   * @param id - permission id
   * @return permission - specif permission searched
   */
  @Override
  public Permission find(Long id) {
    return manager.find(Permission.class, id);
  }

  /**
   * @param permission
   * @return updated permission
   */
  @Transactional
  @Override
  public Permission save(Permission permission) {
    return manager.merge(permission);
  }

  /**
   * @param permission - permission that will be removed
   */
  @Transactional
  @Override
  public void remove(Permission permission) {
    manager.remove(permission);
  }
}
