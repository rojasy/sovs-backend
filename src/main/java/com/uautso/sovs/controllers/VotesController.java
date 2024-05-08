package com.uautso.sovs.controllers;

import com.uautso.sovs.dto.VotesDto;
import com.uautso.sovs.model.Votes;
import com.uautso.sovs.service.VotesService;
import com.uautso.sovs.utils.Response;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.stereotype.Service;

@Service
@GraphQLApi
public class VotesController {

    private final VotesService votesService;

    public VotesController(VotesService votesService) {
        this.votesService = votesService;
    }

    @GraphQLMutation(name = "addVote")
    public Response<Votes> addVote(@GraphQLArgument(name = "addVote")VotesDto votesDto){
        return votesService.addVote(votesDto);
    }

}
