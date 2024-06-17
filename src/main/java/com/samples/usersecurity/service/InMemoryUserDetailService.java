package com.samples.usersecurity.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemoryUserDetailService implements UserDetailsService {
    private final Map<String, UserDetails> users = new HashMap<>();

    // 기본 사용자 추가
    public InMemoryUserDetailService() {
        addUser("user", "{noop}password", "ROLE_USER");
        addUser("admin", "{noop}admin", "ROLE_ADMIN");
    }

    // 사용자 추가 메소드
    public void addUser(String username, String password, String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role)); // 사용자 권한 설정
        //비밀번호 인코딩X
        UserDetails user = new User(username, password, authorities);
        users.put(username, user);
    }

    // 사용자 이름으로 사용자 정보를 가져오는 메소드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
