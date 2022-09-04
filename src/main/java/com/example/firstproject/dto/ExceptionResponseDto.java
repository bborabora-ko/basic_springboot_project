package com.example.firstproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExceptionResponseDto { //예외발생 시 반환할 reponse객체
    private LocalDateTime time;
    private Boolean isSuccess;
    private String message;
    private Map<String, String> details;
}
