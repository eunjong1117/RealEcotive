package com.example.naver.repository;

import com.example.naver.entity.MissionAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MissionAuthRepository extends JpaRepository<MissionAuth, Long> {
    List<MissionAuth> findByEmailAndIsApprovedTrue(String email);

    Optional<MissionAuth> findTopByEmailOrderByCreatedAtDesc(String email);
}