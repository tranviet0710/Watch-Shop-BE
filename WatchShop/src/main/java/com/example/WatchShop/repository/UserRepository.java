package com.example.WatchShop.repository;

import com.example.WatchShop.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {

}
