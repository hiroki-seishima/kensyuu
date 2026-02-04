package com.example.moattravel3.security;  //ユーザー情報を取得したり、UserDetailsUmplクラスのインスタンスを生成するなどのビジネスロックを担当

import java.util.ArrayList;  //arrayリストAPI
import java.util.Collection; //コレクションAPI

import org.springframework.security.core.GrantedAuthority;  
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.moattravel3.entity.User;
import com.example.moattravel3.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository){  
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        try{
            User user = userRepository.findByEmail(email); //ログインで入力したメールアドレスでDBからUserエンティティを取得
            String userRoleName = user.getRole().getName();  //UserのRoleエンティティからロール名（“ROLE_USER”など）を取得　ユーザーかアドミン
            Collection<GrantedAuthority> authorities = new ArrayList<>();  //権限リストを作成（Spring Security標準）。ArrayListで柔軟に複数ロール対応可能 addを使って簡単にリスト追加
            authorities.add(new SimpleGrantedAuthority(userRoleName));  //ロール名をSpring Security形式（GrantedAuthority）に変換して追加
            return new UserDetailsImpl(user,authorities);  //認証情報（User + 権限）をSpring Securityに返す
        } catch (Exception e){
            throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
        }
    }
}
    

