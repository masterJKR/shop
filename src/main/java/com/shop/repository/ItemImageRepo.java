package com.shop.repository;

import com.shop.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemImageRepo extends JpaRepository<ItemImage, Long> {
}
