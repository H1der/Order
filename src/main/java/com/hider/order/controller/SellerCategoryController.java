package com.hider.order.controller;

import com.hider.order.dataobject.ProductCategory;
import com.hider.order.exception.SellException;
import com.hider.order.form.CategoryForm;
import com.hider.order.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 卖家类目
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * 类目列表
     *
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Model model) {
        List<ProductCategory> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        return "category/list";
    }

    @GetMapping("/index")
    public String index(@RequestParam(value = "categoryId", required = false) Integer categoryId, Model model) {
        if (categoryId != null) {
            ProductCategory productCategory = categoryService.findById(categoryId);
            model.addAttribute("category", productCategory);
        }
        return "category/index";
    }

    /**
     * 保存更新
     *
     * @param form
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/save")
    public String post(@Valid CategoryForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("msg", bindingResult.getFieldError().getDefaultMessage());
            model.addAttribute("url", "/order/seller/category/index");
            return "common/error";
        }
        ProductCategory productCategory = new ProductCategory();
        try {
            if (form.getCategoryId() != null) {
                productCategory = categoryService.findById(form.getCategoryId());
            }
            BeanUtils.copyProperties(form, productCategory);
            categoryService.save(productCategory);
        } catch (SellException e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("url", "/order/seller/category/index");
            return "common/error";
        }
        model.addAttribute("url", "/order/seller/category/list");
        return "common/success";
    }
}
