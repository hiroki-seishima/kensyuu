package com.example.moattravel.controller;

import java.util.List;  //29章で追加

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  //29章で追加
import org.springframework.web.bind.annotation.GetMapping;

import com.example.moattravel.entity.House;  //29章で追加
import com.example.moattravel.repository.HouseRepository;  //29章で追加

@Controller   //クラスにつけるアノテーション　@~はアノテーションとよぶ
public class HomeController {
    private final HouseRepository houseRepository;   //29章で追加 

    public HomeController(HouseRepository houseRepository) {   //29章で追加
        this.houseRepository = houseRepository;  //29章で追加
    }

    @GetMapping("/")  //メソッドにつけるアノテーション
    public String index(Model model) {  //29章でModel modelを追加
        List<House> newHouses = houseRepository.findTop10ByOrderByCreatedAtDesc(); //29章で追加
        model.addAttribute("newHouses", newHouses);  //29章で追加

        return "index";  //呼び出すビューの名前をreturnで返す
    }
}
