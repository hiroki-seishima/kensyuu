package com.example.moatmusic.repository;  //roleテーブルとやりとりをするリポジトリ

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moatmusic.entity.Role;
public interface RoleRepository extends JpaRepository<Role, Integer>{
    
}
