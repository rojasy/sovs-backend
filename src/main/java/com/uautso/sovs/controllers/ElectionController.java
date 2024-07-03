package com.uautso.sovs.controllers;

import com.uautso.sovs.dto.ElectionDto;
import com.uautso.sovs.dto.UserAccountDto;
import com.uautso.sovs.model.Candidates;
import com.uautso.sovs.model.Election;
import com.uautso.sovs.model.UserAccount;
import com.uautso.sovs.service.ElectionService;
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
public class ElectionController {

    private final ElectionService electionService;
    private final PageableConfig pageableConfig;

    public ElectionController(ElectionService electionService, PageableConfig pageableConfig) {
        this.electionService = electionService;
        this.pageableConfig = pageableConfig;
    }

    @GraphQLMutation(name = "createElection")
    public Response<Election> createElection(@GraphQLArgument(name = "electionDto")ElectionDto electionDto){
        return electionService.createElection(electionDto);
    }

    @GraphQLQuery(name = "getAllElection")
    public Page<Election> getAllElection(@GraphQLArgument(name = "pageParam")PageableParam param){
        PageRequest pageable = pageableConfig.pageable(param);
        return electionService.getAllElection(pageable);
    }

    @GraphQLMutation(name = "deleteElection")
    public Response<Election> deleteElection(@GraphQLArgument(name = "uuid")String uuid){
        return electionService.deleteElectionByUuid(uuid);
    }

    @GraphQLMutation(name = "updateElection")
    public Response<Election> updateElection(@GraphQLArgument(name = "election") ElectionDto electionDto) {
        return electionService.updateElectionByUuid(electionDto);
    }

}
