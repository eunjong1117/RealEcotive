package com.example.naver.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.MalformedURLException;

@RestController
public class ImageController {

    @GetMapping("/images/banner.png")
    public Resource showbanner() throws MalformedURLException {
        // 이미지 파일이 저장된 경로 설정 (예: /src/main/resources/static/images/banner.png)
        File file = new File("src/main/resources/static/images/banner.png");

        // 파일이 존재하지 않으면 예외 처리 추가 가능

        // URLResource로 파일 반환
        return new FileSystemResource(file);
    }

    @GetMapping("/images/sprout-earth.png")
    public Resource showSproutEarthImage() throws MalformedURLException {
        // 이미지 파일이 저장된 경로 설정 (예: /src/main/resources/static/images/banner.png)
        File file = new File("src/main/resources/static/images/sprout-earth.png");

        // 파일이 존재하지 않으면 예외 처리 추가 가능

        // URLResource로 파일 반환
        return new FileSystemResource(file);
    }
}
