package com.example.naver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MissionController {

    @GetMapping("/Mission")
    public String MissionPage() {
        return "Mission";
    }
}
