package com.shop.dto;

import com.shop.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class MemberForm {
    @NotEmpty(message = "이메일은 필수 입력 값 입니다.")
    @Email(message = "이메일 형식으로 입력 해주세요.")
    private String email;
    @NotEmpty(message = "아이디는 필수 입력 값 입니다.")
    @Length(min = 3, max = 20 , message = "3자리 이상 20자리 이하로 입력하세요")
    private String userId;

    @NotEmpty(message = "비밀번호는 필수 입력 값 입니다.")
    @Length(min = 4 , max = 12 , message = "4자 이상 12자리 이하로 입력 하세요")
    private String password;

    @NotEmpty(message = "주소는 필수 입력 입니다.")
    private String address; // 기본주소
    private String addressDetail; // 상세주소
    private String zipCode; // 우편번호
    @NotEmpty(message = "연락처는 필수 입력 입니다.")
    private String tel;// 연락처


    private static ModelMapper modelMapper = new ModelMapper();

    public static MemberForm from(Member member){
        return modelMapper.map(member, MemberForm.class);
    }
}






