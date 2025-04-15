package com.shop.control;

import com.shop.dto.cart.CartItemDto;
import com.shop.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/cart/add")
    public @ResponseBody ResponseEntity cartAdd(@RequestBody @Valid CartItemDto cartItemDto,
                                  BindingResult bindingResult, Principal principal) {
            if(bindingResult.hasErrors()){
                //수량을 1개 이상 선택하지 않았다면 실행
                StringBuilder sb = new StringBuilder(); // json응답은 문자열로 보내야 된다.
                List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                for(FieldError fieldError : fieldErrors)
                    sb.append( fieldError.getDefaultMessage());
                // json 응답을 보내기 위해 StringBuilder에 저장
                return new ResponseEntity<String>(sb.toString() , HttpStatus.BAD_REQUEST);
            }

            // 로그인 계정 정보 가져와서  해당 계정의 카트에 담기
            String userId = principal.getName(); // 로그인한 회원의 아이디
            try{
                cartService.addCart( cartItemDto , userId );
            }catch(Exception e){  // CartService 클래스의 메서드를 통해 테이블에 저장 하는 과정에서
                // 오류가 있다면 ....
                e.printStackTrace();
                return new ResponseEntity<String>( e.getMessage() ,HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
