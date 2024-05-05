package com.uautso.sovs.repository;

import com.uautso.sovs.model.Candidates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatesRepository extends JpaRepository<Candidates,Long> {
    Optional<Candidates> findFirstByTitle(String title);

    Optional<Candidates> findFirstByUuid(String uuid);
}
