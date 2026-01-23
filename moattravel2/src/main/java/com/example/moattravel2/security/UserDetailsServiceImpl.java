package com.example.moattravel2.security;  //19-4

import java.util.ArrayList;  //ArrayListをインポート

import java.util.Collection;  //collectionをインポート

import org.springframework.security.core.GrantedAuthority; //Spring Securityでユーザーの権限（authority）を表現するインターフェースをインポート
import org.springframework.security.core.authority.SimpleGrantedAuthority; //GrantedAuthority インターフェースの標準実装クラスをインポート
import org.springframework.security.core.userdetails.UserDetails; //Spring Securityでユーザー認証情報を提供するコアインターフェースをインポート
import org.springframework.security.core.userdetails.UserDetailsService;  //Spring Securityのユーザー認証コアインターフェースをインポート
import org.springframework.security.core.userdetails.UsernameNotFoundException;  //UserDetailsService.loadUserByUsername() でメールアドレスが見つからない場合に投げる例外クラスをインポート
import org.springframework.stereotype.Service; //クラスをSpringのサービス層コンポーネントとして自動登録するアノテーションをインポート

import com.example.moattravel2.entity.User;  //entity.Userへアクセス
import com.example.moattravel2.repository.UserRepository;  //repository.UserRepositoryへアクセス

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmail(email);
            String userRoleName = user.getRole().getName();
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(userRoleName));
            return new UserDetailsImpl(user, authorities);
        } catch (Exception e) {
            throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
        }
    }
}
