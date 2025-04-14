package com.shop.control;

import com.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;


}
