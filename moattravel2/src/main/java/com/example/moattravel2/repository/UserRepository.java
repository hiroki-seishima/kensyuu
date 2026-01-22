package com.example.moattravel2.repository;  //17-4
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.moattravel2.entity.User; //  entity.Userにアクセス

public interface UserRepository extends JpaRepository<User, Integer>{
    
}
