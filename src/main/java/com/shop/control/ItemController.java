package com.shop.control;

import com.shop.dto.item.ItemListDto;
import com.shop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    // 상품 상세 페이지
    @GetMapping("/items/detail/{itemId}")
    public String itemDetail(@PathVariable("itemId") Long itemId, Model model){

        model.addAttribute("item", itemService.getItem(itemId) );
        return "item/itemDetail";
    }

    // 메뉴 클릭시 -  카테고리별 상품 리스트 가져와 출력하기
    @GetMapping("/items/{category}")
    public String itemMenu(@PathVariable("category") String Category,
                           Model model){

        List<ItemListDto> itemListDtos = itemService.getItemList(Category);
        model.addAttribute("itemListDtos", itemListDtos);
        return "item/list";
    }


}



