package com.example.naver.service;

import com.example.naver.entity.MissionAuth;
import com.example.naver.repository.MissionAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MissionAuthService {

    private final MissionAuthRepository missionAuthRepository;

    @Value("${file.upload-dir}") // application.properties 에 설정한 경로
    private String uploadDir;

    public void saveMission(String email, String assignedMission, String level, String content, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어 있습니다.");
        }

        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String savedName = UUID.randomUUID() + ext;
        String fullPath = uploadDir + File.separator + savedName;

        // ✅ 디렉토리 자동 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // 여기 추가!
        }

        // ✅ 파일 저장
        file.transferTo(new File(fullPath));

        // ✅ DB 저장
        MissionAuth missionAuth = MissionAuth.builder()
                .assignedMission(assignedMission)
                .email(email)
                .level(level)
                .content(content)
                .imagePath("/upload/files/" + savedName) // 프론트에서 불러올 경로
                .createdAt(LocalDateTime.now())
                .isApproved(false)
                .build();

        missionAuthRepository.save(missionAuth);
    }


    public MissionAuth findById(Long id) {
        return missionAuthRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 미션이 존재하지 않습니다. id=" + id));
    }

    public void approveMission(Long id) {
        MissionAuth mission = missionAuthRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 미션이 존재하지 않습니다."));
        mission.setApproved(true);
        missionAuthRepository.save(mission);
    }

    public boolean hasUploadedToday(String email) {
        LocalDate today = LocalDate.now();
        return missionAuthRepository.existsByEmailAndCreatedAtBetween(
                email,
                today.atStartOfDay(),
                today.plusDays(1).atStartOfDay()
        );
    }

    public boolean isApproved(String email) {
        return missionAuthRepository.findTopByEmailOrderByCreatedAtDesc(email)
                .map(MissionAuth::isApproved)
                .orElse(false);
    }


}
