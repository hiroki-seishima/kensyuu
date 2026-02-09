package com.example.moattravel3.repository;  //houseテーブルとやりとりをするリポジトリ


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel3.entity.House;

public interface HouseRepository extends JpaRepository<House, Integer> {  //<Entityクラス, 主キー型>
    public Page<House> findByNameLike(String keyword,Pageable pageable); //ページ一覧内での文字（ホテル名）での部分検査一致メソッド

    public Page<House> findByNameLikeOrAddressLike(String nameKeyword, String addressKeyword, Pageable pageable);//ページ一覧内での文字（ホテル名、ホテル住所）を検索するメソッド

    public Page<House> findByAddressLike(String area,Pageable pageable);  //ページ一覧内での文字（ホテルの住所）での部分検索メソッド

    public Page<House> findByPriceLessThanEqual(Integer price,Pageable pageable);  //ページ一覧内での数値（指定した金額以下で絞り込み検索できるメソッド

    public Page<House> findByNameLikeOrAddressLikeOrderByCreatedAtDesc(String nameKeyword,String addressKeyword,Pageable pageable);  //ページ一覧内での民宿の名前、住所の部分一致検索をして作成日時が新しいものから降順で順番に出すメソッド。

    public Page<House> findByNameLikeOrAddressLikeOrderByPriceAsc(String nameKeyword,String area,Pageable pageable);  //ページ一覧内での民宿名と住所の部分検索をし、金額の安い順番（高い順の昇順のため）

    public Page<House> findByAddressLikeOrderByCreatedAtDesc(String area,Pageable pageable);  //ページ一覧内で住所の部分一致検索で作成日時新しい順で降順のメソッド
    
    public Page<House> findByAddressLikeOrderByPriceAsc(String area,Pageable pageable);  //ページ一覧内で住所の部分一致検索をして料金が高い順に降順

    public Page<House> findByPriceLessThanEqualOrderByCreatedAtDesc(Integer price, Pageable pageable);  //指定した料金以下のものを検索して作成日時が新しいものから降順にするメソッド

    public Page<House> findByPriceLessThanEqualOrderByPriceAsc(Integer price,Pageable pageable);  //金額を指定してそれ以下のものを含め、安い順番にするメソッド

    public Page<House> findAllByOrderByCreatedAtDesc(Pageable pageable);  //すべて表示したもので作成日時が新しいものから降順に並べるメソッド

    public Page<House> findAllByOrderByPriceAsc(Pageable pageable);  //すべて表示したもので料金を安い順番で表示する（高い順に昇順）

    public List<House> findTop10ByOrderByCreatedAtDesc();  //10件のデータを作成日時が新しい順で降順のメソッド
}
