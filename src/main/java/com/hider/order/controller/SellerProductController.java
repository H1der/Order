package com.hider.order.controller;

import com.hider.order.dataobject.ProductInfo;
import com.hider.order.exception.SellException;
import com.hider.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 卖家端商品
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    /**
     * 列表
     *
     * @param page
     * @param size
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                       Model model) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        model.addAttribute("productInfoPage", productInfoPage);
        model.addAttribute("currentPage", page);
        return "product/list";
    }

    /**
     * 商品上架
     *
     * @param productId
     * @param model
     * @return
     */
    @RequestMapping("/on_sale")
    public String onSale(@RequestParam("productId") String productId, Model model) {
        try {
            productService.onSale(productId);
        } catch (SellException e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("url", "/order/seller/product/list");
            return "common/error";
        }
        model.addAttribute("url", "/order/seller/product/list");
        return "common/success";

    }

    /**
     * 商品下架
     *
     * @param productId
     * @param model
     * @return
     */
    @RequestMapping("/off_sale")
    public String offSale(@RequestParam("productId") String productId, Model model) {
        try {
            productService.offSale(productId);
        } catch (SellException e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("url", "/order/seller/product/list");
            return "common/error";
        }
        model.addAttribute("url", "/order/seller/product/list");
        return "common/success";

    }
}
