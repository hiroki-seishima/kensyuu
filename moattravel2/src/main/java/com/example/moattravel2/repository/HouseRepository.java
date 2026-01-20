package com.example.moattravel2.repository;  //6-4

import org.springframework.data.domain.Page;  //9-2pageインターフェイス
import org.springframework.data.domain.Pageable;  //9-2pageableインターフェイス


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.moattravel2.entity.House;  

public interface HouseRepository extends JpaRepository<House, Integer> {  
    public Page<House> findByNameLike(String keyword, Pageable pageable);
}
