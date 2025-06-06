package com.example.naver.controller;

import com.example.naver.entity.NaverLoginProfile;
import com.example.naver.repository.MissionAuthRepository;
import com.example.naver.dto.MissionAuthDto;
import com.example.naver.entity.MissionAuth;
import com.example.naver.service.MissionAuthService;
import com.example.naver.service.NaverLoginService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.naver.dto.RejectRequestDto;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Controller
@RequiredArgsConstructor
public class MissionAuthController {

    private final MissionAuthService missionAuthService;
    private final NaverLoginService naverLoginService;
    private final MissionAuthRepository missionAuthRepository;


    @PostMapping("/api/mission/auth")
    public String uploadMission(@RequestParam("assignedMission") String assignedMission,
                                @RequestParam("level") String level,
                                @RequestParam("content") String content,
                                @RequestParam("photo") MultipartFile file, // MultipartFile 타입으로 받기
                                HttpSession session,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        System.out.println("🔵 업로드 시작");

        try {
            // ✅ 이메일 얻기: 세션 or 로그인 서비스
            String email = (String) session.getAttribute("email");
            if (email == null) {
                email = naverLoginService.getLastNaverProfile().getEmail();
            }

            System.out.println("✅ email: " + email);
            System.out.println("✅ assignedMission: " + assignedMission);
            System.out.println("✅ level: " + level);
            System.out.println("✅ content: " + content);
            System.out.println("✅ filename: " + file.getOriginalFilename());

            // 미션 저장
            missionAuthService.saveMission(email, assignedMission, level, content, file);

            // 미션이 없으면 기본 메시지를 전달
            if (assignedMission == null || assignedMission.isEmpty()) {
                assignedMission = "미션 주제를 선택하세요!";
            }

            // 리다이렉트 후 Thymeleaf 렌더링에 활용
            redirectAttributes.addFlashAttribute("uploaded", true);
            redirectAttributes.addFlashAttribute("approved", false);
            redirectAttributes.addFlashAttribute("assignedMission", assignedMission); // DB에서 저장된 미션
            System.out.println("🟢 업로드 끝");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "업로드 실패: " + e.getMessage());
            return "Mission";
        }

        return "redirect:/Mission"; // 또는 redirect:/Mission (원하는 동작에 따라)
    }



    // MissionController.java 코드, 외부로 분리한 새로운 클래스
    @GetMapping("/api/admin/mission-list")
    @ResponseBody
    public List<MissionAuthDto> getMissionList() {
        return missionAuthRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(MissionAuthDto::new)
                .collect(Collectors.toList());
    }

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

        Optional<MissionAuth> latestMission = missionAuthRepository.findTopByEmailOrderByCreatedAtDesc(profile.getEmail());

        if (latestMission.isPresent()) {
            MissionAuth mission = latestMission.get();

            model.addAttribute("approved", mission.isApproved());
            model.addAttribute("uploaded", true);
            model.addAttribute("content", mission.getContent());  // ✅ 미션 내용
            model.addAttribute("level", mission.getLevel());      // ✅ 난이도
            model.addAttribute("imagePath", mission.getImagePath()); // ✅ 이미지 경로
            model.addAttribute("rejected", mission.isRejected());
            model.addAttribute("rejectReason", mission.getRejectReason());
        } else {
            model.addAttribute("uploaded", false);
            model.addAttribute("approved", false);
        }

        return "Mission";
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

    @PostMapping("/api/admin/reject")
    @ResponseBody
    public String rejectMission(@RequestBody RejectRequestDto rejectRequest) {
        MissionAuth mission = missionAuthRepository.findById(rejectRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 미션이 없습니다. id=" + rejectRequest.getId()));

        mission.setApproved(false);
        mission.setRejected(true); // 필드 있어야 함
        mission.setRejectReason(rejectRequest.getReason());
        missionAuthRepository.save(mission);
        return "ok";
    }

}
