package com.example.moattravel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; //22章で追加
import org.springframework.validation.FieldError;  //22章で追加
import org.springframework.validation.annotation.Validated;  //22章で追加
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;  //22章で追加
import org.springframework.web.bind.annotation.PostMapping;  //22章で追加
import org.springframework.web.bind.annotation.RequestParam;  //24章で追加
import org.springframework.web.servlet.mvc.support.RedirectAttributes;  //22章で追加

import com.example.moattravel.entity.User;  //24章で追加
import com.example.moattravel.entity.VerificationToken;  //24章で追加
import com.example.moattravel.event.SignupEventPublisher;  //24章で追加
import com.example.moattravel.form.SignupForm;
import com.example.moattravel.service.UserService;  //22章で追加
import com.example.moattravel.service.VerificationTokenService;  //24章で追加

import jakarta.servlet.http.HttpServletRequest;  //24章で追加

@Controller
public class AuthController {
    private final UserService userService;  //22章で追加
    private final SignupEventPublisher signupEventPublisher;  //24章で追加
    private final VerificationTokenService verificationTokenService;  //24章で追加
    

    public AuthController(UserService userService,SignupEventPublisher signupEventPublisher, VerificationTokenService verificationTokenService) {  //22章で追加,24章で引数SignupEventPublisher signupEventPublisher, VerificationTokenService verificationTokenServiceを追加
        this.userService = userService;  //22章で追加
        this.signupEventPublisher = signupEventPublisher;  //24章で追加
        this.verificationTokenService = verificationTokenService;  //24章で追加
    }

    @GetMapping("/login")
    public String login() {        
        return "auth/login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {        
        model.addAttribute("signupForm", new SignupForm());
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute @Validated SignupForm signupForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {      //24章で, HttpServletRequest httpServletRequestを追加
    // メールアドレスが登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
        if (userService.isEmailRegistered(signupForm.getEmail())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
            bindingResult.addError(fieldError);                       
        }    

        // パスワードとパスワード（確認用）の入力値が一致しなければ、BindingResultオブジェクトにエラー内容を追加する
        if (!userService.isSamePassword(signupForm.getPassword(), signupForm.getPasswordConfirmation())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "パスワードが一致しません。");
            bindingResult.addError(fieldError);
        }        

        if (bindingResult.hasErrors()) {
            return "auth/signup";
        }
        
        //userService.create(signupForm);  24章で削除
        //redirectAttributes.addFlashAttribute("successMessage", "会員登録が完了しました。"); 24章で削除
        User createdUser = userService.create(signupForm);

        String requestUrl = new String(httpServletRequest.getRequestURL()); //24章で追加
        signupEventPublisher.publishSignupEvent(createdUser, requestUrl);  //24章で追加
        redirectAttributes.addFlashAttribute("successMessage", "ご入力いただいたメールアドレスに認証メールを送信しました。メールに記載されているリンクをクリックし、会員登録を完了してください。");  //24章で追加
        
        return "redirect:/";
    }
    @GetMapping("/signup/verify")
    public String verify(@RequestParam(name = "token") String token, Model model) {
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
    
        if (verificationToken != null) {
            User user = verificationToken.getUser();    
            userService.enableUser(user);

            String successMessage = "会員登録が完了しました。";
            model.addAttribute("successMessage", successMessage);            
        } else {
            String errorMessage = "トークンが無効です。";
            model.addAttribute("errorMessage", errorMessage);
        }

        return "auth/verify";         
    }
}
