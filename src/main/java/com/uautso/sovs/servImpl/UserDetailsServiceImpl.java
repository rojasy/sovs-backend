package com.uautso.sovs.servImpl;


import com.uautso.sovs.model.UserAccount;
import com.uautso.sovs.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAccountRepository repository;

    @Autowired
    public UserDetailsServiceImpl(UserAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = repository.findFirstByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("A user with this username does not exist!"));
        return UserDetailsImpl.build(user);
    }
}
