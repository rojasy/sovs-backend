package com.uautso.sovs.controllers;

import com.uautso.sovs.dto.DashboardResponse;
import com.uautso.sovs.dto.TotalVotesDto;
import com.uautso.sovs.dto.VotesDto;
import com.uautso.sovs.model.Candidates;
import com.uautso.sovs.model.Votes;
import com.uautso.sovs.service.VotesService;
import com.uautso.sovs.utils.Response;
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
public class VotesController {

    private final VotesService votesService;
    private final PageableConfig pageableConfig;

    public VotesController(VotesService votesService, PageableConfig pageableConfig) {
        this.votesService = votesService;
        this.pageableConfig = pageableConfig;
    }

    @GraphQLMutation(name = "addVote")
    public Response<Votes> addVote(@GraphQLArgument(name = "addVote")VotesDto votesDto){
        return votesService.addVote(votesDto);
    }

    @GraphQLQuery(name = "getCandidateVotes")
    public Response<Candidates> getCandidateVotes(@GraphQLArgument(name = "totalVotes") TotalVotesDto totalVotesDto){
        return  votesService.getTotalVotes(totalVotesDto);
    }

    @GraphQLQuery(name = "getDashboard")
    public Response<DashboardResponse> getDashboard(){
        return  votesService.getDashboard();
    }

}
