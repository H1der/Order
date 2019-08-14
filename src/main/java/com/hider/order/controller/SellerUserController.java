package com.hider.order.controller;

import com.hider.order.constant.CookieConstant;
import com.hider.order.dataobject.ResultEnum;
import com.hider.order.dataobject.SellerInfo;
import com.hider.order.service.impl.SellerServiceImpl;
import com.hider.order.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerServiceImpl sellerService;

    @GetMapping("/login")
    public String login(@RequestParam("openid") String openid,
                        HttpServletResponse response, Model model) {
        //1.openid与数据库匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null) {
            model.addAttribute("msg", ResultEnum.LOGIN_FAIL);
            model.addAttribute("url", "/order/seller/order/list");
            return "common/error";
        }
        //2.设置session到redis

        //3.设置session到cookie
        String token = UUID.randomUUID().toString();
        Integer expire = CookieConstant.EXPIRE;

        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
        return "redirect:/seller/order/list";
    }

    @GetMapping("/logout")
    public void logout() {

    }

}
