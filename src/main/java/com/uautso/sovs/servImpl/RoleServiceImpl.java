package com.uautso.sovs.servImpl;


import com.uautso.sovs.dto.RoleDto;
import com.uautso.sovs.model.Permission;
import com.uautso.sovs.model.Role;
import com.uautso.sovs.model.UserAccount;
import com.uautso.sovs.repository.PermissionRepository;
import com.uautso.sovs.repository.RoleRepository;
import com.uautso.sovs.service.RoleService;
import com.uautso.sovs.utils.Response;
import com.uautso.sovs.utils.ResponseCode;
import com.uautso.sovs.utils.userextractor.LoggedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {


    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    private final LoggedUser loggedUser;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository, LoggedUser loggedUser) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.loggedUser = loggedUser;
    }

    @Override
    public Response<Role> createUpdateRole(RoleDto roleDto) {
        try {
            UserAccount user = loggedUser.getUser();
            if (user == null) {
                log.error("UNAUTHENTICATED USER IS TRYING TO CREATE/UPDATE ROLE, REJECTED");
                return new Response<>(true, ResponseCode.UNAUTHORIZED, "Unauthorized");
            }

            Role role = new Role();
            boolean isFirstTime = true;
            if (roleDto.getUuid() != null) {
                Optional<Role> roleOptional = roleRepository.findFirstByUuid(roleDto.getUuid());
                if (roleOptional.isPresent()) {
                    isFirstTime = false;
                    role = roleOptional.get();
                }
            }

            role.setName(roleDto.getRoleName().toUpperCase().replace(" ", "_"));
            role.setDisplayName(roleDto.getRoleName().replace("_", " "));

            Set<Permission> rolePermissions = role.getPermissions();

            for (String uuid : roleDto.getPermissionUuids()) {
                Optional<Permission> permissionOptional = permissionRepository.findFirstByUuid(uuid);
                if (permissionOptional.isPresent()) {
                    Permission permission = permissionOptional.get();
                    rolePermissions.add(permission);
                }
            }

            role.setPermissions(rolePermissions);

            if (isFirstTime) {
                role.setCreatedBy(user.getId());
                role.setCreatedAt(LocalDateTime.now());
            } else {
                role.setUpdatedBy(user.getId());
                role.setUpdatedAt(LocalDateTime.now());
            }

            Role savedRole = roleRepository.save(role);

            return new Response<>(false, ResponseCode.SUCCESS, savedRole, isFirstTime ? "Role created successfully" : "Role updated successfully");

        } catch (Exception e) {
            log.error("ERROR WHILE CREATING/UPDATING ROLE: ", e);
        }

        return new Response<>(true, ResponseCode.FAIL, "Failed to create role");
    }

    @Override
    public Response<Role> deleteRole(String uuid) {
        try {
            Optional<Role> roleOptional = roleRepository.findFirstByUuid(uuid);
            if (roleOptional.isEmpty())
                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "Record not found");

            Role role = roleOptional.get();
            roleRepository.delete(role);

            return new Response<>(false, ResponseCode.SUCCESS, "Deleted " + role.getDisplayName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(true, ResponseCode.FAIL, "Failed to delete role");
    }

    @Override
    public Response<Role> getRoleByUUid(String uuid) {
        try {

            Optional<Role> roleOptional = roleRepository.findFirstByUuid(uuid);

            if (roleOptional.isEmpty())
                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "Role not found");

            Role role = roleOptional.get();

            return new Response<>(false, ResponseCode.SUCCESS, role);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(true, ResponseCode.FAIL, "Failed to get role");
    }




    @Override
    public Response<Role> assignPermissionsToRole(String roleUid, List<Long> permissionIds) {
        try {

            Optional<Role> roleOptional = roleRepository.findFirstByUuid(roleUid);
            if (roleOptional.isEmpty())
                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "Role does not exist");

            Role role = roleOptional.get();
            Set<Permission> permissions = role.getPermissions();
            for (Long id : permissionIds){
                if (permissions == null)
                    permissions = new HashSet<>();
                Optional<Permission> optional = permissionRepository.findById(id);
                optional.ifPresent(permissions::add);
            }

            role.setPermissions(permissions);

            Role role1 = roleRepository.save(role);

            return new Response<>(false, ResponseCode.SUCCESS, role1, "Permissions assigned to role successfully");

        }catch (Exception e){
            e.printStackTrace();
        }
        return new Response<>(true, ResponseCode.FAIL, "Failed to assign permissions to role");
    }
}
