package com.example.moattravel2.controller; //18-3 認証機能

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; //21-3で追加
import org.springframework.validation.BindingResult; //22-4で追加　Spring MVCのフォームバリデーション結果を保持するインターフェースをインポート、@Valid やBean Validation（ @NotBlank ,  @Email ）の結果を受け取り、エラー判定
import org.springframework.validation.FieldError; //22-4で追加　 BindingResult内の個別フィールドのバリデーションエラーを表すクラスをインポート
import org.springframework.validation.annotation.Validated; //22-4で追加　Spring独自のバリデーションアノテーションで、グループバリデーションをサポート
// Validation（バリデーション）とは、フォーム入力値やデータが正しい形式・ルールかを自動チェックする仕組み
import org.springframework.web.bind.annotation.GetMapping;  //ゲットマッピングのアノテーション　データ取得・画面表示
import org.springframework.web.bind.annotation.ModelAttribute; //22-4で追加 Spring MVCでフォームデータとJavaオブジェクトを自動バインドするアノテーション.HTMLフォームの入力値をJavaオブジェクトのフィールドに自動設定
import org.springframework.web.bind.annotation.PostMapping; //22-4で追加 ポストマッピングのアノテーション　データ送信・登録
import org.springframework.web.servlet.mvc.support.RedirectAttributes; //22-4で追加 リダイレクト時にデータを一時的に引き継ぐためのインターフェースをインポート.リダイレクト先で「成功メッセージ」「エラーメッセージ」を表示
import com.example.moattravel2.form.SignupForm; //21-3で追加
import com.example.moattravel2.service.UserService; //22-4で追加

@Controller
public class AuthController { // ログイン機能
    private final UserService userService;  //22-4で追加

    public AuthController(UserService userService) {  //22-4で追加
        this.userService = userService;  //22-4で追加
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/signup") // 21-3で追加
    public String signup(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute @Validated SignupForm signupForm, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
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

        userService.create(signupForm);
        redirectAttributes.addFlashAttribute("successMessage", "会員登録が完了しました。");

        return "redirect:/";
    }
}
