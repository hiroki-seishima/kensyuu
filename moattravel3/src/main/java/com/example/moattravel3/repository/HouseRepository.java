package com.example.moattravel3.repository;  //houseテーブルとやりとりをするリポジトリ

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel3.entity.House;

public interface HouseRepository extends JpaRepository<House, Integer> {  //<Entityクラス, idの型>
    public Page<House> findByNameLike(String keyword,Pageable pageable); //ページ一覧内での文字（ホテル名）での部分検査一致メソッド
}
