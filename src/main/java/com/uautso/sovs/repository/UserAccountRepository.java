package com.uautso.sovs.repository;


import com.uautso.sovs.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findFirstByUsername(String username);

    Optional<UserAccount> findFirstByPhone(String phone);

    Optional<UserAccount> findFirstByUuid(String uuid);


    Long countAllByDeletedFalse();


    Optional<UserAccount> findFirstByRefreshToken(String token);

}
