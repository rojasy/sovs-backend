package com.uautso.sovs.service;


import com.uautso.sovs.dto.LoginDto;
import com.uautso.sovs.dto.LoginResponseDto;
import com.uautso.sovs.model.UserAccount;
import com.uautso.sovs.utils.Response;

public interface AuthService {

    Response<LoginResponseDto> login(LoginDto loginDto);
    Response<LoginResponseDto> revokeToken(String refreshToken);
    Response<Boolean> forgetPassword(String email);
    Response<UserAccount> getLoggedUser();

}
