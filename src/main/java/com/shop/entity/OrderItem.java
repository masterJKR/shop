package com.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name ="order_id")
    private Order order;
    @Column
    private int orderPrice; //주문가격
    @Column
    private int count;//주문 수량

    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem( item );
        orderItem.setOrderPrice( item.getPrice());
        orderItem.setCount( count );
        item.removeStock(count); // 주문수량 만큼 상품 수량감소

        return orderItem;
    }

    public int getTotalPrice(){  //총결제금액
        return orderPrice*count;
    }

    public void cancel(){  //주문취소 - 수량 원복
        this.getItem().addStock(count);
    }
}
