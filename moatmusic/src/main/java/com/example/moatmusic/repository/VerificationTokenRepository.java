package com.example.moatmusic.repository;  //VerificationTokenテーブルとやりとりをするリポジトリ

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moatmusic.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Integer> {
    public VerificationToken findByToken(String token);  //トークン検索メソッド
    
}
