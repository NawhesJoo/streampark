package com.project.restcontroller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RestController
@RequestMapping(value = "/qna")
@RequiredArgsConstructor
public class RestQnAController {
    final String format = "password => {}";

    // Ajax 요청을 처리할 메소드
    @PostMapping("/delete.do")
    public boolean checkPassword(@RequestBody PasswordRequest request) {
        String receivedPassword = request.getPassword(); // Ajax 요청에서 비밀번호 값을 받아옵니다.
        String hashedPasswordFromDB = getPasswordFromDB();
        log.info(format, hashedPasswordFromDB);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(receivedPassword, hashedPasswordFromDB);
    }

    private String getPasswordFromDB() {
        return null;
    }
}

class PasswordRequest {
    private String password;

    public String getPassword() {
        return null;
    }
}
