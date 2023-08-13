package github.matheusferreiral.fdeliveryapi.domain.repository;

import github.matheusferreiral.fdeliveryapi.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {}
