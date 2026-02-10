package com.example.moattravel3.repository;  //reservationテーブルとやりとりをするリポジトリ

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel3.entity.Reservation;
import com.example.moattravel3.entity.User;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

    public Page<Reservation> findByUserOrderByCreatedAtDesc(User user,Pageable pageable);  //ユーザーの全予約を作成日を降順でページネーション取得するメソッド
    
}
