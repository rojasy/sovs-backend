package com.uautso.sovs.service;

import com.uautso.sovs.dto.ElectionDto;
import com.uautso.sovs.model.Election;
import com.uautso.sovs.utils.Response;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public interface ElectionService {

    Page<Election> getAllElection(Pageable pageable);

    Response<Election> createElection(ElectionDto electionDto);

    Response<Election> getElectionByUuid(String uuid);

    

}
