package com.localai.assistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
    @GetMapping("/")
    public String home(){
        return "chat";
    }
    @GetMapping("/health-tool")
public String healthToolsPage(){
    return "health-tool";
}
}
