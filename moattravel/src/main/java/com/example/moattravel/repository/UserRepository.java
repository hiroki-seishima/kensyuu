package com.example.moattravel.repository;

import org.springframework.data.domain.Page;  //26章で追加
import org.springframework.data.domain.Pageable;  //26章で追加
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    public User findByEmail(String email);  //19.3で追加
    public Page<User> findByNameLikeOrFuriganaLike(String nameKeyword, String furiganaKeyword, Pageable pageable);  //26章で追加
}
