package com.shop.service;

import com.shop.repository.ItemImageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemImageAdminService {
    private final ItemImageRepo itemImageRepo;
    private final FileService fileService;
}
