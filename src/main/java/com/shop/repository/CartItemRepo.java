package com.shop.repository;


import com.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long id, Long id1);

    List<CartItem> findByCartIdOrderByIdDesc(Long id);
}
