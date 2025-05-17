package com.example.naver.controller;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

@Controller
public class NaverLogoutController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // false: 세션 없으면 null 반환
        if (session != null) {
            String accessToken = (String) session.getAttribute("accessToken");

            if (accessToken != null) {
                // 네이버에 토큰 만료 요청 보내기
                try {
                    String clientId = "PnXxGcAqLM5nVlmjE8zz";
                    String clientSecret = "2xQHGU2_CV";

                    String logoutUrl = "https://nid.naver.com/oauth2.0/token?" +
                            "grant_type=delete" +
                            "&client_id=" + clientId +
                            "&client_secret=" + clientSecret +
                            "&access_token=" + accessToken +
                            "&service_provider=NAVER";

                    RestTemplate restTemplate = new RestTemplate();
                    URI uri = new URI(logoutUrl);

                    restTemplate.exchange(uri, HttpMethod.POST, null, String.class); // 요청만 보내면 됨

                } catch (Exception e) {
                    System.out.println("네이버 로그아웃 중 오류 발생: " + e.getMessage());
                }
            }

            // 세션 무효화
            session.invalidate();
        }

        return "redirect:/login"; // 로그아웃 후 로그인 페이지로 이동
    }

    @GetMapping("/NaverLogout")
    public String naverLogoutPage() {
        // 네이버 로그아웃을 iframe, JS 또는 메타 태그로 호출하고
        // 완료 후 바로 내 로그인 페이지로 리다이렉트하는 뷰 보여주기
        return "NaverLogout"; // templates/NaverLogout.html
    }

}
