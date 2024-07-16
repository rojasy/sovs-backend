package com.uautso.sovs.servImpl;


import com.uautso.sovs.dto.UserAccountDto;
import com.uautso.sovs.model.Role;
import com.uautso.sovs.model.UserAccount;
import com.uautso.sovs.repository.RoleRepository;
import com.uautso.sovs.repository.UserAccountRepository;
import com.uautso.sovs.service.UserAccountService;
import com.uautso.sovs.utils.Response;
import com.uautso.sovs.utils.ResponseCode;
import com.uautso.sovs.utils.UserInformationValidation;
import com.uautso.sovs.utils.userextractor.LoggedUser;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final LoggedUser loggedUser;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository accountRepository, RoleRepository roleRepository, LoggedUser loggedUser, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.loggedUser = loggedUser;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Response<UserAccount> createUpdateUser(UserAccountDto accountDto) {
        try {
            UserAccount user = loggedUser.getUser();
            if (user == null) {
                logger.info("UNAUTHENTICATED USER TRYING TO CREATE/UPDATE USER, REJECTED");
                return new Response<>(true, ResponseCode.UNAUTHORIZED, "Unauthenticated!");
            }
            logger.info("{} CREATING/UPDATING USER WITH DETAILS ======> {}", user.getUsername(), accountDto);
            UserAccount userAccount = new UserAccount();
            boolean isFirstTime = true;
            if (accountDto.getUuid() != null) {
                Optional<UserAccount> accountOptional = accountRepository.findFirstByUuid(accountDto.getUuid());
                if (accountOptional.isPresent()) {
                    userAccount = accountOptional.get();
                    isFirstTime = false;
                }
            }

            if (!UserInformationValidation.isEmailValid(accountDto.getEmail()))
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Please enter a valid email");

            if (accountDto.getPhoneNumber() == null || !UserInformationValidation.isPhoneNumberValid(accountDto.getPhoneNumber()) || accountDto.getPhoneNumber().length() != 12)
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Please enter a valid phone number");

            Optional<UserAccount> optionalByPhone = accountRepository.findFirstByPhone(accountDto.getPhoneNumber());
            if ((isFirstTime && optionalByPhone.isPresent()) || (!isFirstTime && !Objects.equals(userAccount.getPhone(), accountDto.getPhoneNumber()) && optionalByPhone.isPresent()))
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Phone number already taken");

            Optional<UserAccount> optionalByEmail = accountRepository.findFirstByUsername(accountDto.getEmail());
            if ((isFirstTime && optionalByEmail.isPresent()) || (!isFirstTime && !Objects.equals(userAccount.getEmail(), accountDto.getEmail()) && optionalByEmail.isPresent()))
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "This email already taken");

            Optional<UserAccount> optionalByUsername = accountRepository.findFirstByUsername(accountDto.getUsername());
            if ((isFirstTime && optionalByUsername.isPresent()) || (!isFirstTime && !Objects.equals(userAccount.getUsername(), accountDto.getUsername()) && optionalByUsername.isPresent()))
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "This Username already taken");

            if (isFirstTime && accountDto.getUsername() == null)
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Username Cannot be Empty");

            if (isFirstTime && accountDto.getFirstName() == null)
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "First Name Cannot be Empty");

            if (isFirstTime && accountDto.getLastName() == null)
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Last Name Cannot be Empty");

            if (isFirstTime && accountDto.getCourses() == null)
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Course Cannot be Empty");

            if (isFirstTime && accountDto.getPassword() == null)
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Password Cannot be Empty");

            if (!accountDto.getFirstName().isBlank() && !Objects.equals(accountDto.getFirstName(), userAccount.getFirstName()))
                userAccount.setFirstName(accountDto.getFirstName());

            if (!accountDto.getLastName().isBlank() && !Objects.equals(accountDto.getLastName(), userAccount.getLastName()))
                userAccount.setLastName(accountDto.getLastName());

            if (accountDto.getPhoneNumber() != null && !Objects.equals(accountDto.getPhoneNumber(), userAccount.getPhone()))
                userAccount.setPhone(accountDto.getPhoneNumber());

            if (accountDto.getUsername() != null && !Objects.equals(accountDto.getUsername(), userAccount.getUsername()))
                userAccount.setUsername(accountDto.getUsername());

            if (accountDto.getEmail() != null && !Objects.equals(accountDto.getEmail(), userAccount.getEmail()))
                userAccount.setEmail(accountDto.getEmail());

            if (isFirstTime && userAccount.getPassword() == null) {
                userAccount.setPassword(passwordEncoder.encode(accountDto.getPassword()));
            }

            if (isFirstTime && userAccount.getCourses() == null) {
                userAccount.setCourses(accountDto.getCourses());

            }

            UserAccount account = accountRepository.save(userAccount);

            return new Response<>(false, ResponseCode.SUCCESS, account, isFirstTime ? "User Created Successfully" : "User Updated Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(true, ResponseCode.FAIL, "Failed to create user");
    }

    @Override
    public Response<UserAccount> deleteUserAccount(String uuid) {
        try {
            UserAccount user = loggedUser.getUser();
            if (user == null) {
                logger.info("UNAUTHENTICATED USER TRYING TO DELETE USER, REJECTED");
                return new Response<>(true, ResponseCode.UNAUTHORIZED, "Unauthenticated!");
            }

            Optional<UserAccount> accountOptional = accountRepository.findFirstByUuid(uuid);
            if (accountOptional.isEmpty())
                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "User NOt Found");

            UserAccount account = accountOptional.get();
            account.setDeleted(true);
            UserAccount updatedAccount = accountRepository.save(account);

            return new Response<>(false, ResponseCode.SUCCESS, updatedAccount, "User deleted successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(true, ResponseCode.FAIL, "Failed to deleted user, Unknown error occurred");
    }




    @Override
    public Response<UserAccount> getUserByUuid(String uuid) {
        try {
            Optional<UserAccount> accountOptional = accountRepository.findFirstByUuid(uuid);
            return accountOptional.map(userAccount -> new Response<>(false, ResponseCode.SUCCESS, userAccount)).orElseGet(() -> new Response<>(true, ResponseCode.NO_RECORD_FOUND, "User not found"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Response<UserAccount> setRolesToUser(String userUuid, List<Long> roleIds) {
        try {
            Optional<UserAccount> accountOptional = accountRepository.findFirstByUuid(userUuid);
            if (accountOptional.isEmpty())
                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "User not found");

            UserAccount account = accountOptional.get();
            Set<Role> roles = new HashSet<>();
            for (Long id : roleIds) {
                Optional<Role> roleOptional = roleRepository.findById(id);
                roleOptional.ifPresent(roles::add);
            }

            account.setRoles(roles);

            UserAccount userAccount = accountRepository.save(account);

            return new Response<>(false, ResponseCode.SUCCESS, userAccount, null, "Roles assigned to user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(true, ResponseCode.FAIL, "Operation failed");
    }



    @Override
    public Page<UserAccount> getAllUsers(Pageable pageable) {
        try {
            UserAccount user = loggedUser.getUser();
            if (user == null) {
                logger.info("UNAUTHENTICATED USER TRYING TO FETCH INSTITUTION USERS, REJECTED");
                return new PageImpl<>(new ArrayList<>(), pageable, 0);
            }
            return accountRepository.findAll(pageable);
        } catch (Exception e) {
            log.error("FAILED TO GET ALL USERS: ", e);
        }
        return new PageImpl<>(new ArrayList<>(), pageable, 0);
    }

    @Override
    public Response<UserAccount> changePassword(String oldPassword, String newPassword) {
        try {
            UserAccount user = loggedUser.getUser();
            if (user == null) {
                logger.info("UNAUTHENTICATED USER TRYING TO CHANGE PASSWORD, REJECTED");
                return new Response<>(true, ResponseCode.UNAUTHORIZED, "Unauthorized");
            }
            if (!passwordEncoder.matches(oldPassword, user.getPassword()))
                return new Response<>(true, ResponseCode.FAIL, "Password didn't match");

            user.setPassword(passwordEncoder.encode(newPassword));
            UserAccount updatedUser = accountRepository.save(user);
            return new Response<>(false, ResponseCode.SUCCESS, updatedUser, "Password changed successfully");

        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response<>(true, ResponseCode.EXCEPTION, "Failed to change password");
        }
    }




    @Override
    public Response<UserAccount> activateUser(String uuid) {
        try {
            UserAccount user = loggedUser.getUser();
            if (user == null) {
                logger.warn("UNAUTHENTICATED USER IS TRYING TO ACTIVATE USER, REJECTED");
                return new Response<>(true, ResponseCode.UNAUTHORIZED, "Unauthorized");
            }
            Optional<UserAccount> optionalUserAccount = accountRepository.findFirstByUuid(uuid);
            if (optionalUserAccount.isEmpty())
                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "User not found");

            UserAccount userAccount = optionalUserAccount.get();
            userAccount.setActive(true);
            UserAccount updatedUser = accountRepository.save(userAccount);
            return new Response<>(false, ResponseCode.SUCCESS, updatedUser, "User activated successfully");
        } catch (Exception e) {
            logger.error("ERROR WHILE ACTIVATING USER: ", e);
            return new Response<>(true, ResponseCode.EXCEPTION, "Operation failed");
        }
    }

    @Override
    public Response<UserAccount> deactivateUser(String uuid) {
        try {
            UserAccount user = loggedUser.getUser();
            if (user == null) {
                logger.warn("UNAUTHENTICATED USER IS TRYING TO DEACTIVATE USER, REJECTED");
                return new Response<>(true, ResponseCode.UNAUTHORIZED, "Unauthorized");
            }
            Optional<UserAccount> optionalUserAccount = accountRepository.findFirstByUuid(uuid);
            if (optionalUserAccount.isEmpty())
                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "User not found");

            UserAccount userAccount = optionalUserAccount.get();
            userAccount.setActive(false);
            UserAccount updatedUser = accountRepository.save(userAccount);
            return new Response<>(false, ResponseCode.SUCCESS, updatedUser, "User deactivated successfully");
        } catch (Exception e) {
            logger.error("ERROR WHILE ACTIVATING USER: ", e);
            return new Response<>(true, ResponseCode.EXCEPTION, "Operation failed");
        }
    }
}
