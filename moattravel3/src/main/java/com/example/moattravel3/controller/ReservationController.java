package com.example.moattravel3.controller;  //ログイン済みユーザーの予約一覧を表示するためのコントローラー

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.moattravel3.entity.Reservation;
import com.example.moattravel3.entity.User;
import com.example.moattravel3.repository.ReservationRepository;
import com.example.moattravel3.security.UserDetailsImpl;

@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository){ //コンストラクタ
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,@PageableDefault(page = 0, size = 10,sort ="id",direction = Direction.ASC) Pageable pageable,Model model) {//ログインユーザーの予約一覧をページネーションで表示するメソッド
        User user = userDetailsImpl.getUser();  //spring securityからログイン中のUserエンティティを取得
        Page<Reservation> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user,pageable);  //ログインユーザーの予約一覧をページネーションで取得
        model.addAttribute("reservationPage",reservationPage);  //予約一覧情報を画面に受けわたし
        return "reservations/index";  //予約一覧を表示
    }
}
