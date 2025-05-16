package com.example.naver.controller;

import com.example.naver.entity.NaverLoginProfile;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MyPageController {

    @ModelAttribute("naverProfile")
    public NaverLoginProfile populateNaverProfile(HttpSession session) {
        // 세션에 있으면 반환, 없으면 null 반환
        return (NaverLoginProfile) session.getAttribute("naverProfile");
    }
}