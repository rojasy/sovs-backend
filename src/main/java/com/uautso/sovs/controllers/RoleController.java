package com.uautso.sovs.controllers;


import com.uautso.sovs.dto.RoleDto;
import com.uautso.sovs.model.Role;
import com.uautso.sovs.service.RoleService;
import com.uautso.sovs.utils.Response;
import com.uautso.sovs.utils.paginationutil.PageableConfig;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@GraphQLApi
public class RoleController {


    private final RoleService roleService;
    private final PageableConfig config;

    @Autowired
    public RoleController(RoleService roleService, PageableConfig config) {
        this.roleService = roleService;
        this.config = config;
    }


    @GraphQLMutation(name = "createUpdateRole")
    public Response<Role> createUpdateRole(@GraphQLArgument(name = "role") RoleDto roleDto) {
        return roleService.createUpdateRole(roleDto);
    }

    @GraphQLMutation(name = "deleteRole")
    public Response<Role> deleteRole(@GraphQLArgument(name = "uid") String uuid) {
        return roleService.deleteRole(uuid);
    }

    @GraphQLQuery(name = "getRoleByUUid")
    public Response<Role> getRoleByUUid(@GraphQLArgument(name = "uid") String uuid) {
        return roleService.getRoleByUUid(uuid);
    }


    @PreAuthorize("hasAnyRole('ROLE_UPDATE_ROLE')")
    @GraphQLMutation(name = "assignPermissionsToRole")
    public Response<Role> assignPermissionsToRole(@GraphQLArgument(name = "roleUid")String roleUuid,
                                                  @GraphQLArgument(name = "permissionIds") List<Long> permissionIds){
        return roleService.assignPermissionsToRole(roleUuid, permissionIds);
    }

}
