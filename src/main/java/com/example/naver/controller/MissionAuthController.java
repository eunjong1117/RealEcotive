package com.example.naver.controller;

import com.example.naver.entity.NaverLoginProfile;
import com.example.naver.repository.MissionAuthRepository;
import com.example.naver.dto.MissionAuthDto;
import com.example.naver.entity.MissionAuth;
import com.example.naver.service.MissionAuthService;
import com.example.naver.service.NaverLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MissionAuthController {

    private final MissionAuthService missionAuthService;
    private final NaverLoginService naverLoginService;
    private final MissionAuthRepository missionAuthRepository;

    @PostMapping("/api/mission/auth")
    public String uploadMission(@RequestParam("level") String level,
                                @RequestParam("content") String content,
                                @RequestParam("photo") MultipartFile file,
                                HttpSession session,
                                Model model) {

        try {
            // ✅ 이메일 얻기: 세션 or 로그인 서비스
            String email = (String) session.getAttribute("email");
            if (email == null) {
                email = naverLoginService.getLastNaverProfile().getEmail();
            }

            System.out.println("✅ email: " + email);
            System.out.println("✅ level: " + level);
            System.out.println("✅ content: " + content);
            System.out.println("✅ filename: " + file.getOriginalFilename());

            // ✅ 미션 저장
            missionAuthService.saveMission(email, level, content, file);

            model.addAttribute("uploaded", true);
            model.addAttribute("approved", false);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "업로드 실패: " + e.getMessage());
        }

        return "Mission"; // 또는 redirect:/Mission (원하는 동작에 따라)
    }

    // MissionController.java

    @GetMapping("/api/admin/mission-list")
    @ResponseBody
    public List<MissionAuthDto> getMissionList() {
        return missionAuthRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(MissionAuthDto::new)
                .collect(Collectors.toList());
    }

    // MissionAuthController.java
    @GetMapping("/admin/mission/{id}")
    public String getMissionDetail(@PathVariable Long id, Model model) {
        MissionAuth mission = missionAuthService.findById(id); // id로 미션 찾기
        model.addAttribute("mission", mission);
        return "AdminDetail"; // AdminDetail.html로 이동
    }

    @GetMapping("/Mission")
    public String missionPage(Model model) {
        NaverLoginProfile profile = naverLoginService.getLastNaverProfile();
        model.addAttribute("naverProfile", profile);

        // ✅ DB에서 해당 이메일의 가장 최신 미션 가져오기
        Optional<MissionAuth> latestMission = missionAuthRepository.findTopByEmailOrderByCreatedAtDesc(profile.getEmail());

        if (latestMission.isPresent() && latestMission.get().isApproved()) {
            model.addAttribute("approved", true);
        } else {
            model.addAttribute("approved", false);
        }

        return "Mission"; // Mission.html로 이동
    }



    @PostMapping("/api/admin/approve")
    @ResponseBody
    public String approveMission(@RequestParam Long id) {
        MissionAuth mission = missionAuthRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 미션이 없습니다. id=" + id));

        mission.setApproved(true); // 승인 처리
        missionAuthRepository.save(mission); // 저장

        return "ok";
    }


}
