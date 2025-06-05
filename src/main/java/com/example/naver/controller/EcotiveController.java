package com.example.naver.controller;

import com.example.naver.entity.MissionAuth;
import com.example.naver.repository.MissionAuthRepository;
import com.example.naver.service.NaverLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class EcotiveController {

    private final NaverLoginService naverLoginService;          // ✅ 주입
    private final MissionAuthRepository missionAuthRepository;  // ✅ 주입

    @GetMapping("/Ecotive")
    public String ecotivePage(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            email = naverLoginService.getLastNaverProfile().getEmail();
        }

        Optional<MissionAuth> latestMission = missionAuthRepository.findTopByEmailOrderByCreatedAtDesc(email);

        if (latestMission.isPresent()) {
            MissionAuth mission = latestMission.get();
            model.addAttribute("missionContent", mission.getContent());
            model.addAttribute("missionUploaded", true);
        } else {
            model.addAttribute("missionUploaded", false);
        }

        return "Ecotive";
    }


}
