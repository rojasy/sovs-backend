package com.uautso.sovs.service;

import com.uautso.sovs.dto.CandidateDto;
import com.uautso.sovs.model.Candidates;
import com.uautso.sovs.utils.Response;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public interface CandidateService {

    Page<Candidates> getAllCandidates(Pageable pageable);

    Response<Candidates> createCandidate(String uuid);

    Response<Candidates> deleteCandidate(String uuid);

    Response<Candidates> updateCandidate(CandidateDto candidateDto);

    Response<Candidates> getCandidateByUuid(String uuid);

}