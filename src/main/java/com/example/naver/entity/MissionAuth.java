package com.example.naver.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;


// Entity의 역할
// "DB 테이블과 1:1로 매핑되는 객체"
// Spring Data JPA에서 DB와 CRUD 작업을 할 때 사용해
// 예: missionAuthRepository.save(entity) 이런 식으로 DB에 저장하거나 꺼낼 때
// 저장, 수정, 삭제, 조회 전용
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assigned_mission")
    private String assignedMission;  // 미션 주제

    @Column(name = "user_input_content")
    private String userInputContent;  // 사용자가 입력한 미션 내용

    @Column(name = "is_approved")
    private boolean isApproved = false;

    private String content;

    private String email;          // 로그인한 사용자 이메일

    private String level;          // 난이도 (상, 중, 하)

    private String imagePath;      // 저장된 이미지 경로 (ex. /files/uuid.jpg)

    private String filePath;

    private LocalDateTime createdAt;  // 인증 등록 시각

    private boolean rejected; // 거절 사유

    private String rejectReason;
}