package com.example.moattravel2.repository;  //23-4

import org.springframework.data.jpa.repository.JpaRepository; //リポジトリのインターフェイス
import com.example.moattravel2.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository< VerificationToken, Integer>{
    public VerificationToken findByToken(String token);
}
