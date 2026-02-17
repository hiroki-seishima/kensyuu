package com.example.moatmusic.security; //UserDetailsServiceインターフェイスを実装させビジネスロックを担当するクラス

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.moatmusic.entity.User;
import com.example.moatmusic.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { //登録してあるメールアドレスをDBから探してなければ見つからない表示をつくるメソッド
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
