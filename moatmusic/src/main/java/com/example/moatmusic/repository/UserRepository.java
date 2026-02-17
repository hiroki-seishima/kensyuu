package com.example.moatmusic.repository;  //userテーブルとやりとりをするリポジトリ

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moatmusic.entity.User;
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);  //メールアドレスを検索するメソッド
    
}

