package com.example.moattravel2.controller; //5-3

import java.util.List; //29-3で追加
import org.springframework.stereotype.Controller; //コントローラーのフレームワーク

import org.springframework.web.bind.annotation.GetMapping; //Getmappingのフレームワーク
import org.springframework.ui.Model; //29-3で追加

import com.example.moattravel2.entity.House;
import com.example.moattravel2.repository.HouseRepository; //29-3で追加

@Controller
public class HomeController {
    private final HouseRepository houseRepository; // 29-3で追加

    public HomeController(HouseRepository houseRepository) { // 29-3で追加
        this.houseRepository = houseRepository; // 29-3で追加
    }

    @GetMapping("/")
    public String index(Model model) { // 29-3でModel modelを追加
        List<House> newHouses = houseRepository.findTop10ByOrderByCreatedAtDesc();  //29-3で追加
        model.addAttribute("newHouses", newHouses);  //29-3で追加
        return "index"; // indexへ遷移
    }
}
