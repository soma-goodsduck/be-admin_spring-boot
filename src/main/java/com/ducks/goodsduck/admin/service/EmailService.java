package com.ducks.goodsduck.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import java.util.Random;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender emailSender;

    public String sendEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        String code = createCode();
        message.setTo(email);
        message.setSubject("[GOODSDUCK-ADMIN] 이메일 인증 코드");
        message.setText("인증번호 6자리 " + code + " 를 입력해주세요.");
        emailSender.send(message);
        return code;
    }

    private String createCode() {
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 6; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    code.append((char) (rnd.nextInt(26) + 97));
                    break;
                case 1:
                    code.append((char) (rnd.nextInt(26) + 65));
                    break;
                case 2:
                    code.append((rnd.nextInt(10)));
                    break;
            }
        }
        return code.toString();
    }
}
