package com.shop.control;

import com.shop.dto.MemberForm;
import com.shop.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/signIn")
    public String login( Model model ){
        return "member/signIn";
    }

    // 로그인 실패시 - 다시 로그인 페이지 제공
    @GetMapping("/signIn/error")
    public String loginfail(Model model){
        model.addAttribute("loginErrorMsg","아이디 나 비밀번호 좀 다시 허여봅써");
        return "member/signIn";
    }

    @GetMapping("/members/signUp")
    public String join( Model model ){
        model.addAttribute("memberForm", new MemberForm());
        return "member/signUp";
    }

    @PostMapping("/members/signUp")
    public String join(@Valid MemberForm memberForm ,
                       BindingResult bindingResult, Model model){

        if( bindingResult.hasErrors()){ // 회원가입 양식에 올바르지 않은값 입력했다면
            return "member/signUp";
        }
        // 양식에 전부 잘 입력했다면  회원가입 을 위한 테이블에 저장하기 진행
        try {
            memberService.saveMember(memberForm, passwordEncoder);
        }catch(IllegalStateException i){ // catch는 try내부에 예외 발생시 실행
            model.addAttribute("errorMessage" ,  i.getMessage() );
            // errorMessage 에  "아이디가 중복 입니다." 문자열 넣기
            return "member/signUp";
        }
        return "redirect:/signIn"; // 회원가입 잘 되었다면 로그인페이지 이동
    }




}
