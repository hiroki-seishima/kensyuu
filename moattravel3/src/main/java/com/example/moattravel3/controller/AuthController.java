package com.example.moattravel3.controller;  //認証機能用（ログイン、会員登録）のコントローラー

import org.springframework.stereotype.Controller;  //コントローラーのAPI
import org.springframework.ui.Model;//モデルのAPI
import org.springframework.validation.BindingResult; //バリテーションの結果を受け取り反映させる
import org.springframework.validation.FieldError;  //フィールドのバリテーションエラーを表す
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;  //GetMappingのAPI
import org.springframework.web.bind.annotation.ModelAttribute;  //フォームデータとJavaオブジェクトを自動バインド
import org.springframework.web.bind.annotation.PostMapping; //PostMappingのAPI
import org.springframework.web.bind.annotation.RequestParam;//パラメータの値を引数に割り当てる
import org.springframework.web.servlet.mvc.support.RedirectAttributes;  //リダイレクト時にデータを一時的に引き継ぐ

import com.example.moattravel3.entity.User;
import com.example.moattravel3.entity.VerificationToken;
import com.example.moattravel3.event.SignupEventPublisher;
import com.example.moattravel3.form.SignupForm;
import com.example.moattravel3.service.UserService;
import com.example.moattravel3.service.VerificationTokenService;

import jakarta.servlet.http.HttpServletRequest;  //コントローラーでHTTPリクエスト情報を取得

@Controller
public class AuthController {
    private final UserService userService;
    private final SignupEventPublisher signupEventPublisher; 
    private final VerificationTokenService verificationTokenService;

    public AuthController(UserService userService,SignupEventPublisher signupEventPublisher,VerificationTokenService verificationTokenService){  //コンストラクタ
        this.userService = userService;
        this.signupEventPublisher = signupEventPublisher;
        this.verificationTokenService=verificationTokenService;
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
    public String signup(@ModelAttribute @Validated SignupForm signupForm,BindingResult bindingResult,RedirectAttributes redirectAttributes,HttpServletRequest httpServletRequest){
        //メールアドレスが登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
        if (userService.isEmailRegistered(signupForm.getEmail())){ //
            FieldError fieldError = new FieldError(bindingResult.getObjectName(),"email","すでに登録済みのメールアドレスです。");  //エラー内容のインスタンスを生成
            bindingResult.addError(fieldError);  //エラー内容を追加

        }
        //パスワードとパスワード（確認用）の入力値が一致しなければ、BindingResultオブジェクトにエラー内容を追加
        if (!userService.isSamePassword(signupForm.getPassword(),signupForm.getPasswordConfirmation())){
            FieldError fieldError = new FieldError(bindingResult.getObjectName(),"password","パスワードが一致しません。"); //引数１、エラー内容を格納するオブジェクト名、２エラーを発生させるフィールド名、３エラーメッセージ
            bindingResult.addError(fieldError);  //エラー内容を追加
        }

        if (bindingResult.hasErrors()){
            return "auth/signup";  //エラーが検出されたらsignupで再表示
        }
        // userService.create(signupForm);  //エラーがなければ(ifに引っ掛からなければ)会員登録内容をDBに登録
        // redirectAttributes.addFlashAttribute("successMessage","会員登録が完了しました。");
        User createdUser = userService.create(signupForm);  // DBへ保存　enable=falseの状態
        String requestUrl = new String(httpServletRequest.getRequestURL());  //
        signupEventPublisher.publishSignupEvent(createdUser,requestUrl);  //イベントの発行（会員登録完了通知の発行）
        redirectAttributes.addFlashAttribute("successMessage","ご入力いただいたメールアドレスに認証メールを送信しました。メールに記載されているリンクをクリックし、会員登録を完了してください。");
        return "redirect:/";  //登録完了のページへ移動
    }


    @GetMapping("/signup/verify")  //メール認証のリンクを入った時に本登録を完了させる処理をし表示
    public String verify(@RequestParam (name = "token")String token,Model model){
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
        if (verificationToken !=null){  //DBで登録したトークンを探してあれば有効
            User user = verificationToken.getUser();
            userService.enableUser(user); //有効としてDBに保存
            String successMessage = "会員登録が完了しました。";
            model.addAttribute("successMessage",successMessage);
        }else{
            String errorMessage = "トークンが無効です。";
            model.addAttribute("errorMessage", errorMessage);  //無効となりエラーメッセージを表示
        }
        return "auth/verify";
    }
}
