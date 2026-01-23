package com.example.moattravel2.security;  //19-2

import java.util.Collection;  //コレクションのインポート
import org.springframework.security.core.GrantedAuthority;  //Spring Securityフレームワークで認証されたユーザーに付与された権限（authority）を表すインターフェースをインポート
import org.springframework.security.core.userdetails.UserDetails;  //Spring Securityでユーザー認証に必要な情報を提供するコアインターフェースをインポート ユーザー名、パスワード、権限（ GrantedAuthority のコレクション）、アカウント状態（有効期限切れ、ロック、利用可能か）を管理します。認証プロセスで UserDetailsService.loadUserByUsername() が呼び出され、このオブジェクトを返却して Authentication オブジェクトを作成
import com.example.moattravel2.entity.User;  // entity.Userにアクセス

public class UserDetailsImpl implements UserDetails {  //UserDetailsインターフェイスを継承
    private final User user;

    private final Collection<GrantedAuthority> authorities;

    public UserDetailsImpl(User user, Collection<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public User getUser() {
        return user;
    }

    // ハッシュ化済みのパスワードを返す
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // ログイン時に利用するユーザー名（メールアドレス）を返す
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    // ロールのコレクションを返す
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // アカウントが期限切れでなければtrueを返す アカウント作成をしないためこの時点でtrueで返す
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // ユーザーがロックされていなければtrueを返す ユーザーがロックをする機能を作らないためこの時点でtrueで返す
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // ユーザーのパスワードが期限切れでなければtrueを返す　有効期限の設定をしないためこの時点でtrueで返す
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // ユーザーが有効であればtrueを返す
    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
