package com.example.moattravel2.security; //18-5

import org.springframework.context.annotation.Bean;  //メソッドをSpring Beanとして登録するアノテーションをインポート
import org.springframework.context.annotation.Configuration;  //JavaクラスをSpringの設定クラスとして定義するアノテーションをインポート
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;  //Spring Security 6以降のメゾットレベル認証を有効化
import org.springframework.security.config.annotation.web.builders.HttpSecurity;  //Spring SecurityのWebセキュリティ設定を構築するためのビルダークラスをインポート
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;  //Spring SecurityのWebセキュリティ機能を有効化するアノテーションをインポート
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;  //パスワードを安全にハッシュ化するSpring SecurityのBCrypt実装クラスをインポート
import org.springframework.security.crypto.password.PasswordEncoder;  //パスワードを安全にハッシュ化するためのSpring Securityインターフェースをインポート
import org.springframework.security.web.SecurityFilterChain;  //Spring Security 6.x以降でWebセキュリティフィルタチェーンを定義するビーン型をインポート

@Configuration // @Bean メソッドを定義し、Springコンテナが管理するBeanを登録します。XML設定の代替として使用され、MoatTravelではDataSourceやStripe設定などを定義
@EnableWebSecurity //Spring Securityによるセキュリティ機能を有効にし、認証・認可のルールやログイン・ログアウト処理など各種設定
@EnableMethodSecurity  //クラスに付与し、Spring SecurityのフィルターチェーンやWebセキュリティ設定を有効にします。
public class WebSecurityConfig {
    @Bean  //クラス内のメソッドに付与し、そのメソッドが返すオブジェクトをSpringコンテナが管理するBeanとして登録

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {  

        http
                .authorizeHttpRequests((requests) -> requests  
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/storage/**", "/", "/signup/**", "/houses", "/houses/{id}").permitAll() // すべてのユーザーにアクセスを許可するURL　22-5, "/signup/**"で追加　27-5で, "/houses"を追加  30-4で, "/houses/{id}"を追加
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 管理者にのみアクセスを許可するURL
                        .anyRequest().authenticated() // 上記以外のURLはログインが必要（会員または管理者のどちらでもOK）
                )
                .formLogin((form) -> form
                        .loginPage("/login") // ログインページのURL
                        .loginProcessingUrl("/login") // ログインフォームの送信先URL
                        .defaultSuccessUrl("/?loggedIn") // ログイン成功時のリダイレクト先URL
                        .failureUrl("/login?error") // ログイン失敗時のリダイレクト先URL

                        .permitAll()

                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/?loggedOut") // ログアウト時のリダイレクト先URL
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
