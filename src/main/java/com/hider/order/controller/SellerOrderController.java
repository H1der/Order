package com.hider.order.controller;

import com.hider.order.dataobject.ResultEnum;
import com.hider.order.dto.OrderDTO;
import com.hider.order.exception.SellException;
import com.hider.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        model.addAttribute("currentPage", page);
        return "order/list";

    }

    /**
     * 取消订单
     *
     * @param orderId
     * @return
     */
    @GetMapping("/cancel")
    public String cancel(@RequestParam("orderId") String orderId, Model model) {
        try {
            OrderDTO orderDTO = orderService.findById(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("[卖家端订单取消]发生异常 {}", e);
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("url", "/order/seller/order/list");
            return "common/error";
        }
        model.addAttribute("msg", ResultEnum.ORDER_CANNEL_SUCCESS.getMessage());
        model.addAttribute("url", "/order/seller/order/list");
        return "common/success";
    }

    /**
     * 订单详情
     *
     * @param orderId
     * @param model
     * @return
     */
    @GetMapping("/detail")
    public String detail(@RequestParam("orderId") String orderId, Model model) {
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderService.findById(orderId);
        } catch (SellException e) {
            log.error("[卖家端订单详情]发生异常 {}", e);
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("url", "/order/seller/order/list");
            return "common/error";
        }
        model.addAttribute("orderDTO", orderDTO);
        return "order/detail";
    }

    /**
     * 订单完结
     *
     * @param orderId
     * @param model
     * @return
     */
    @GetMapping("/finish")
    public String finished(@RequestParam("orderId") String orderId, Model model) {
        try {
            OrderDTO orderDTO = orderService.findById(orderId);
            orderService.finish(orderDTO);
        } catch (SellException e) {
            log.error("[卖家端订单完结]发生异常 {}", e);
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("url", "/order/seller/order/list");
            return "common/error";
        }
        model.addAttribute("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        model.addAttribute("url", "/order/seller/order/list");
        return "common/success";

    }
}
