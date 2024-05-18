package com.uautso.sovs.repository;

import com.uautso.sovs.model.Election;
import com.uautso.sovs.utils.enums.ElectionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElectionRepository extends JpaRepository<Election,Long> {

    Optional<Election> findFirstByUuid(String uuid);

    Optional<Election> findFirstByName(String name);

    Optional<Election> findFirstByCategory(ElectionCategory category);

}
