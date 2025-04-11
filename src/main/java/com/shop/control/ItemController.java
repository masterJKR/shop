package com.shop.control;

import com.shop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items/{itemId}")
    public String itemDetail(@PathVariable("itemId") Long itemId, Model model){

        model.addAttribute("item", itemService.getItem(itemId) );
        return "item/itemDetail";
    }
}
