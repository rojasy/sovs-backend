package com.uautso.sovs.controllers;



import com.uautso.sovs.dto.UserAccountDto;
import com.uautso.sovs.model.Candidates;
import com.uautso.sovs.model.UserAccount;
import com.uautso.sovs.service.AuthService;
import com.uautso.sovs.service.UserAccountService;
import com.uautso.sovs.utils.Response;
import com.uautso.sovs.utils.enums.ElectionCategory;
import com.uautso.sovs.utils.paginationutil.PageableConfig;
import com.uautso.sovs.utils.paginationutil.PageableParam;
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
public class UserAccountController {

    private final PageableConfig pageableConfig;
    private final UserAccountService accountService;
    private final AuthService authService;

    @Autowired
    public UserAccountController(PageableConfig pageableConfig, UserAccountService accountService, AuthService authService) {
        this.pageableConfig = pageableConfig;
        this.accountService = accountService;
        this.authService = authService;
    }

   // @PreAuthorize("hasAnyRole('ROLE_CREATE_USER','ROLE_UPDATE_USER')")
    @GraphQLMutation(name = "createUpdateUser")
    public Response<UserAccount> createUpdateUser(@GraphQLArgument(name = "user") UserAccountDto account) {
        return accountService.createUpdateUser(account);
    }

  //  @PreAuthorize("hasAnyRole('ROLE_DELETE_USER')")
    @GraphQLMutation(name = "deleteUser")
    public Response<UserAccount> deleteUser(@GraphQLArgument(name = "uuid")String uuid){
        return accountService.deleteUserAccount(uuid);
    }

    //@PreAuthorize("hasAnyRole('ROLE_VIEW_USER')")
    @GraphQLQuery(name = "getUserByUid")
    public Response<UserAccount> getUserByUid(@GraphQLArgument(name = "uuid")String uid){
        return accountService.getUserByUuid(uid);
    }

 //   @PreAuthorize("hasAnyRole('ROLE_ACTIVATE_USER')")
    @GraphQLMutation(name = "activateUser")
    public Response<UserAccount> activateUser(@GraphQLArgument(name = "uuid")String uid){
        return accountService.activateUser(uid);
    }

  //  @PreAuthorize("hasAnyRole('ROLE_DEACTIVATE_USER')")
    @GraphQLMutation(name = "deactivateUser")
    public Response<UserAccount> deactivateUser(@GraphQLArgument(name = "uuid")String uid){
        return accountService.deactivateUser(uid);
    }

    @GraphQLQuery(name = "getAllUsers")
    public Page<UserAccount> getAllUsers(@GraphQLArgument(name = "pageParam") PageableParam param){
        PageRequest pageable = pageableConfig.pageable(param);
        return  accountService.getAllUsers(pageable);
    }

    @PreAuthorize("hasAnyRole('ROLE_UPDATE_USER')")
    @GraphQLMutation(name = "assignRoleToUser")
    public Response<UserAccount> assignRoleToUser(@GraphQLArgument(name = "userUuid")String uuid,
                                                  @GraphQLArgument(name = "roleIds")List<Long> roleIds){
        return accountService.setRolesToUser(uuid, roleIds);
    }

    @GraphQLQuery(name = "getLoggedInUser")
    public Response<UserAccount> getLoggedInUser(){
        return authService.getLoggedUser();
    }

}
