package com.uautso.sovs.service;


import com.uautso.sovs.model.PermissionGroup;
import com.uautso.sovs.utils.Response;

public interface PermissionGroupService {

    Response<PermissionGroup> getPermissionGroupList();

}