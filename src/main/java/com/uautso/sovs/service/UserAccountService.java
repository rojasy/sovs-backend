package com.uautso.sovs.service;

import com.uautso.sovs.dto.UserAccountDto;
import com.uautso.sovs.model.UserAccount;
import com.uautso.sovs.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserAccountService {

    Response<UserAccount> createUpdateUser(UserAccountDto accountDto);

    Response<UserAccount> deleteUserAccount(String uuid);



    Page<UserAccount> getAllUsers(Pageable pageable);

    Response<UserAccount> getUserByUuid(String uuid);

    Response<UserAccount> setRolesToUser(String serUuid, List<Long> roleIds);


    Response<UserAccount> changePassword(String oldPassword, String newPassword);


    Response<UserAccount> activateUser(String uuid);
    Response<UserAccount> deactivateUser(String uuid);
}

