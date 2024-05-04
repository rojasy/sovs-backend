package com.uautso.sovs.servImpl;


import com.uautso.sovs.dto.LoginDto;
import com.uautso.sovs.dto.LoginResponseDto;
import com.uautso.sovs.jwt.JWTUtils;
import com.uautso.sovs.model.UserAccount;
import com.uautso.sovs.repository.UserAccountRepository;
import com.uautso.sovs.service.AuthService;
import com.uautso.sovs.utils.Response;
import com.uautso.sovs.utils.ResponseCode;
import com.uautso.sovs.utils.userextractor.LoggedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserAccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoggedUser loggedUser;
    private final JWTUtils jwtUtils;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserAccountRepository accountRepository, PasswordEncoder passwordEncoder, LoggedUser loggedUser, JWTUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.loggedUser = loggedUser;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Response<LoginResponseDto> login(LoginDto loginDto) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtUtils.generateJwtToken(authentication);
            String refreshToken = UUID.randomUUID().toString();

            Optional<UserAccount> accountOptional = accountRepository.findFirstByUsername(authentication.getName());

            return getLoginResponseResponse(accountOptional, jwtToken, refreshToken);


        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(true, ResponseCode.FAIL, e.getMessage());
        }
    }

    @Override
    public Response<LoginResponseDto> revokeToken(String refreshToken) {
        try {

            Optional<UserAccount> accountOptional = accountRepository.findFirstByRefreshToken(refreshToken);
            if (accountOptional.isEmpty()) {
                return new Response<>(true, ResponseCode.FAIL, null, null, "Invalid refresh token");
            }


            return getLoginResponseResponse(accountOptional, "", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(true, ResponseCode.FAIL, null, null, "Failed to refresh token");
    }

    @Override
    public Response<Boolean> forgetPassword(String email) {
        return null;
    }

    @Override
    public Response<UserAccount> getLoggedUser() {
        try {
            UserAccount user = loggedUser.getUser();
            if (user == null)
                return new Response<>(true, ResponseCode.UNAUTHORIZED, "Unauthorized");
            return new Response<>(false, ResponseCode.SUCCESS, user, "Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(true, ResponseCode.FAIL, "Operation Unsuccessful");
    }

    @NotNull
    private Response<LoginResponseDto> getLoginResponseResponse(Optional<UserAccount> accountOptional, String jwtToken, String refreshToken) {
        if (accountOptional.isPresent()) {

            UserAccount account = accountOptional.get();
            account.setRefreshToken(refreshToken);
            account.setRefreshTokenCreatedAt(LocalDateTime.now());
            accountRepository.save(account);
            LoginResponseDto response = new LoginResponseDto(
                    jwtToken,
                    refreshToken,
                    "Bearer",
                    account.getUsername(),
                    account.getUuid()
            );

            return new Response<>(false, ResponseCode.SUCCESS, response, null, "Login successful");

        }

        return new Response<>(true, ResponseCode.FAIL, "Failed to login");
    }

    private boolean isValidEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    private HttpServletRequest getAuthorizationHeader() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request;
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }

}
