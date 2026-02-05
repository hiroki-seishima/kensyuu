package com.example.moattravel3.controller;  //認証機能用（ログイン、会員登録）のコントローラー

import org.springframework.stereotype.Controller;  //コントローラーのAPI
import org.springframework.ui.Model;//モデルのAPI
import org.springframework.validation.BindingResult; //バリテーションの結果を受け取り反映させる
import org.springframework.validation.FieldError;  //フィールドのバリテーションエラーを表す
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;  //GetMappingのAPI
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping; //PostMappingのAPI
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.moattravel3.form.SignupForm;
import com.example.moattravel3.service.UserService;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService){  //コンストラクタ
        this.userService = userService;
    }

    @GetMapping("/login")    //ログイン画面を表示
    public String login() {
        return "auth/login";   
    }

    @GetMapping("/signup")  //会員登録ページの表示
    public String signup(Model model){
        model.addAttribute("signupForm", new SignupForm());
        return "auth/signup";
    }

    @PostMapping("/signup")  
    public String signup(@ModelAttribute @Validated SignupForm signupForm,BindingResult bindingResult,RedirectAttributes redirectAttributes){
        //メールアドレスが登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
        if (userService.isEmailRegistered(signupForm.getEmail())){ //
            FieldError fieldError = new FieldError(bindingResult.getObjectName(),"email","すでに登録済みのメールアドレスです。");  //エラー内容のインスタンスを生成
            bindingResult.addError(fieldError);  //エラー内容を作成

        }
        //パスワードとパスワード（確認用）の入力値が一致しなければ、BindingResultオブジェクトにエラー内容を追加
        if (!userService.isSamePassword(signupForm.getPassword(),signupForm.getPasswordConfirmation())){
            FieldError fieldError = new FieldError(bindingResult.getObjectName(),"password","パスワードが一致しません。"); //引数１、エラー内容を格納するオブジェクト名、２エラーを発生させるフィールド名、３エラーメッセージ
            bindingResult.addError(fieldError);
        }

        if (bindingResult.hasErrors()){
            return "auth/signup";  //エラーが検出されたらsignupで再表示
        }
        userService.create(signupForm);
        redirectAttributes.addFlashAttribute("successMessage","会員登録が完了しました。");
        return "redirect:/";  //登録完了のページへ移動
    }

}
