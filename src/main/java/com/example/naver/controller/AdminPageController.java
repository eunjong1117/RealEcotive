package com.example.naver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {
    @GetMapping("/AdminPage")
    public String AdminPage() {
        return "Admin";
    }

    @GetMapping("/Admin")
    public String Admin(){
        return "Admin";
    }

}
