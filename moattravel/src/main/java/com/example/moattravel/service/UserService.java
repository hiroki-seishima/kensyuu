package com.example.moattravel.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.moattravel.entity.Role;
import com.example.moattravel.entity.User;
import com.example.moattravel.form.SignupForm;
import com.example.moattravel.form.UserEditForm; //25章で追加
import com.example.moattravel.repository.RoleRepository;
import com.example.moattravel.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;        
        this.passwordEncoder = passwordEncoder;
    }    

    @Transactional
    public User create(SignupForm signupForm) {
        User user = new User();
        Role role = roleRepository.findByName("ROLE_GENERAL");

        user.setName(signupForm.getName());
        user.setFurigana(signupForm.getFurigana());
        user.setPostalCode(signupForm.getPostalCode());
        user.setAddress(signupForm.getAddress());
        user.setPhoneNumber(signupForm.getPhoneNumber());
        user.setEmail(signupForm.getEmail());
        user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
        user.setRole(role);
        user.setEnabled(false);     //24章でfalseに変更   

        return userRepository.save(user);
    }

    @Transactional  //25章で追加
    public void update(UserEditForm userEditForm) {  //25章で追加
    User user = userRepository.getReferenceById(userEditForm.getId());  //25章で追加

    user.setName(userEditForm.getName());  //25章で追加
    user.setFurigana(userEditForm.getFurigana());  //25章で追加
    user.setPostalCode(userEditForm.getPostalCode());  //25章で追加
    user.setAddress(userEditForm.getAddress());  //25章で追加
    user.setPhoneNumber(userEditForm.getPhoneNumber());  //25章で追加
    user.setEmail(userEditForm.getEmail());  //25章で追加
    
    userRepository.save(user);  //25章で追加
}
    
    // メールアドレスが登録済みかどうかをチェックする
    public boolean isEmailRegistered(String email) {
        User user = userRepository.findByEmail(email);  
        return user != null;
    }

    // パスワードとパスワード（確認用）の入力値が一致するかどうかをチェックする
    public boolean isSamePassword(String password, String passwordConfirmation) {
        return password.equals(passwordConfirmation);
    }

    // ユーザーを有効にする  //24章で追加
    @Transactional  //24章で追加
    public void enableUser(User user) {  //24章で追加
        user.setEnabled(true);   //24章で追加
        userRepository.save(user);  //24章で追加
    }

    // メールアドレスが変更されたかどうかをチェックする  //25章で追加
    public boolean isEmailChanged(UserEditForm userEditForm) {  //25章で追加
        User currentUser = userRepository.getReferenceById(userEditForm.getId());  //25章で追加
        return !userEditForm.getEmail().equals(currentUser.getEmail());  //25章で追加
    }
}
