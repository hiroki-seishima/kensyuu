package com.example.moattravel.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;  //25.4章で追加
import org.springframework.validation.FieldError;  //25.4章で追加
import org.springframework.validation.annotation.Validated;  //25.4章で追加
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;  //25.4章で追加
import org.springframework.web.bind.annotation.PostMapping;  //25.4章で追加
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;  //25.4章で追加

import com.example.moattravel.entity.User;
import com.example.moattravel.form.UserEditForm; //25章で追加
import com.example.moattravel.repository.UserRepository;
import com.example.moattravel.security.UserDetailsImpl;
import com.example.moattravel.service.UserService;  //25.4章で追加

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository; 
    private final UserService userService;   //25.4章で追加

    public UserController(UserRepository userRepository, UserService userService) {  //, UserService userServiceを25.4章で追加  
        this.userRepository = userRepository;
        this.userService = userService;        //25.4章で追加

    }
    @GetMapping
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {         
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  

        model.addAttribute("user", user);

        return "user/index";
    }
    
    @GetMapping("/edit")  //25章で追加
    public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {        
    User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  
    
    UserEditForm userEditForm = new UserEditForm(user.getId(), user.getName(), user.getFurigana(), user.getPostalCode(), user.getAddress(), user.getPhoneNumber(), user.getEmail());

    model.addAttribute("userEditForm", userEditForm);

    return "user/edit";
    }

    @PostMapping("/update")  //25.4章で追加
    public String update(@ModelAttribute @Validated UserEditForm userEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {  //25.4章で追加
    // メールアドレスが変更されており、かつ登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
        if (userService.isEmailChanged(userEditForm) && userService.isEmailRegistered(userEditForm.getEmail())) {  //25.4章で追加
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");  //25.4章で追加
            bindingResult.addError(fieldError);    //25.4章で追加                     
        }

        if (bindingResult.hasErrors()) {  //25.4章で追加
            return "user/edit";  //25.4章で追加
        }

        userService.update(userEditForm);  //25.4章で追加

        redirectAttributes.addFlashAttribute("successMessage", "会員情報を編集しました。");  //25.4章で追加
        
        return "redirect:/user";  //25.4章で追加
    }
}
