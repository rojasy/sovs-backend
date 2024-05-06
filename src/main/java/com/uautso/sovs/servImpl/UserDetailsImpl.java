package com.uautso.sovs.servImpl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uautso.sovs.model.Role;
import com.uautso.sovs.model.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String username;
    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    private final boolean active;

    public UserDetailsImpl(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities,
                           boolean active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.active = active;
    }

    public static UserDetailsImpl build(UserAccount user){

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                getAuthorities(user.getRoles()),
                user.getActive()
        );
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    private static List<GrantedAuthority> getAuthorities(Set<Role> roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : roles){
            List<SimpleGrantedAuthority> collect = role.getPermissions()
                    .stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                    .collect(Collectors.toList());
            grantedAuthorities.addAll(collect);
        }
        return grantedAuthorities;
    }
}
