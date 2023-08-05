package github.matheusferreiral.fdeliveryapi.domain.repository;

import github.matheusferreiral.fdeliveryapi.domain.model.Permission;
import java.util.List;

public interface PermissionRepository {

  List<Permission> list();

  Permission find(Long id);

  Permission save(Permission paymentMethod);

  void remove(Permission paymentMethod);
}
