package com.samples.usersecurity.controller;

import ch.qos.logback.core.model.Model;
import com.samples.usersecurity.service.InMemoryUserDetailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    private final InMemoryUserDetailService userDetailsService;

    // 생성자 주입
    public UserController(InMemoryUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // 로그인 페이지 매핑
    @GetMapping("/login")
    public String login() {
        return "login"; // login.html 반환
    }

    // 회원가입 페이지 매핑
    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("register");
        // UserForm 객체를 모델에 추가
        modelAndView.addObject("user", new UserForm());
        return modelAndView; // register.html 반환
    }

    // 회원가입 처리 매핑
    @PostMapping("/register")
    public String register(@ModelAttribute UserForm userForm) {
        // 새로운 사용자 추가
        userDetailsService.addUser(userForm.getUsername(), userForm.getPassword(), "ROLE_USER");
        return "redirect:/login"; // 로그인 페이지로 이동
    }

    // 메인 화면 매핑
    @GetMapping("/home")
    public ModelAndView home() {
        //홈으로 이동
        return new ModelAndView("home");
    }
}