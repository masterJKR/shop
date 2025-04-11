package com.shop.control;

import com.shop.dto.item.ItemForm;
import com.shop.service.ItemAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ItemAdminService itemAdminService;

    //  관리자를 위한 컨트롤러~
    //  관리자로 로그인하고  상품 등록 ,관리 에 관한 요청을 받는 녀석~

    //  상품등록 페이지 요청 - localhost/admin/item/new
    @GetMapping("/item/new")
    public String itemNew(Model model){

        model.addAttribute("itemForm" , new ItemForm());

        return "admin/itemForm";
    }

    // 상품등록 form 데이터 전송 요청 - /admin/item/new
    @PostMapping("/item/new")
    public String itemSave(@Valid ItemForm itemForm ,
                 BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){ // 필수입력값 을 작성하지 않은경우
            return "admin/itemForm";
        }


        return "redirect:/admin/item/new";
    }
}



