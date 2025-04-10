package com.shop.service;

import com.shop.repository.ItemImageRepo;
import com.shop.repository.ItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemAdminService {
    private final ItemRepo itemRepo;
    private final ItemImageRepo itemImageRepo;
    private final ItemImageAdminService itemImageAdminService;
}
