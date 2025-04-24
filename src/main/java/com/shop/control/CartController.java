package com.shop.control;

import com.shop.dto.cart.CartItemDto;
import com.shop.dto.cart.CartListDto;
import com.shop.dto.cart.CartOrderDto;
import com.shop.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

//    @PostMapping("/cart/list")
//    public @ResponseBody ResponseEntity cartList( Principal principal) {
//
//        List<CartListDto> cartListDto = cartService.getCartList(principal.getName());
//
//        return new ResponseEntity<CartItemDto>(cartListDto, HttpStatus.OK);
//    }


    // 장바구니에서 선택한 상품 주문 하기
    @PostMapping("/cart/orders")
    public @ResponseBody ResponseEntity orderItems(@RequestBody CartOrderDto cartOrderDto, Principal principal){
        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        if(cartOrderDtoList == null || cartOrderDtoList.size() == 0){
            return new ResponseEntity<String>("주문할 상품을 선택해주세요", HttpStatus.FORBIDDEN);
        }

        for (CartOrderDto cartOrder : cartOrderDtoList) {
            if(!cartService.validateCartItem(cartOrder.getCartItemId(), principal.getName())){
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }
        }

        Long orderId = cartService.orderCartItem(cartOrderDtoList, principal.getName());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }


    //장바구니에서 상품 삭제 하기
    @DeleteMapping("cart/Item/{cartItemId}")
    public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId, Principal principal) {
        if(!cartService.validateCartItem(cartItemId, principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.deleteCartItem(cartItemId);

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);

    }


    //장바구니 수량 변경
    @PatchMapping("/cart/Item/{cartItemId}")
    public @ResponseBody ResponseEntity updateCartItem(@PathVariable("cartItemId") Long cartItemId, int count, Principal principal){
        if(count <= 0){
            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        } else if(!cartService.validateCartItem(cartItemId, principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.updateCartItemCount(cartItemId, count);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }


    @GetMapping("/cart/list")
    public String cartList( Principal principal,Model model) {

        List<CartListDto>  cartListDtos = cartService.getCartList(principal.getName());

        model.addAttribute("cartListDtos", cartListDtos);
        return "cart/cartList";
    }

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

            return new ResponseEntity<Long>(1L, HttpStatus.OK);
    }

}
