package com.example.moattravel.repository;

import java.util.List;  //29章で追加

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel.entity.House;

public interface HouseRepository extends JpaRepository<House,Integer>{
    public Page<House> findByNameLike(String keyword,Pageable pageable);
    
    
    public Page<House> findByNameLikeOrAddressLike(String nameKeyword, String addressKeyword, Pageable pageable);    //27章で追加
    public Page<House> findByAddressLike(String area, Pageable pageable);  //27章で追加
    public Page<House> findByPriceLessThanEqual(Integer price, Pageable pageable);  //27章で追加
    
    public Page<House> findByNameLikeOrAddressLikeOrderByCreatedAtDesc(String nameKeyword, String addressKeyword, Pageable pageable);  //28章で追加
    public Page<House> findByNameLikeOrAddressLikeOrderByPriceAsc(String nameKeyword, String addressKeyword, Pageable pageable);  //28章で追加
    public Page<House> findByAddressLikeOrderByCreatedAtDesc(String area, Pageable pageable);  //28章で追加
    public Page<House> findByAddressLikeOrderByPriceAsc(String area, Pageable pageable);  //28章で追加
    public Page<House> findByPriceLessThanEqualOrderByCreatedAtDesc(Integer price, Pageable pageable);  //28章で追加
    public Page<House> findByPriceLessThanEqualOrderByPriceAsc(Integer price, Pageable pageable);   //28章で追加
    public Page<House> findAllByOrderByCreatedAtDesc(Pageable pageable);  //28章で追加
    public Page<House> findAllByOrderByPriceAsc(Pageable pageable);  //28章で追加
    public List<House> findTop10ByOrderByCreatedAtDesc();  //29章で追加
}

    

