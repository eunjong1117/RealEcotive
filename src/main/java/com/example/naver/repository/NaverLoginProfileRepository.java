package com.example.naver.repository;

import com.example.naver.entity.NaverLoginProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NaverLoginProfileRepository extends JpaRepository<NaverLoginProfile, Long> {

    @Query("SELECT p FROM NaverLoginProfile p ORDER BY p.loginTime DESC") // 실제 필드명에 맞게 수정
    List<NaverLoginProfile> findLatestProfiles();
}
