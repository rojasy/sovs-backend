package com.uautso.sovs.controllers;

import com.uautso.sovs.dto.CandidateDto;
import com.uautso.sovs.model.Candidates;
import com.uautso.sovs.service.CandidateService;
import com.uautso.sovs.utils.Response;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.stereotype.Service;

@Service
@GraphQLApi
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GraphQLMutation(name = "createCandidate")
    public Response<Candidates> createCandidate(@GraphQLArgument(name = "CandidateDto")CandidateDto candidateDto){
        return candidateService.createCandidate(candidateDto);
    }

}
