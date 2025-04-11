package com.shop.repository;

import com.shop.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemImageRepo extends JpaRepository<ItemImage, Long> {
    ItemImage findByItemIdAndRepImgYn(Long id, String y);

    List<ItemImage> findByItemId(Long itemId);
}
