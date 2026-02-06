package com.example.moattravel3.service;  //会員情報を登録するためのサービスクラス

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.moattravel3.entity.Role;
import com.example.moattravel3.entity.User;
import com.example.moattravel3.form.SignupForm;
import com.example.moattravel3.repository.RoleRepository;
import com.example.moattravel3.repository.UserRepository;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,RoleRepository roleRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    public User create(SignupForm signupForm) { //フォームから送信された会員情報をデータベースに登録する。
        User user = new User();  //userエンティティから呼び出すためにインスタンス生成
        Role role = roleRepository.findByName("ROLE_GENERAL");  //DBのroleテーブルの一般ユーザーを検索し、見つかった一般ユーザーをRoleエンティティをrole変数に格納

        user.setName(signupForm.getName());
        user.setFurigana(signupForm.getFurigana());
        user.setPostalCode(signupForm.getPostalCode());
        user.setAddress(signupForm.getAddress());
        user.setPhoneNumber(signupForm.getPhoneNumber());
        user.setEmail(signupForm.getEmail());
        user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
        user.setRole(role);
        user.setEnabled(false);

        return userRepository.save(user);  //保存して生成したuser情報を返す。なお、save()はspring Data JPAが自動実装

    }

    public boolean isEmailRegistered(String email){//メールアドレスが登録済みかどうかをチェックするメソッド
    User user = userRepository.findByEmail(email); //DBのuserテーブルからemailを検索して見つかったemailをuserエンテイティのuser変数へ格納
    return user !=null;  //nullでなければ返すとなってるが、すでにメールアドレスが登録していればtrueを返す。つまりメールアドレスが登録しれなければfalseで返される。
    }

    public boolean isSamePassword(String password,String passwordConfirmation) {//パスワードとパスワード（確認用）の入力値が一致するかどうかをチェックする
        return password.equals(passwordConfirmation);  //両者イコールであれば返す
    }
    
    //ユーザーを有効にする
    @Transactional
    public void enableUser(User user){
        user.setEnabled(true);  //enableを有効にする
        userRepository.save(user);  //有効にした情報をDBへ保存
    }

}

