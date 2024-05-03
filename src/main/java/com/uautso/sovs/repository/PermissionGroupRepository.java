package com.uautso.sovs.repository;


import com.uautso.sovs.model.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, Long> {

    Optional<PermissionGroup> findFirstByUuid(String uuid);

    Optional<PermissionGroup> findFirstByGroupName(String groupName);

}
