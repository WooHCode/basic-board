package com.study.board.controller;

import com.study.board.dto.LoginDto;
import com.study.board.entity.Member;
import com.study.board.entity.MyMember;
import com.study.board.service.LoginServiceV1;
import com.study.board.service.LoginServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LoginControllerV2 {
    private final LoginServiceV2 loginServiceV2;


    @GetMapping("/login")
    public String login(@ModelAttribute LoginDto loginDto) {
        return "/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginDto dto, BindingResult result,
                        HttpServletRequest request) {
        if (result.hasErrors()) {
            return "/loginForm";
        }
        Member myMember = loginServiceV2.findMember(dto.getLoginId(), dto.getLoginPw());
        if (myMember == null) {
            result.reject("loginFail","아아디 또는 비밀번호가 맞지않습니다.");
            return "/loginForm";
        }else {
            HttpSession session = request.getSession();
            String redirector = request.getParameter("redirector");
            if (redirector == null){
                redirector = "/";
            }
            session.setAttribute(MemberId.MEMBER_ID, myMember);
            return "redirect:"+redirector;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "/loginForm";
    }
}
