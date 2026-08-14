package com.jurisai.dao.repository;

import com.jurisai.entity.User;
import com.jurisai.entity.pk.UserPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, UserPK> {
    Optional<User> findByIdAccountId(Long accountId);
    Optional<User> findByIdUserId(Long userId);
}
