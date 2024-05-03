package com.uautso.sovs.utils;



import com.uautso.sovs.model.Permission;

import java.util.ArrayList;
import java.util.List;


public class Permissions {

    public static <Permission> List<Permission> permissionsList() {

        List<Permission> permissions = new ArrayList<>();

        //USER_MANAGEMENT
        permissions.add(new Permission("ROLE_CREATE_USER", "CREATE USER", "USER_MANAGEMENT"));
        permissions.add(new Permission("ROLE_UPDATE_USER", "UPDATE USER", "USER_MANAGEMENT"));
        permissions.add(new Permission("ROLE_VIEW_USER", "VIEW USER", "USER_MANAGEMENT"));
        permissions.add(new Permission("ROLE_LIST_USERS", "LIST USERS", "USER_MANAGEMENT"));
        permissions.add(new Permission("ROLE_DELETE_USER", "DELETE USER", "USER_MANAGEMENT"));
        permissions.add(new Permission("ROLE_ACTIVATE_USER", "ACTIVATE USER", "USER_MANAGEMENT"));
        permissions.add(new Permission("ROLE_DEACTIVATE_USER", "DEACTIVATE USER", "USER_MANAGEMENT"));


        //ROLE_MANAGEMENT
        permissions.add(new Permission("ROLE_CREATE_ROLE", "CREATE ROLE", "ROLE_MANAGEMENT"));
        permissions.add(new Permission("ROLE_UPDATE_ROLE", "UPDATE ROLE", "ROLE_MANAGEMENT"));
        permissions.add(new Permission("ROLE_VIEW_ROLE", "VIEW ROLE", "ROLE_MANAGEMENT"));
        permissions.add(new Permission("ROLE_LIST_ROLES", "LIST ROLES", "ROLE_MANAGEMENT"));
        permissions.add(new Permission("ROLE_DELETE_ROLE", "DELETE ROLE", "ROLE_MANAGEMENT"));


        return permissions;
    }


    public static List<Permission> primeMinisterPermission() {
        List<Permission> permissions = new ArrayList<>();

        return permissions;
    }

}