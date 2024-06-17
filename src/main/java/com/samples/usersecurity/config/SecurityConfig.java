package com.samples.usersecurity.config;

import com.samples.usersecurity.service.InMemoryUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
//security 설정
@Configuration
public class SecurityConfig  {
    private final InMemoryUserDetailService userDetailService;

    // 생성자 주입
    public SecurityConfig(InMemoryUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    // 비밀번호 인코더 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // HTTP 보안 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/register", "/login").permitAll() // 회원가입 경로 접근 허용
                                .anyRequest().authenticated() // 그 외의 경로는 인증 필요
                )
                .formLogin(formLogin->
                        formLogin
                                .loginPage("/login") // 로그인 페이지 설정
                                .defaultSuccessUrl("/home", true)// 로그인 성공 시 리디렉션 경로 설정
                                .failureUrl("/login?error=true") // 로그인 실패 시 리디렉션 경로 설정
                                .permitAll()
                )
                .logout(logout -> {
                            logout
                                    .logoutSuccessUrl("/login?logout")
                                    .permitAll();
                        }
                );
        return http.build();
    }


}
