package com.example.moattravel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller   //クラスにつけるアノテーション　@~はアノテーションとよぶ
public class HomeController {
    @GetMapping("/")  //メソッドにつけるアノテーション
    public String index() {
        return "index";  //呼び出すビューの名前をreturnで返す
    }
}
