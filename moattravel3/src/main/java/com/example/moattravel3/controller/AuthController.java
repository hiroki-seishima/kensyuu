package com.example.moattravel3.controller;  //認証機能用（ログイン、会員登録）のコントローラー

import org.springframework.stereotype.Controller;  //コントローラーのAPI
import org.springframework.ui.Model;//モデルのAPI
import org.springframework.web.bind.annotation.GetMapping;  //GetMappingのAPI

import com.example.moattravel3.form.SignupForm;

@Controller
public class AuthController {
    @GetMapping("/login")    //ログイン画面を表示
    public String login() {
        return "auth/login";   
    }

    @GetMapping("/signup")  //会員登録ページの表示
    public String signup(Model model){
        model.addAttribute("signupForm", new SignupForm());
        return "auth/signup";
    }
}
