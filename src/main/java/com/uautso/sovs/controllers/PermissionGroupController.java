package com.uautso.sovs.controllers;


import com.uautso.sovs.model.PermissionGroup;
import com.uautso.sovs.service.PermissionGroupService;
import com.uautso.sovs.utils.Response;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@GraphQLApi
public class PermissionGroupController {
    private final PermissionGroupService permissionGroupService;

    @Autowired
    public PermissionGroupController(PermissionGroupService permissionGroupService) {
        this.permissionGroupService = permissionGroupService;
    }

    @GraphQLQuery(name = "getPermissionGroupList")
    public Response<PermissionGroup> getPermissionGroupList(){
        return permissionGroupService.getPermissionGroupList();
    }

}