package com.uautso.sovs.controllers;


import com.uautso.sovs.dto.LoginDto;
import com.uautso.sovs.dto.LoginResponseDto;
import com.uautso.sovs.service.AuthService;
import com.uautso.sovs.utils.Response;
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
        System.out.println("Here login");
        LoginDto loginDto = new LoginDto();
        System.out.println(loginDto.getUsername());
        loginDto.setUsername(body.getFirst("username"));
        System.out.println(loginDto.getPassword());
        loginDto.setPassword(body.getFirst("password"));
        Response<LoginResponseDto> response = authService.login(loginDto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/user")
    public Principal getRoles(Principal principal){
        return principal;
    }


}
