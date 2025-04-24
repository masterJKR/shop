package com.shop.entity;

import com.shop.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL
            , orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems =new ArrayList<>();

    @Column
    private LocalDateTime orderDate; //주문일
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문 상태

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public static Order createOrder(Member member, List<OrderItem> orderItems){
        Order order = new Order();
        order.setMember(member);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate( LocalDateTime.now());
        return order;
    }
    public int getTotalPrice(){  //여러상품 구매시 전체 총 결제금액
        int total =0;
        for(OrderItem orderItem : orderItems){
            total += orderItem.getTotalPrice();
        }
        return total;
    }
    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
}
