package com.example.moatmusic.repository;  //userテーブルとやりとりをするリポジトリ

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moatmusic.entity.User;
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);  //メールアドレスを検索するメソッド

    public Page<User> findByNameLikeOrFuriganaLike(String nameKeyword, String furiganaKeyword, Pageable pageable);  //登録情報の名前、もしくはフリガナの部分一致検索をページネーションで表示
    
}

