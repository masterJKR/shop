package com.shop.control;

import com.shop.dto.item.ItemForm;
import com.shop.dto.item.ItemListDto;
import com.shop.dto.item.ItemSearchDto;
import com.shop.service.ItemAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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
                           BindingResult bindingResult,
                           @RequestParam("itemImgFile") List<MultipartFile> multipartFileList,
                           Model model){

        if(bindingResult.hasErrors()){ // 필수입력값 을 작성하지 않은경우
            return "admin/itemForm";
        }
        // 잘 입력했다면~~  테이블에 저장 되도록 해야겠지요??~
        // 그런 데 말입니다.      이미지를 한장도 넣지 않았다면 말입니다.
        //  저장 하면 안되요~~~  그래서 이미지 안 넣은 경우도~~ 해야 겠죠?~ 그쵸??? 대답해!

        if(multipartFileList.get(0).isEmpty() && itemForm.getId() == null){
            //이미지가 한장도 선택하지 않았다면, 무조건 한장 이상은 선택해야 한다.!!!
            model.addAttribute("errorMessage", "첫번째 상품이미지는필수 등록입니다.");
            return "admin/itemForm";
        }
        try {
            // 테이블에 저장 하기 위해  service의 메서드를 호출한다.
            itemAdminService.saveItem(itemForm, multipartFileList);
        }catch(Exception e){
            System.out.println("이미지 업로드 실패했다!! 코드 확인하거나, 경로 올바른지 확인");
            model.addAttribute("errorMessage","상품 등록 중 에러가 발생");
            return "admin/itemForm";
        }

        return "redirect:/";
    }


    // 상품 관리 페이지 요청
    @GetMapping(value={"/items" , "/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto,
                             @PathVariable("page") Optional<Integer> page,
                             Model model ){
        Pageable pageable = PageRequest.of( page.isPresent() ? page.get() : 0 , 10);

        Page<ItemListDto> itemListDtos = itemAdminService.getItemMng(itemSearchDto, pageable);

        model.addAttribute("items",itemListDtos);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage",5);

        return "admin/itemMng";
    }


}



