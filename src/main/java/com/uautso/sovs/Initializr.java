package com.uautso.sovs;


import com.uautso.sovs.model.Permission;
import com.uautso.sovs.model.PermissionGroup;
import com.uautso.sovs.model.Role;
import com.uautso.sovs.model.UserAccount;
import com.uautso.sovs.repository.PermissionGroupRepository;
import com.uautso.sovs.repository.PermissionRepository;
import com.uautso.sovs.repository.RoleRepository;
import com.uautso.sovs.repository.UserAccountRepository;
import com.uautso.sovs.utils.Permissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class Initializr implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final PermissionRepository permissionRepository;
    private final PermissionGroupRepository groupRepository;
    private Logger logger = LoggerFactory.getLogger(Initializr.class);

    @Autowired
    public Initializr(RoleRepository roleRepository, UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder, PermissionRepository permissionRepository, PermissionGroupRepository groupRepository) {
        this.roleRepository = roleRepository;
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.permissionRepository = permissionRepository;
        this.groupRepository = groupRepository;
    }


    @Override
    public void run(ApplicationArguments args) {


        logger.info("========================SEEDING PERMISSIONS ON APPLICATION START=========================");
        List<Permission> permissions = Permissions.permissionsList();
        for (Permission permission : permissions) {
            Optional<Permission> permissionOptional = permissionRepository.findFirstByName(permission.getName());
            if (permissionOptional.isEmpty())
                permissionRepository.save(permission);
        }
        logger.info("===========================FINISHED SEEDING PERMISSIONS===================================");

        logger.info("========================SEEDING REGIONS ON APPLICATION START=========================");

        logger.info("===========================FINISHED SEEDING REGIONS===================================");

        logger.info("========================CREATING SUPPER ADMIN ROLE========================================");
        Optional<Role> roleOptional = roleRepository.findFirstByName("SUPER_ADMINISTRATOR");
        Role role = new Role();
        if (roleOptional.isEmpty()) {
            role.setName("SUPER_ADMINISTRATOR");
            role.setDisplayName("SUPER ADMINISTRATOR");

            List<Permission> permissionList = permissionRepository.findAll();
            List<Permission> adminPermissionList = new ArrayList<>();
            for (Permission permission : permissionList) {
                if (!Objects.equals(permission.getGroup(), "VOTE_CHAIRPERSON"))
                    adminPermissionList.add(permission);
            }
            Set<Permission> permissionSet = new HashSet<>(adminPermissionList);
            role.setPermissions(permissionSet);
        } else {
            role = roleOptional.get();
            List<Permission> permissionList = permissionRepository.findAll();
            Set<Permission> permissionSet = new HashSet<>(permissionList);
            role.setPermissions(permissionSet);
        }
        role = roleRepository.save(role);
        logger.info("=====================FINISHED CREATING SUPPER ADMIN ROLE==================================");


        createPrimeMinisterRole();


        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        UserAccount userAccount;
        Optional<UserAccount> accountOptional = userAccountRepository.findFirstByUsername("19011028");
        if (accountOptional.isEmpty()) {
            userAccount = new UserAccount();
            userAccount.setUsername("19011028");
            userAccount.setPhone("255745057633");
            userAccount.setFirstName("Super");
            userAccount.setEmail("rojasyngaiza01@gmail.com");
            userAccount.setLastName("Admin");
            userAccount.setPassword(passwordEncoder.encode("12345678"));
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            userAccount.setRoles(roles);
            userAccountRepository.save(userAccount);
        }


        Optional<Role> optionalRole = roleRepository.findFirstByName("INSTITUTION_ADMINISTRATOR");
        if (optionalRole.isEmpty()) {
            role = new Role();
            role.setName("INSTITUTION_ADMINISTRATOR");
            role.setDisplayName("INSTITUTION ADMINISTRATOR");
            roleRepository.save(role);
        }

        List<Permission> permissionList = permissionRepository.findAll();
        for (Permission permission : permissionList) {
            if (permission.getPermissionGroup() == null) {
                Optional<PermissionGroup> groupOptional = groupRepository.findFirstByGroupName(permission.getGroup());
                PermissionGroup permissionGroup;
                if (groupOptional.isPresent()) {
                    permissionGroup = groupOptional.get();
                    permission.setPermissionGroup(permissionGroup);
                } else {
                    permissionGroup = new PermissionGroup();
                    permissionGroup.setGroupName(permission.getGroup());
                    PermissionGroup group = groupRepository.save(permissionGroup);
                    permission.setPermissionGroup(group);
                }
                permissionRepository.save(permission);
            }
        }

    }

    private void createPrimeMinisterRole() {
        logger.info("========================CREATING VOTE CHAIRPERSON ROLE========================================");
        Optional<Role> roleOptional = roleRepository.findFirstByName("VOTE_CHAIRPERSON");
        Role role = new Role();
        if (roleOptional.isEmpty()) {
            role.setName("VOTE_CHAIRPERSON");
            role.setDisplayName("VOTE CHAIRPERSON");

            List<Permission> permissionList = permissionRepository.findAll();
            Set<Permission> permissionSet = new HashSet<>(permissionList);
            role.setPermissions(permissionSet);
        } else {
            role = roleOptional.get();
            List<Permission> permissionList = permissionRepository.findAll();
            Set<Permission> permissionSet = new HashSet<>(permissionList);
            role.setPermissions(permissionSet);
        }
        roleRepository.save(role);
        logger.info("=====================FINISHED CREATING VOTE CHAIRPERSON ROLE==================================");
    }




}
