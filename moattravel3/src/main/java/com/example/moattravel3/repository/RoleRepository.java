package com.example.moattravel3.repository;  //roleテーブルとやりとりをするリポジトリ

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.moattravel3.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    public Role findByName(String name);  //ロール名でロールを検索するメソッド

}
