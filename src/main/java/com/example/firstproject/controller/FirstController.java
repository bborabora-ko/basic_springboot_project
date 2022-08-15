package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Controller선언
public class FirstController {

    @GetMapping("/hi")
    public String niceToMeetYou(){
        return "greetings"; // templates/greetings.mustache -> 브라우저로 전송!
    }
}
