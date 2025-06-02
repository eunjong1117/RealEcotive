package com.example.naver.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "is_approved")
    private boolean isApproved = false;

    private String email;          // 로그인한 사용자 이메일

    private String level;          // 난이도 (상, 중, 하)

    @Column(length = 10000)
    private String content;        // 인증 설명

    private String imagePath;      // 저장된 이미지 경로 (ex. /files/uuid.jpg)

    private String filePath;

    private LocalDateTime createdAt;  // 인증 등록 시각
}