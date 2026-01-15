package com.example.moattravel.repository;

import org.springframework.data.domain.Page;  //32章で追加
import org.springframework.data.domain.Pageable;  //32章で追加
import org.springframework.data.jpa.repository.JpaRepository;  

import com.example.moattravel.entity.Reservation;
import com.example.moattravel.entity.User;  //32章で追加

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    public Page<Reservation> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);  //32章で追加
}
