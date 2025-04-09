package com.shop.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/signIn")
    public String login( Model model ){

        return "member/signIn";
    }

}
