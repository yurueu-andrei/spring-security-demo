package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User u left join fetch u.roles r left join fetch r.authorities where u.username = :username")
    Optional<User> findByUsername(@Param("login") String username);

}
