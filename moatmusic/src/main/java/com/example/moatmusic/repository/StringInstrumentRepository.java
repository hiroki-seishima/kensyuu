package com.example.moatmusic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

 import com.example.moatmusic.entity.StringInstrument;

 public interface StringInstrumentRepository extends JpaRepository<StringInstrument,Integer> {

}