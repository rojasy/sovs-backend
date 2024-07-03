package com.uautso.sovs.service;

import com.uautso.sovs.dto.ElectionDto;
import com.uautso.sovs.model.Election;
import com.uautso.sovs.utils.Response;
import com.uautso.sovs.utils.enums.ElectionCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ElectionService {

    Page<Election> getAllElection(Pageable pageable);

    Response<Election> createElection(ElectionDto electionDto);

    Response<Election> getElectionByUuid(String uuid);

    Response<Election> getElectionByCategory(ElectionCategory category);

    Response<Election> deleteElectionByUuid(String uuid);

    Response<Election> updateElectionByUuid(ElectionDto electionDto);

    

}
