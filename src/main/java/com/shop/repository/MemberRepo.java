package com.shop.repository;


import com.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepo extends JpaRepository<Member, Long> {
    // 회원 가입해서 테이블에 하기위한 메서드는 save이다.  -  이미 존재하는 메서드이기
    // 때문에  선언 해줄 필요 없다.

    // 로그인 할때 필요한 메서드 ,  회원 정보 수정
    public Member findByUserId(String userId);
}
