package com.uautso.sovs.controllers;

import com.uautso.sovs.dto.CandidateDto;
import com.uautso.sovs.model.Candidates;
import com.uautso.sovs.service.CandidateService;
import com.uautso.sovs.utils.Response;
import com.uautso.sovs.utils.enums.ElectionCategory;
import com.uautso.sovs.utils.paginationutil.PageableConfig;
import com.uautso.sovs.utils.paginationutil.PageableParam;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@GraphQLApi
public class CandidateController {

    private final CandidateService candidateService;
    private final PageableConfig pageableConfig;

    public CandidateController(CandidateService candidateService, PageableConfig pageableConfig) {
        this.candidateService = candidateService;
        this.pageableConfig = pageableConfig;
    }

    @GraphQLMutation(name = "createCandidate")
    public Response<Candidates> createCandidate(@GraphQLArgument(name = "CandidateDto")CandidateDto candidateDto){
        return candidateService.createCandidate(candidateDto);
    }

    @GraphQLQuery(name = "getAllCandidateByElectionCategory")
    public Page<Candidates> getAllCandidateByElectionCategory(@GraphQLArgument(name = "category")ElectionCategory category,
                                                              @GraphQLArgument(name = "pageParam")PageableParam param){
        PageRequest pageable = pageableConfig.pageable(param);
        return  candidateService.getCandidateByElectionCategory(category,pageable);
    }

    @GraphQLQuery(name = "getAllCandidates")
    public Page<Candidates> getAllCandidates(@GraphQLArgument(name = "pageParam")PageableParam param){
        PageRequest pageable = pageableConfig.pageable(param);
        return  candidateService.getAllCandidates(pageable);
    }

}
