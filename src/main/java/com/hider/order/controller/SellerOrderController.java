package com.hider.order.controller;

import com.hider.order.dto.OrderDTO;
import com.hider.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     *
     * @param page  第几页,从第一页开始
     * @param size  一页显示多少条数据
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                       Model model) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        model.addAttribute("orderDTOPage", orderDTOPage);
        return "order/list";

    }
}
