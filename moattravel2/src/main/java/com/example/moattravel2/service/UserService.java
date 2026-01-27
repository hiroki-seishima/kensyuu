package com.example.moattravel2.service; //22-3

import org.springframework.security.crypto.password.PasswordEncoder; //Spring Securityのパスワード暗号化・照合インターフェースをインポート

import org.springframework.stereotype.Service; //クラスをSpringのサービスコンポーネントとして自動Bean登録するアノテーションをインポート
import org.springframework.transaction.annotation.Transactional; //Springの宣言的トランザクション管理アノテーションをインポート 

import com.example.moattravel2.entity.Role; //entity.Roleにアクセス
import com.example.moattravel2.entity.User; //entity.Userにアクセス
import com.example.moattravel2.form.SignupForm; //form.SignupFormにアクセス
import com.example.moattravel2.form.UserEditForm;
import com.example.moattravel2.repository.RoleRepository; //repository.RoleRepositoryにアクセス
import com.example.moattravel2.repository.UserRepository; //repository.UserRepositoryにアクセス

@Service
public class UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder; // PasswordEncoderで平文パスワードを一方向ハッシュ化し、ログイン時に平文とハッシュを安全に比較

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User create(SignupForm signupForm) { // create()はサービスクラスの中のメソッド
        User user = new User();
        Role role = roleRepository.findByName("ROLE_GENERAL");

        user.setName(signupForm.getName());
        user.setFurigana(signupForm.getFurigana());
        user.setPostalCode(signupForm.getPostalCode());
        user.setAddress(signupForm.getAddress());
        user.setPhoneNumber(signupForm.getPhoneNumber());
        user.setEmail(signupForm.getEmail());
        user.setPassword(passwordEncoder.encode(signupForm.getPassword())); // encode()メソッドでパスワードをハッシュ化
        user.setRole(role);
        user.setEnabled(false); // 24-3でfalseに変更

        return userRepository.save(user);
    }

    @Transactional // 25-4で追加
    public void update(UserEditForm userEditForm) {
        User user = userRepository.getReferenceById(userEditForm.getId());

        user.setName(userEditForm.getName());
        user.setFurigana(userEditForm.getFurigana());
        user.setPostalCode(userEditForm.getPostalCode());

        user.setAddress(userEditForm.getAddress());
        user.setPhoneNumber(userEditForm.getPhoneNumber());
        user.setEmail(userEditForm.getEmail());

        userRepository.save(user);
    }

    // メールアドレスが登録済みかどうかをチェックする
    public boolean isEmailRegistered(String email) { // isEmailRegistered()はサービスクラスのメソッド
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    // パスワードとパスワード（確認用）の入力値が一致するかどうかをチェックする
    public boolean isSamePassword(String password, String passwordConfirmation) { // isSamePasswordはサービスクラスのメソッド
        return password.equals(passwordConfirmation);
    }

    // ユーザーを有効にする 24-3
    @Transactional
    public void enableUser(User user) { // メール認証用のページ（https://ドメイン名/signup/verify?token=生成したトークン）において、認証に成功した際に実行するメソッド
        user.setEnabled(true);
        userRepository.save(user);
    }
    // メールアドレスが変更されたかどうかをチェックする 25-4で追加
    public boolean isEmailChanged(UserEditForm userEditForm) {
        User currentUser = userRepository.getReferenceById(userEditForm.getId());
        return !userEditForm.getEmail().equals(currentUser.getEmail());
    }
}
