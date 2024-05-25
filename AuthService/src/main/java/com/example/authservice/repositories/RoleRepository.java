package com.example.authservice.repositories;

import com.example.authservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>  {

    @Query("SELECT r FROM Role r")
    Set<Role> getAllRole();

    @Query("SELECT r FROM Role r where r.id = 2L")
    Role getRoleForCustomer();


    Role getRoleById(Long id);

}
