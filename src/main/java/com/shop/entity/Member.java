package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter@Setter
@Table( name = "shop_member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String userId;
    private String password;
    private String address; // 기본주소
    private String addressDetail; // 상세주소
    private String zipCode; // 우편번호
    private String tel;// 연락처

    // 권한
    @Enumerated(EnumType.STRING) //  열거형의 이름 그대로 저장 - USER 라고저장
    private Role role;

    private static ModelMapper modelMapper = new ModelMapper();

    static{
        modelMapper.typeMap(MemberForm.class, Member.class)
                .addMappings( mapper -> {
                   mapper.skip(Member::setId);
                   mapper.skip(Member::setRole);
                });
          // MemberForm에 id, role이 없기 때문에 skip 한다.
        //  도착지의  클래스 set 메서드 기준으로 동작 하기 때문에 출발지 클래스에 없는 변수는 skip
    }

    public static Member from(MemberForm memberForm , PasswordEncoder passwordEncoder){
        Member member = modelMapper.map(memberForm, Member.class);

        // 시큐리티를 통해 비밀번호를 암호화 해서 저장한다.
        String password = passwordEncoder.encode( member.getPassword() );
        member.setPassword( password );

        member.setRole(Role.USER);
        return member;
    }
}




