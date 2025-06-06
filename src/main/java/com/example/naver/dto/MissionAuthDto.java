package com.example.naver.dto;

import com.example.naver.entity.MissionAuth;
import lombok.Getter;
import lombok.Setter;

// DTO(Data Transfer Object)의 역할, 프론트에 넘겨줄 데이터 형식
// "데이터를 안전하고 효율적으로 전달하기 위한 객체"
// 클라이언트 ↔ 서버 사이, 혹은 서버 내부에서 데이터를 전달할 때 사용해.
//
@Getter
public class MissionAuthDto {
    private Long id;
    private String email;
    private String level;
    private String content;
    private String createdAt;
    private boolean isApproved;
    private boolean isRejected;
    private String assignedMission;

    public MissionAuthDto(MissionAuth mission) {
        this.id = mission.getId();
        this.email = mission.getEmail();
        this.level = mission.getLevel();
        this.content = mission.getContent();
        this.createdAt = (mission.getCreatedAt() != null)
                ? mission.getCreatedAt().toString()
                : "";  // null일 땐 빈 문자열
        this.isApproved = mission.isApproved();
        this.assignedMission = mission.getAssignedMission();
        this.isRejected = mission.isRejected();

    }


    public boolean isApproved() {
        return isApproved;
    }

    public boolean isRejected() {
        return isRejected;
    }
}