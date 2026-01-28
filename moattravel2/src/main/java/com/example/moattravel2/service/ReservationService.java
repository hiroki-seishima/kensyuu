package com.example.moattravel2.service; //34-2

import java.time.LocalDate;

import java.time.temporal.ChronoUnit; //Java 8 Time APIで日付・時間の差分を計算するための列挙型（enum）**をインポート

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional; //35-2で追加

import com.example.moattravel2.entity.House; //35-2で追加
import com.example.moattravel2.entity.Reservation; //35-2で追加
import com.example.moattravel2.entity.User; //35-2で追加 
import com.example.moattravel2.form.ReservationRegisterForm; //35-2で追加
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

    public void create(ReservationRegisterForm reservationRegisterForm) {
        Reservation reservation = new Reservation();
        House house = houseRepository.getReferenceById(reservationRegisterForm.getHouseId());
        User user = userRepository.getReferenceById(reservationRegisterForm.getUserId());

        LocalDate checkinDate = LocalDate.parse(reservationRegisterForm.getCheckinDate());

        LocalDate checkoutDate = LocalDate.parse(reservationRegisterForm.getCheckoutDate());

        reservation.setHouse(house);
        reservation.setUser(user);

        reservation.setCheckinDate(checkinDate);
        reservation.setCheckoutDate(checkoutDate);
        reservation.setNumberOfPeople(reservationRegisterForm.getNumberOfPeople());
        reservation.setAmount(reservationRegisterForm.getAmount());

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
