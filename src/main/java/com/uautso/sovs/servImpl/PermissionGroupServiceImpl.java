package com.uautso.sovs.servImpl;

import com.uautso.sovs.model.PermissionGroup;
import com.uautso.sovs.model.UserAccount;
import com.uautso.sovs.repository.PermissionGroupRepository;
import com.uautso.sovs.service.PermissionGroupService;
import com.uautso.sovs.utils.Response;
import com.uautso.sovs.utils.ResponseCode;
import com.uautso.sovs.utils.userextractor.LoggedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PermissionGroupServiceImpl implements PermissionGroupService {

    private final PermissionGroupRepository groupRepository;
    private final LoggedUser loggedUser;

    @Autowired
    public PermissionGroupServiceImpl(PermissionGroupRepository groupRepository, LoggedUser loggedUser) {
        this.groupRepository = groupRepository;
        this.loggedUser = loggedUser;
    }

    @Override
    public Response<PermissionGroup> getPermissionGroupList() {
        try {
            UserAccount user = loggedUser.getUser();
            if (user == null) {
                log.warn("UNAUTHENTICATED USER IS TRYING TO GET PERMISSIONS GROUP, REJECTED");
                return new Response<>(true, ResponseCode.UNAUTHORIZED, "Unauthorized");
            }
            List<PermissionGroup> groupList = groupRepository.findAll();
            return new Response<>(false, ResponseCode.SUCCESS, null, groupList, "");

        } catch (Exception e) {
            log.error("ERROR WHILE GETTING PERMISSION GROUPS: ", e);
        }
        return new Response<>(true, ResponseCode.FAIL, "Failed to load permission groups");
    }
}
