package com.example.moatmusic.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.moatmusic.entity.StringInstrument;
import com.example.moatmusic.entity.Reservation;
import com.example.moatmusic.entity.User;
// import com.example.moatmusic.form.ReservationRegisterForm;
import com.example.moatmusic.repository.StringInstrumentRepository;
import com.example.moatmusic.repository.ReservationRepository;
import com.example.moatmusic.repository.UserRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final StringInstrumentRepository stringInstrumentRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository,
            StringInstrumentRepository stringInstrumentRepository,
            UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.stringInstrumentRepository = stringInstrumentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    // public void create(ReservationRegisterForm reservationRegisterForm) {
    public void create(Map<String, String> paymentIntentObject) {
        Reservation reservation = new Reservation();

        Integer stringInstrumentId = Integer.valueOf(paymentIntentObject.get("stringInstrumentId"));
        Integer userId = Integer.valueOf(paymentIntentObject.get("userId"));
        StringInstrument stringInstrument =
                // stringInstrumentRepository.getReferenceById(reservationRegisterForm.getStringInstrumentId());
                stringInstrumentRepository.getReferenceById(stringInstrumentId);
        User user =
                // userRepository.getReferenceById(reservationRegisterForm.getUserId());
                userRepository.getReferenceById(userId);
        LocalDateTime rentalStartTimeDate =
                // LocalDateTime.parse(reservationRegisterForm.getRentalStartTimeDate());
                LocalDateTime.parse(paymentIntentObject.get("rentalStartTimeDate"));
        LocalDateTime rentalEndTimeDate =
                // LocalDateTime.parse(reservationRegisterForm.getRentalEndTimeDate());
                LocalDateTime.parse(paymentIntentObject.get("rentalEndTimeDate"));
        Integer rentalQuantity = Integer.valueOf(paymentIntentObject.get("rentalQuantity"));
        Integer amount = Integer.valueOf(paymentIntentObject.get("amount"));
        reservation.setStringInstrument(stringInstrument);
        reservation.setUser(user);
        reservation.setRentalStartTimeDate(rentalStartTimeDate);
        reservation.setRentalEndTimeDate(rentalEndTimeDate);

        // reservation.setRentalQuantity(reservationRegisterForm.getRentalQuantity());
        reservation.setRentalQuantity(rentalQuantity);
        // reservation.setAmount(reservationRegisterForm.getAmount());
        reservation.setAmount(amount);

        reservationRepository.save(reservation);
    }

    // 台数が最大貸出数以下かどうかをチェックする
    public boolean isWithinCapacity(Integer rentalQuantity, Integer capacity) {
        return rentalQuantity <= capacity;
    }

    // レンタル料金を計算する（時間あたりで計算）
    public Integer calculateAmount(LocalDateTime rentalStartTimeDate, LocalDateTime rentalEndTimeDate, Integer price,
            Integer rentalQuantity) {
        long rentalHours = ChronoUnit.HOURS.between(rentalStartTimeDate, rentalEndTimeDate);
        int amount = price * rentalQuantity * (int) rentalHours;
        return amount;
    }
}
