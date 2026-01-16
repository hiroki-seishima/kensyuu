package com.example.moattravel.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;   //38章で追加

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; //35章で追加

import com.example.moattravel.entity.House;  //35章で追加
import com.example.moattravel.entity.Reservation;  //35章で追加
import com.example.moattravel.entity.User;  //35章で追加
import com.example.moattravel.form.ReservationRegisterForm;  //35章で追加
import com.example.moattravel.repository.HouseRepository;  //35章で追加
import com.example.moattravel.repository.ReservationRepository;  //35章で追加
import com.example.moattravel.repository.UserRepository;  //35章で追加

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;  
    private final HouseRepository houseRepository;   //35章で追加
    private final UserRepository userRepository;  //35章で追加

    public ReservationService(ReservationRepository reservationRepository, HouseRepository houseRepository, UserRepository userRepository) {  //35章で追加
        this.reservationRepository = reservationRepository;  //35章で追加
        this.houseRepository = houseRepository;  //35章で追加
        this.userRepository = userRepository;  //35章で追加
    }      

    @Transactional   //35章で追加
    // public void create(ReservationRegisterForm reservationRegisterForm) {    //35章で追加　38章で削除
    public void create(Map<String, String> paymentIntentObject) {  //38章で引数をMap<String, String> paymentIntentObjectに変更
        Reservation reservation = new Reservation();  //35章で追加
        Integer houseId = Integer.valueOf(paymentIntentObject.get("houseId"));  //38章で追加
        Integer userId = Integer.valueOf(paymentIntentObject.get("userId"));   //38章で追加
        House house = houseRepository.getReferenceById(houseId);  //35章で追加　　38章でreservationRegisterForm.getHouseId()をhouseIdに変更
        User user = userRepository.getReferenceById(userId);  //35章で追加 38章でreservationRegisterForm.getUserId()をuserIdに変更
        LocalDate checkinDate = LocalDate.parse("checkinDate");  //35章で追加　38章でreservationRegisterForm.getCheckinDate()から"checkinDate"に変更
        LocalDate checkoutDate = LocalDate.parse("checkoutDate");    //35章で追加  38章でreservationRegisterForm.getCheckoutDate()から"checkoutDate"に変更
        Integer numberOfPeople = Integer.valueOf(paymentIntentObject.get("numberOfPeople"));    //38章で追加 
        Integer amount = Integer.valueOf(paymentIntentObject.get("amount"));   //38章で追加

        reservation.setHouse(house);  //35章で追加
        reservation.setUser(user);  //35章で追加
        reservation.setCheckinDate(checkinDate);  //35章で追加
        reservation.setCheckoutDate(checkoutDate);  //35章で追加
        reservation.setNumberOfPeople(numberOfPeople);  //35章で追加　　38章でreservationRegisterForm.getNumberOfPeople()をnumberOfPeopleに変更
        reservation.setAmount(amount);  //35章で追加　38章でreservationRegisterForm.getAmount()をamountに変更
        
        reservationRepository.save(reservation);   //35章で追加
    }
    // 宿泊人数が定員以下かどうかをチェックする
    public boolean isWithinCapacity(Integer numberOfPeople, Integer capacity) {
        return numberOfPeople <= capacity;
    }

    // 宿泊料金を計算する
    public Integer calculateAmount(LocalDate checkinDate, LocalDate checkoutDate, Integer price) {
        long numberOfNights = ChronoUnit.DAYS.between(checkinDate, checkoutDate);
        int amount = price * (int)numberOfNights;
        return amount;
    }
}
