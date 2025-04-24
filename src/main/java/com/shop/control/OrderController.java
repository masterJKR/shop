package com.shop.control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class OrderController {



    // 주문내역 화면 보기
    @GetMapping("/order/list")
    public String orderList(Model model) {

        return "order/orderList";
    }
}
