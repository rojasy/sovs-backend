package com.uautso.sovs.repository;

import com.uautso.sovs.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findFirstByUuid(String uuid);

    List<Permission> findAllByOrderByGroupAsc();

    Optional<Permission> findFirstByName(String name);

}
