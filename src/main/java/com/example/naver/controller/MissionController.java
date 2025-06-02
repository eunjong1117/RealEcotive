package com.example.naver.controller;

import com.example.naver.entity.MissionAuth;
import com.example.naver.repository.MissionAuthRepository;
import com.example.naver.service.MissionAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class MissionController {
    @Autowired
    private MissionAuthRepository missionAuthRepository;

    private final MissionAuthService missionService; // ✅ 필드 선언 추가

    @PostMapping("/api/mission/auth/upload")
    public String uploadMission(@RequestParam("photo") MultipartFile photo,
                                @RequestParam("content") String content,
                                @RequestParam("level") String level,
                                HttpSession session,
                                Model model) {
        // 로그인 사용자 이메일 가져오기
        String email = (String) session.getAttribute("email");

        // ✅ 미션 저장 로직 (예시로 서비스 호출)
        try {
            missionService.saveMission(email, level, content, photo);
            model.addAttribute("uploaded", true);
            model.addAttribute("approved", false);
        } catch (IOException e) {
            e.printStackTrace(); // 또는 로그 출력
            model.addAttribute("error", "파일 업로드 중 문제가 발생했습니다.");
        }
        return "Mission";
    }

    @GetMapping("/MissionInfo")
    public String MissionPage(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email");

        boolean hasUploaded = missionService.hasUploadedToday(email); // 오늘 업로드했는지 확인
        boolean isApproved = missionService.isApproved(email);        // 승인 여부 확인

        model.addAttribute("uploaded", hasUploaded);
        model.addAttribute("approved", isApproved);

        Optional<MissionAuth> lastMissionOpt = missionAuthRepository.findTopByEmailOrderByCreatedAtDesc(email);
        if (lastMissionOpt.isPresent()) {
            MissionAuth lastMission = lastMissionOpt.get();
            model.addAttribute("uploaded", true);
            model.addAttribute("content", lastMission.getContent());
            model.addAttribute("level", lastMission.getLevel());
            model.addAttribute("imagePath", lastMission.getFilePath()); // /files/xxx.png 형태
        } else {
            model.addAttribute("uploaded", false);
        }
        return "Mission";
    }


}
