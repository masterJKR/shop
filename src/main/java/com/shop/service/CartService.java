package com.shop.service;

import com.shop.repository.CartItemRepo;
import com.shop.repository.CartRepo;
import com.shop.repository.ItemRepo;
import com.shop.repository.MemberRepo;
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
}




