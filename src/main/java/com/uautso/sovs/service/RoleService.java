package com.uautso.sovs.service;


import com.uautso.sovs.dto.RoleDto;
import com.uautso.sovs.model.Role;
import com.uautso.sovs.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface RoleService {

    Response<Role> createUpdateRole(RoleDto roleDto);
    Response<Role> deleteRole(String uuid);
    Response<Role> getRoleByUUid(String uuid);
    Response<Role> assignPermissionsToRole(String roleUid, List<Long> permissionIds);

}
