package com.example.moattravel2.repository;  //31-3

import org.springframework.data.domain.Page;  //32-2で追加


import org.springframework.data.domain.Pageable;  //32-2で追加

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.moattravel2.entity.Reservation;
import com.example.moattravel2.entity.User; //32-2で追加

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    public Page<Reservation> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);  //32-2で追加
}
