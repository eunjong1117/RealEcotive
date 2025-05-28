// WebConfig.java
package com.example.naver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /files/** 로 시작하는 요청은 실제 파일 시스템의 files 폴더에서 찾게 설정
        registry.addResourceHandler("/upload/files/**")
                .addResourceLocations("file:///C:/Users/whdgu/Desktop/LJHSTUDY/RealEcotive/upload/files/");
    }
}
