package com.example.moattravel2.controller; //7-4

import java.util.List;  // コレクションのリスト
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  //モデルのフレームワーク
import org.springframework.web.bind.annotation.GetMapping;   
import org.springframework.web.bind.annotation.RequestMapping;  
import com.example.moattravel2.entity.House;  // Houseにアクセス
import com.example.moattravel2.repository.HouseRepository;  //HouseRepositoryにアクセス

@Controller  
@RequestMapping("/admin/houses")  
public class AdminHouseController {  
    private final HouseRepository houseRepository;  

    public AdminHouseController(HouseRepository houseRepository) { 
        this.houseRepository = houseRepository;  
    }

    @GetMapping  
    public String index(Model model) {  
        List<House> houses = houseRepository.findAll();  
        model.addAttribute("houses", houses);  
        return "admin/houses/index";  
    }
}
