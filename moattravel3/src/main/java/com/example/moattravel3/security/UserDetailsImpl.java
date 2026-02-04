package com.example.moattravel3.security;  //ユーザー名(メールアドレス）やパスワード、ロールなどのユーザー情報を保持するための役割を担当

import java.util.Collection;  //spring securityの仕様でコレクションを使う。単一ならリストも可だがuserとadminがあるためコレクションを採用

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.moattravel3.entity.User;

public class UserDetailsImpl implements UserDetails {
    private final User user;
    private final Collection<GrantedAuthority> authorities;

    public UserDetailsImpl(User user,Collection<GrantedAuthority> authorities){
        this.user = user;
        this.authorities = authorities;
    }

    public User getUser() {
        return user;
    }

    //ハッシュ化済みのパスワードを返す  BCriptのやつ
    @Override  //UserDails内のメソッドを上書き　また、APIはimport java.lang.Override;だが自動ではいってるため省略可。@Overrideはなくてもimplementsで継承しているため動くが、スペルチェック、意図の明示等がある。
    public String getPassword(){
        return user.getPassword();
    }

    //ログイン時に利用するユーザー名（メールアドレス）を返す
    @Override
    public String getUsername(){
     return user.getEmail();

    }
    
     
     //ローカルのコレクションを返す
     @Override
     public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    
    }

    //アカウントが期限切れでなければtrueで返す

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //ユーザーがロックされていなければtrueを返す
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //ユーザーのパスワードが期限切れでなければtrueを返す
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    //ユーザーが有効であればtrueで返す
    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }   
}
