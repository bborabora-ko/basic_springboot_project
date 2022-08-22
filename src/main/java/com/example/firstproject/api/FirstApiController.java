package com.example.firstproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Rest API용 컨트롤러, 기본적으로 JSON반환(일반 컨트롤러는 view페이지 반환함)
public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello(){
        return "hello, world!!";
    }
}
