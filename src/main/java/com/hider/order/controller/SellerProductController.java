package com.hider.order.controller;

import com.hider.order.dataobject.ProductCategory;
import com.hider.order.dataobject.ProductInfo;
import com.hider.order.exception.SellException;
import com.hider.order.form.ProductForm;
import com.hider.order.service.CategoryService;
import com.hider.order.service.ProductService;
import com.hider.order.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 卖家端商品
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

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

    @GetMapping("/index")
    public String index(@RequestParam(value = "productId", required = false) String productId, Model model) {
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productService.findById(productId);
            model.addAttribute("productInfo", productInfo);
        }
        //查询所有类目
        List<ProductCategory> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        return "product/index";
    }

    /**
     * 保存/更新
     *
     * @param form
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/save")
    public String save(@Valid ProductForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("msg", bindingResult.getFieldError().getDefaultMessage());
            model.addAttribute("url", "/order/seller/product/index");
            return "common/error";
        }
        ProductInfo productInfo = new ProductInfo();
        try {
            //如果getProductId为空,说明是新增
            if (!StringUtils.isEmpty(form.getProductId())) {
                productInfo = productService.findById(form.getProductId());
            } else {
                form.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(form, productInfo);
            productService.save(productInfo);
        } catch (SellException e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("url", "/order/seller/product/index");
            return "common/error";
        }
        model.addAttribute("url", "/order/seller/product/list");
        return "common/success";
    }
}
