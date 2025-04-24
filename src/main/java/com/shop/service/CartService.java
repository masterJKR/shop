package com.shop.service;

import com.shop.dto.cart.CartItemDto;
import com.shop.dto.cart.CartListDto;
import com.shop.dto.cart.CartOrderDto;
import com.shop.dto.order.OrderDto;
import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ItemRepo itemRepo;
    private final MemberRepo memberRepo;
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final ItemImageRepo itemImageRepo;
    private final OrderService orderService;

    //장바구니 목록 가져오기
    public List<CartListDto> getCartList(String userId) {
        List<CartListDto> cartListDtos = new ArrayList<>();

        Member member = memberRepo.findByUserId(userId);
        Cart cart = cartRepo.findByMemberId(member.getId());

        List<CartItem> cartItems = cartItemRepo.findByCartIdOrderByIdDesc(cart.getId());
        List<Item> items = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            String imgUrl = itemImageRepo.findImgUrlByItemId(cartItem.getItem().getId());
            CartListDto cartListDto = CartListDto.fromCart( cartItem ,imgUrl);
            cartListDtos.add(cartListDto);
        }


        return cartListDtos;
    }

    // 장바구니에 넣기
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
    // 수량 변경 하기 전에 장바구니 주인이 맞는가 확인하기
    public boolean validateCartItem(Long cartItemId, String name) {
        Member curMember = memberRepo.findByUserId(name);
        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartItem.getCart().getMember();

        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }
    // 상품 수량 변경
    public void updateCartItemCount(Long cartItemId, int count) {
        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItem.updateCount(count); //장바구니에 담은 상품 수량 증가 - 엔티티 객체의 값을 변경 해주면 자동 update쿼리실행
    }

    // 상품 삭제
    public void deleteCartItem(Long cartItemId) {

        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepo.delete(cartItem);
    }

    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String name) {
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepo
                    .findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            orderDto.setCount(cartItem.getCount());
            orderDtoList.add(orderDto);
        }

        Long orderId = orderService.orders(orderDtoList, name);
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepo
                    .findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);
            cartItemRepo.delete(cartItem);
        }

        return orderId;
    }
}




