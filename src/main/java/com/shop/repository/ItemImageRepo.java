package com.shop.repository;

import com.shop.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemImageRepo extends JpaRepository<ItemImage, Long> {
    ItemImage findByItemIdAndRepImgYn(Long id, String y);

    List<ItemImage> findByItemId(Long itemId);

    @Query("select i.imgUrl from ItemImage i where i.id= :item_id")
    String findImgUrlByItemId(@Param("item_id") Long itemId);
}
