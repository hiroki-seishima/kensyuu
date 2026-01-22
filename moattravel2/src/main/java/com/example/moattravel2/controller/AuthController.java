package com.example.moattravel2.controller; //18-3 認証機能

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {  //ログイン機能
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}
