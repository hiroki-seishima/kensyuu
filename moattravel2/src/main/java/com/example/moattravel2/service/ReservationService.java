package com.example.moattravel2.service; //34-2

import java.time.LocalDate;

import java.time.temporal.ChronoUnit; //Java 8 Time APIで日付・時間の差分を計算するための列挙型（enum）**をインポート
import java.util.Map;  //38-4で追加

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional; //35-2で追加

import com.example.moattravel2.entity.House; //35-2で追加
import com.example.moattravel2.entity.Reservation; //35-2で追加
import com.example.moattravel2.entity.User; //35-2で追加 
//import com.example.moattravel2.form.ReservationRegisterForm; //35-2で追加 38-4で使わない
import com.example.moattravel2.repository.HouseRepository; //35-2で追加
import com.example.moattravel2.repository.ReservationRepository; //35-2で追加
import com.example.moattravel2.repository.UserRepository; //35-2で追加

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;  //35-2で追加
    private final HouseRepository houseRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository, HouseRepository houseRepository,
            UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
    }

    @Transactional

    // public void create(ReservationRegisterForm reservationRegisterForm) {  38-4で下に変更
    public void create(Map<String, String> paymentIntentObject) {  
        Reservation reservation = new Reservation();
        Integer userId = Integer.valueOf(paymentIntentObject.get("userId"));  //38-4で追加
        Integer houseId = Integer.valueOf(paymentIntentObject.get("houseId"));  //38-4で追加
        // House house = houseRepository.getReferenceById(reservationRegisterForm.getHouseId());　38-4で下に変更
        House house = houseRepository.getReferenceById(houseId);
        // User user = userRepository.getReferenceById(reservationRegisterForm.getUserId());　　38-4で下に変更
        User user = userRepository.getReferenceById(userId);

        // LocalDate checkinDate = LocalDate.parse(reservationRegisterForm.getCheckinDate());　　38-4で下に変更
        LocalDate checkinDate = LocalDate.parse(paymentIntentObject.get("checkinDate"));

        // LocalDate checkoutDate = LocalDate.parse(reservationRegisterForm.getCheckoutDate());　　38-4で下に変更
        LocalDate checkoutDate = LocalDate.parse(paymentIntentObject.get("checkoutDate")); 

        Integer numberOfPeople = Integer.valueOf(paymentIntentObject.get("numberOfPeople"));    //38-4で追加      
        Integer amount = Integer.valueOf(paymentIntentObject.get("amount"));   //38-4で追加

        reservation.setHouse(house);
        reservation.setUser(user);

        reservation.setCheckinDate(checkinDate);
        reservation.setCheckoutDate(checkoutDate);
        // reservation.setNumberOfPeople(reservationRegisterForm.getNumberOfPeople());  38-4で追加
        // reservation.setAmount(reservationRegisterForm.getAmount());　　38-4で追加

        reservationRepository.save(reservation);
    }//ここまで35-2で追加

    // 宿泊人数が定員以下かどうかをチェックする
    public boolean isWithinCapacity(Integer numberOfPeople, Integer capacity) {
        return numberOfPeople <= capacity;
    }

    // 宿泊料金を計算する
    public Integer calculateAmount(LocalDate checkinDate, LocalDate checkoutDate, Integer price) {
        long numberOfNights = ChronoUnit.DAYS.between(checkinDate, checkoutDate);
        int amount = price * (int) numberOfNights;
        return amount;
    }

}
