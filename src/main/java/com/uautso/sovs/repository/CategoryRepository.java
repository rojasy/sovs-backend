package com.uautso.sovs.repository;

import com.uautso.sovs.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findFirstByUuid(String uuid);
    Optional<Category> findFirstByName(String name);

}
