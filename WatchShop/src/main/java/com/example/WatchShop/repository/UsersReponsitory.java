package com.example.WatchShop.repository;

import com.example.WatchShop.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersReponsitory extends JpaRepository<Users, Integer> {

   Users findUserByEmail(String email);

   //check email in DB
   boolean existsByEmail (String email);


}
