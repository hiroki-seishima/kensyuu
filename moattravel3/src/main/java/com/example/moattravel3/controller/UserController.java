package com.example.moattravel3.controller;  //会員情報の閲覧・編集を担当するコントローラ

import org.springframework.security.core.annotation.AuthenticationPrincipal;  //ログイン済みユーザーの情報をコントローラーに自動注入
import org.springframework.stereotype.Controller;  //コントローラー
import org. springframework.ui.Model;  //画面にデータを渡すためのオブジェクト
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;  //Getmapping
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;  //RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.moattravel3.entity.User;
import com.example.moattravel3.form.UserEditForm;
import com.example.moattravel3.repository.UserRepository;
import com.example.moattravel3.security.UserDetailsImpl;
import com.example.moattravel3.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository,UserService userService){  //コンストラクタ
        this.userRepository = userRepository;
        this.userService = userService;

    }

    @GetMapping
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,Model model){  //登録情報の閲覧のメソッド
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  //DBのuserテーブルからユーザーとIDで検索

        model.addAttribute("user", user);

        return "user/index";  //自身の会員登録した情報の画面を表示
    }

    @GetMapping("/edit")  
    public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,Model model){  //登録情報の編集メソッド
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  //ログイン情報をDBから検索
        UserEditForm userEditForm = new UserEditForm(user.getId(),user.getName(),user.getFurigana(),user.getPostalCode(),user.getAddress(),user.getPhoneNumber(),user.getEmail());  //編集フォームから呼び出す

        model.addAttribute("userEditForm",userEditForm);  //編集フォーム画面に情報を渡す  

        return "user/edit"; //登録情報編集ページを表示
    }

    @PostMapping("/update")
    public String update(@ModelAttribute @Validated UserEditForm userEditForm,BindingResult bindingResult,RedirectAttributes redirectAttributes){
        //メールアドレスが変更されており、かつ登録済みであれば、BindingResultオブジェクトにエラー内容を追加する

        if (userService.isEmailChanged(userEditForm)&&userService.isEmailRegistered(userEditForm.getEmail())) { //メールアドレスが変更されており、かつ登録済みであれば
            FieldError fieldError = new FieldError(bindingResult.getObjectName(),"email","すでに登録済みのメールアドレスです。");
            bindingResult.addError(fieldError);  //BindingResultオブジェクトにエラー内容を追加する
        }

        if (bindingResult.hasErrors()){  //もしエラーがあれば登録情報編集ページを表示
                return "user/edit";
        }
        userService.update(userEditForm);  //それ以外は（エラーがなければ）更新
        redirectAttributes.addFlashAttribute("successMessage","会員情報を編集しました。");
        return "redirect:/user";  //ユーザーページを表示
        
    }
}
