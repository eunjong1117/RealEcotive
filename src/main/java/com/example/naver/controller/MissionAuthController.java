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
                                HttpServletRequest request) {

        System.out.println("🚀 미션 업로드 요청 도착"); // ✅ 이 줄 추가

        try {
            String email = naverLoginService.getLastNaverProfile().getEmail();
            System.out.println("✅ 이메일: " + email);
            System.out.println("✅ level: " + level);
            System.out.println("✅ content: " + content);
            System.out.println("✅ 파일 이름: " + file.getOriginalFilename());

            missionAuthService.saveMission(email, level, content, file);

            return "redirect:/Mission";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/Mission?error";
        }
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
