package com.ssafy.campcino.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 여기에 사용자 정보를 DB에서 가져오는 로직 추가
        // 예시로 임의 사용자 생성
        if ("testUser".equals(username)) {
            return User.builder()
                    .username("testUser")
                    .password("{noop}password") // 비밀번호는 인코딩 필요 (noop은 인코딩 없이 사용)
                    .roles("USER")
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

}
