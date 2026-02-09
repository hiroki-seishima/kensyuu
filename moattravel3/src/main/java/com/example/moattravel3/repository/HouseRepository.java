package com.example.moattravel3.repository;  //houseテーブルとやりとりをするリポジトリ

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel3.entity.House;

public interface HouseRepository extends JpaRepository<House, Integer> {  //<Entityクラス, 主キー型>
    public Page<House> findByNameLike(String keyword,Pageable pageable); //ページ一覧内での文字（ホテル名）での部分検査一致メソッド

    public Page<House> findByNameLikeOrAddressLike(String nameKeyword, String addressKeyword, Pageable pageable);//ページ一覧内での文字（ホテル名、ホテル住所）を検索するメソッド

    public Page<House> findByAddressLike(String area,Pageable pageable);  //ページ一覧内での文字（ホテルの住所）での部分検索メソッド

    public Page<House> findByPriceLessThanEqual(Integer price,Pageable pageable);  //ページ一覧内での数値（指定した金額以下で絞り込み検索できるメソッド

    public Page<House> findByNameLikeOrAddressLikeOrderByCreateAtDesc(String nameKeyword,String addressKeyword,Pageable pageable);

    
}
