package com.example.moattravel2.repository;  //6-4

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.moattravel2.entity.House;  

public interface HouseRepository extends JpaRepository<House, Integer> {  
}
