package com.uautso.sovs.repository;

import com.uautso.sovs.model.Votes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotesRepository extends JpaRepository<Votes,Long> {

    Optional<Votes> findFirstByCandidatesUuid(String uuid);

    Optional<Votes> findByUserAccountUuidAndCandidatesUuidAndElectionUuid(String uuid,String candidateUuid,String electionUuid);

    long countByCandidatesUuidAndElectionUuid(String candidatesUuid, String electionUuid);

}
