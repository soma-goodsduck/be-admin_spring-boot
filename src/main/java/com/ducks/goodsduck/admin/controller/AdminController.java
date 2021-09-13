package com.ducks.goodsduck.admin.controller;

import com.ducks.goodsduck.admin.model.dto.AdminLoginDto;
import com.ducks.goodsduck.admin.model.dto.CheckDto;
import com.ducks.goodsduck.admin.model.dto.AdminRegisterDto;
import com.ducks.goodsduck.admin.model.dto.VerifyEmailDto;
import com.ducks.goodsduck.admin.model.entity.Admin;
import com.ducks.goodsduck.admin.repository.AdminRepository;
import com.ducks.goodsduck.admin.repository.EmailAuthenticationRepository;
import com.ducks.goodsduck.admin.service.EmailService;
import com.ducks.goodsduck.admin.util.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminController {

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final AdminRepository adminRepository;
    private final EmailAuthenticationRepository emailAuthenticationRepository;

    // FEAT : 이메일 인증코드 전송
    @GetMapping("/send/email")
    @ResponseBody
    public String sendEmail(@RequestParam("email") String email) {
        email = "kwpark96@naver.com";
        String code = emailService.sendEmail(email);
        emailAuthenticationRepository.setCode(email, code);
        return code;
    }

    // FEAT : 이메일 인증코드 검증
    @PostMapping("/verify/email")
    @ResponseBody
    public CheckDto verifyEmailCode(@RequestBody VerifyEmailDto verifyEmailDto) {
        if(verifyEmailDto.getEmail() != null && verifyEmailDto.getCode() != null &&
                emailAuthenticationRepository.getCode(verifyEmailDto.getEmail()).equals(verifyEmailDto.getCode())) {
            return new CheckDto(1L);
        } else {
            return new CheckDto(-1L);
        }
    }

    // FEAT : 회원가입 폼
    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        return "admin_register";
    }

    // FEAT : 회원가입
    @PostMapping("/register")
    public String register(@ModelAttribute AdminRegisterDto adminRegisterDto, Model model) {

        Admin findAdmin = adminRepository.findByEmail(adminRegisterDto.getEmail());
        if(findAdmin.equals(adminRegisterDto.getEmail())) {
            model.addAttribute("type", "registerFail");
            model.addAttribute("message", "중복된 이메일입니다.");
            return "message";
        }

        Admin admin = new Admin(adminRegisterDto.getEmail());
        String encodedPassword = passwordEncoder.encode(adminRegisterDto.getPassword());
        admin.setPassword(encodedPassword);
        adminRepository.save(admin);

        model.addAttribute("type", "adminRegister");
        model.addAttribute("message", "회원가입에 성공했습니다.");
        return "message";
    }

    // FEAT : 로그인 폼
    @GetMapping("/login")
    public String getLoginForm(Model model) {
        return "admin_login";
    }

    // FEAT : 로그인
    @PostMapping("/login")
    public String login(@ModelAttribute AdminLoginDto adminLoginDto, Model model,
                        @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request) {

        Admin admin = adminRepository.findByEmail(adminLoginDto.getEmail());

        // 세션이 있으면 기존 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, admin);

        if(admin == null) {
            model.addAttribute("type", "loginFail");
            model.addAttribute("message", "이메일이 틀렸습니다.");
            return "message";
        }
        else if(!(passwordEncoder.matches(adminLoginDto.getPassword(), admin.getPassword()))) {
            model.addAttribute("type", "loginFail");
            model.addAttribute("message", "비밀번호가 틀렸습니다.");
            return "message";
        }

        model.addAttribute("type", "loginSuccess");
        model.addAttribute("message", "로그인이 완료되었습니다.");
        model.addAttribute("redirectURL", redirectURL);
        return "message";
    }
}
