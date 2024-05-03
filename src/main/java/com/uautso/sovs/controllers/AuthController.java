package com.uautso.sovs.controllers;

import com.ngaiza.test.dto.LoginDto;
import com.ngaiza.test.dto.LoginResponseDto;
import com.ngaiza.test.service.AuthService;
import com.ngaiza.test.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestParam MultiValueMap<String, String> body){
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(body.getFirst("username"));
        loginDto.setPassword(body.getFirst("password"));
        Response<LoginResponseDto> response = authService.login(loginDto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/user")
    public Principal getRoles(Principal principal){
        return principal;
    }


}
