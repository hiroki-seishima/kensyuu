package com.example.moatmusic.repository; //StringInstrumentテーブルとやりとりをするリポジトリ

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moatmusic.entity.StringInstrument;

public interface StringInstrumentRepository extends JpaRepository<StringInstrument, Integer> {
    public Page<StringInstrument> findByNameLike(String nameKeyword, Pageable pageable); // 部分一致検索メソッド

    public Page<StringInstrument> findByHandedness(String handedness, Pageable pageable);  //右、左しかないためLikeは不要と判断

    public Page<StringInstrument> findByPriceLessThanEqual(Integer price,Pageable pageable);
}