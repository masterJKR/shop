package com.shop.service;

import com.shop.dto.cart.CartItemDto;
import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.repository.CartItemRepo;
import com.shop.repository.CartRepo;
import com.shop.repository.ItemRepo;
import com.shop.repository.MemberRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ItemRepo itemRepo;
    private final MemberRepo memberRepo;
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;

    public void addCart(@Valid CartItemDto cartItemDto, String userId) {

        // 장바구니에 담고자하는 상품을 테이블에서 조회 - 조회 결과 가 없다면 null값 넣기
        Item item = itemRepo.findById(cartItemDto.getItemId()).orElse(null);
        Member member = memberRepo.findByUserId(userId);

        //  해당 회원이  카트를 가지고 있냐??  없다면 카트 만들기!!
        Cart cart = cartRepo.findByMemberId( member.getId() );
        // 카트 테이블에  회원별  카트 제공을 위해  회원 id가 저장 되어있다.  만약 없다면 null
        if( cart == null ){
            cart = Cart.createCart(member);
            cartRepo.save(cart);
        }

        // 같은 상품을 또 장바구니 넣는다면  수량 증가
        CartItem cartItem = cartItemRepo.findByCartIdAndItemId(cart.getId(), item.getId());

        if( cartItem != null ) { // 이미 장바구니 해당 상품이 담겨있다. - 수량증가 해야됨
            cartItem.setCount( cartItem.getCount() + cartItemDto.getCount()  );

        }else{ // 장바구니에 상품이 없다 그러니까 새로 테이블에 저장
            CartItem newCartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepo.save(newCartItem);
        }
    }
}




