package com.example.moatmusic.controller;  //ログインを管理するコントローラー

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String login() {
        return "auth/login";  //ログインページへ
    }
}
