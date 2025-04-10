package com.shop.service;

import com.shop.dto.MemberForm;
import com.shop.entity.Member;
import com.shop.repository.MemberRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements UserDetailsService {

    @Autowired
    private MemberRepo memberRepo;


    public void saveMember(@Valid MemberForm memberForm, PasswordEncoder passwordEncoder) {
        // dto->entity  ,  비밀번호 변환을 위해 passwordEncoder
        Member member = Member.from(memberForm, passwordEncoder);
        // entity로 만들어 줘야  테이블에 저장 할 수 있다.

        // 아이디가 중복 되지 않게 확인하기
        // 확인해서  중복이라면  강제로 예외 발생 시킬 것이다.
        validUserId(member); // 아이디 중복 체크하기 위한 메서드 - 중복이면 예외 발생
        memberRepo.save(member); // 중복아니면 테이블에 저장 하기
    }

    // 아이디 중복 체크 메서드
    private void validUserId(Member member){
        Member find = memberRepo.findByUserId( member.getUserId() );

        if( find != null ){
            throw new IllegalStateException("아이디가 중복 입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //스프링 시큐리티 사용시 커스텀 로그인 DB의 데이터로 로그인진행하기 때문에 오버라이딩

        // 로그인 시 입력한 아이디로 회원 테이블에서 정보 조회
        Member member = memberRepo.findByUserId(username);

        if( member == null){
            throw new UsernameNotFoundException(username);
        }

        return User.builder()
                .username(member.getUserId())
                .password(member.getPassword())
                .roles(member.getRole().toString()).build();
    }
}
