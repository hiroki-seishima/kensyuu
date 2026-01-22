package com.example.moattravel2.repository;  //17-4

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.moattravel2.entity.Role; //  entity.Roleにアクセス

public interface RoleRepository extends JpaRepository<Role, Integer>{

}

