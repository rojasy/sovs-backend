package com.uautso.sovs.repository;

import com.uautso.sovs.model.Votes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotesRepository extends JpaRepository<Votes,Long> {

    Optional<Votes> findFirstByUuid(String uuid);

}
