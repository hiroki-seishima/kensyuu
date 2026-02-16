package com.example.moatmusic.repository;  //StringInstrumentテーブルとやりとりをするリポジトリ

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

 import com.example.moatmusic.entity.StringInstrument;

 public interface StringInstrumentRepository extends JpaRepository<StringInstrument,Integer> {
    public Page<StringInstrument> findByNameLike(String keyword, Pageable pageable);  //部分一致検索メソッド

}