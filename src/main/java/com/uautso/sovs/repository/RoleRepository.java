package com.uautso.sovs.repository;

import com.uautso.sovs.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findFirstByUuid(String uuid);

    Optional<Role> findFirstByName(String name);

    Page<Role> findAllByActiveTrue(Pageable pageable);

}
