package com.example.moattravel3.security;  //ウェブセキュリティ

import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration  //クラスの上に置くことでそのクラスが設定用のクラスとして機能する メソッドの頭に@Beanをつけるために必要
@EnableWebSecurity  //認証・認可のルールやログイン・ログアウト処理など各種設定を行える
@EnableMethodSecurity  //メソッドレベルでのセキュリティ機能を有効にする。
public class WebSecurityConfig {

    @Bean  //BeanはDIコンテナに登録されたインスタンスのこと
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((requests) -> requests
        .requestMatchers("css/**","/images/**","/js/**","/storage/**","/","/signup/**","/houses/").permitAll()  //すべてのユーザーーにアクセスを許可するURL
        .requestMatchers("/admin/**" ).hasRole("ADMIN") //管理者にのみアクセスを許可するURL
        .anyRequest().authenticated() //上記以外のURLはログインが必要（会員または管理者のどちらでもOK）
        )
        .formLogin((form) -> form
        .loginPage("/login")  //ログインページのURL
        .loginProcessingUrl("/login")  //ログインフォームの送信先URL
        .defaultSuccessUrl("/?loggedIn") //ログイン成功時のリダイレクト先URL
        .failureUrl("/login?error")  //ログインしパイ時のリダイレクト先のURL
        .permitAll()
        )

        .logout((logout)  -> logout
            .logoutSuccessUrl("/?loggedOut")  //ログアウト時のリダイレクト先URL
            .permitAll()    
        );

    return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();  //BCryptはパスワード用のハッシュ値を生成してくれる協力なハッシュアルゴリズム

    }
    
}
