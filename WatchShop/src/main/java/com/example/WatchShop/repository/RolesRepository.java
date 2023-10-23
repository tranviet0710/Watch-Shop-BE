package com.example.WatchShop.repository;

import com.example.WatchShop.model.Roles;
import com.example.WatchShop.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Roles findByName(String name);
}
