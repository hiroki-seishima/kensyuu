package com.example.moattravel3.controller;  //トップページのコントローラー

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.moattravel3.entity.House;
import com.example.moattravel3.repository.HouseRepository;


@Controller
public class HomeController {
    private final HouseRepository houseRepository;

    public HomeController(HouseRepository houseRepository){
        this.houseRepository = houseRepository;
    }

    @GetMapping("/")
    public String index(Model model){

        List<House> newHouses = houseRepository.findTop10ByOrderByCreatedAtDesc(); //新着10件をリストに格納

        model.addAttribute("newHouses",newHouses);  //TOP10のデータを画面に受け渡す
        return "index";  //トップページを表示
    }
    
}
