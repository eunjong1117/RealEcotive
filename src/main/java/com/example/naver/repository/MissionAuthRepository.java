package com.example.naver.repository;

import com.example.naver.entity.MissionAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MissionAuthRepository extends JpaRepository<MissionAuth, Long> {

    // ✅ 승인된 인증 목록
    List<MissionAuth> findByEmailAndIsApprovedTrue(String email);

    // ✅ 가장 최근 인증 1건
    Optional<MissionAuth> findTopByEmailOrderByCreatedAtDesc(String email);

    // ✅ 오늘 업로드했는지 여부 확인
    boolean existsByEmailAndCreatedAtBetween(String email, LocalDateTime start, LocalDateTime end);
}