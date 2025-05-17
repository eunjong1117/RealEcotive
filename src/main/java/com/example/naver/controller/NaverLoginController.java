package com.example.naver.controller;

import com.example.naver.entity.NaverLoginProfile;
import com.example.naver.service.NaverLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class NaverLoginController {

    @Autowired
    private NaverLoginService naverLoginService;

    @GetMapping("/")
    public String home() {
        return "login"; // Login.html을 반환 -> localhost:8080 첫 화면
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Login.html을 반환 -> localhost:8080 첫 화면
    }

    @GetMapping("/naver/callback")
    public String naverLoginCallback(@RequestParam Map<String, String> callbackParams,
                                     HttpServletRequest request,  // ✅ 추가!
                                     Model model) {
        // ✅ request도 같이 넘겨줘야 함
        NaverLoginProfile naverLoginProfile = naverLoginService.processNaverLogin(callbackParams, request);

        model.addAttribute("naverProfile", naverLoginProfile);
        return "ecotive";
    }


    // 마지막으로 저장된 네이버 프로필 정보를 반환하는 API 엔드포인트
    @GetMapping("/naver/last-profile")
    public ResponseEntity<NaverLoginProfile> getLastNaverProfile() {
        NaverLoginProfile lastProfile = naverLoginService.getLastNaverProfile();
        return ResponseEntity.ok(lastProfile);
    }

    @GetMapping("/naver/userprofile")
    public String profilePage(Model model) {
        // 최근에 저장된 네이버 프로필을 가져와서 모델에 추가
        NaverLoginProfile lastProfile = naverLoginService.getLastNaverProfile();
        model.addAttribute("naverProfile", lastProfile);

        return "Mypage"; // profile.html을 반환하는 논리적 뷰 이름
    }
}
