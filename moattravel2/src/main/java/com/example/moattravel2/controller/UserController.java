package com.example.moattravel2.controller; //25-2会員情報の閲覧・編集を担当するコントローラ

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; //25-4で追加　　Spring MVCのフォームバリデーション結果を保持するインターフェースをインポート、@Valid やBean Validation（ @NotBlank ,  @Email ）の結果を受け取り、エラー判定
import org.springframework.validation.FieldError; //25-4で追加　　BindingResult内の個別フィールドのバリデーションエラーを表すクラスをインポート
import org.springframework.validation.annotation.Validated; //25-4で追加  Spring独自のバリデーションアノテーションで、グループバリデーションをサポート
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute; //25-4で追加
import org.springframework.web.bind.annotation.PostMapping; //25-4で追加
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; //25-4
import com.example.moattravel2.entity.User;
import com.example.moattravel2.form.UserEditForm; //25-4
import com.example.moattravel2.repository.UserRepository;
import com.example.moattravel2.security.UserDetailsImpl;
import com.example.moattravel2.service.UserService; //25-4

@Controller

@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService; // 25-4を追加

    public UserController(UserRepository userRepository, UserService userService) { // 25-4で, UserService userServiceを追加
        this.userRepository = userRepository;
        this.userService = userService; // 25-4
    }

    @GetMapping
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());

        model.addAttribute("user", user);

        return "user/index";
    }

    @GetMapping("/edit")  //25-4で追加
    public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
        UserEditForm userEditForm = new UserEditForm(user.getId(), user.getName(), user.getFurigana(),
                user.getPostalCode(), user.getAddress(), user.getPhoneNumber(), user.getEmail());

        model.addAttribute("userEditForm", userEditForm);

        return "user/edit";
    }

    @PostMapping("/update")  //25-4で追加
    public String update(@ModelAttribute @Validated UserEditForm userEditForm, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        // メールアドレスが変更されており、かつ登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
        if (userService.isEmailChanged(userEditForm) && userService.isEmailRegistered(userEditForm.getEmail())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
            bindingResult.addError(fieldError);
        }

        if (bindingResult.hasErrors()) {
            return "user/edit";
        }

        userService.update(userEditForm);
        redirectAttributes.addFlashAttribute("successMessage", "会員情報を編集しました。");

        return "redirect:/user";
    }
}
