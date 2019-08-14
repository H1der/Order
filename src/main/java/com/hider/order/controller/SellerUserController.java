package com.hider.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seller")
public class SellerUserController {


    @GetMapping("/login")
    public void login() {
        //1.openid与数据库匹配

        //2.设置session到redis

        //3.设置session到cookie

    }

    @GetMapping("/logout")
    public void logout() {

    }

}
