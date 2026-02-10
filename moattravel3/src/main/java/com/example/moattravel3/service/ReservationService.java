package com.example.moattravel3.service;  //予約に関するサービス機能

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    //宿泊人数が定員以下かどうかをチェックする

    public boolean isWithinCapacity(Integer numberOfPeople,Integer capacity){
        return numberOfPeople <= capacity; //定員(capacity)以下であれば返す
    }

    //宿泊料金を計算する
    public Integer calculateAmount(LocalDate checkinDate, LocalDate checkoutDate,Integer price){
        long numberOfNights = ChronoUnit.DAYS.between(checkinDate,checkoutDate);  //チェックイン、チェックアウト日を含めない差分（１５日〜１７日なら２日間）
        int amount = price * (int)numberOfNights;
        return amount;  //宿泊料金計算結果を返す
    }
}
