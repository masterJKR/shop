package com.shop.service;

import com.shop.dto.order.OrderDto;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.entity.Order;
import com.shop.entity.OrderItem;
import com.shop.repository.ItemRepo;
import com.shop.repository.MemberRepo;
import com.shop.repository.OrderRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final ItemRepo itemRepo;
    private final MemberRepo memberRepo;
    private final OrderRepo orderRepo;

    public Long orders(List<OrderDto> orderDtoList, String name) {
        Member member = memberRepo.findByUserId(name);
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderDto orderDto : orderDtoList) {
            Item item = itemRepo.findById(orderDto.getItemId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
            orderItemList.add(orderItem);
        }

        Order order = Order.createOrder(member, orderItemList);
        orderRepo.save(order);

        return order.getId();
    }
}
