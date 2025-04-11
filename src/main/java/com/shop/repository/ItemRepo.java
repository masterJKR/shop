package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<Item,Long> {

    List<Item> findAllByOrderByIdDesc(Pageable pageable);
}
