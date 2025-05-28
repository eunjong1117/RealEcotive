package com.example.naver.dto;

import com.example.naver.entity.MissionAuth;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MissionAuthDto {
    private Long id;
    private String email;
    private String level;
    private String content;
    private String createdAt;
    private boolean isApproved;

    public MissionAuthDto(MissionAuth mission) {
        this.id = mission.getId();
        this.email = mission.getEmail();
        this.level = mission.getLevel();
        this.content = mission.getContent();
        this.createdAt = (mission.getCreatedAt() != null)
                ? mission.getCreatedAt().toString()
                : "";  // null일 땐 빈 문자열
        this.isApproved = mission.isApproved();
    }


    public boolean isApproved() {
        return isApproved;
    }
}