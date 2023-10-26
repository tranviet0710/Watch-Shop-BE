package com.example.WatchShop.repository;

import com.example.WatchShop.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersReponsitory extends JpaRepository<Users, Long> {

   Optional<Users> findByEmail(String email);

   //check email in DB
   boolean existsByEmail (String email);

}