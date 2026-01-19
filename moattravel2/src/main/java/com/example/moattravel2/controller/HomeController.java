package com.example.moattravel2.controller;  //5-3

import org.springframework.stereotype.Controller;  //コントローラーのフレームワーク

import org.springframework.web.bind.annotation.GetMapping;  //Getmappingのフレームワーク

@Controller  
public class HomeController {   
    @GetMapping("/")  
    public String index() {  
        return "index";  //indexへ遷移 
    }
}
