package com.hider.order.controller;

import com.hider.order.VO.ProductInfoVO;
import com.hider.order.VO.ProductVO;
import com.hider.order.VO.ResultVO;
import com.hider.order.dataobject.ProductCategory;
import com.hider.order.dataobject.ProductInfo;
import com.hider.order.service.CategoryService;
import com.hider.order.service.ProductService;
import com.hider.order.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list() {
        //1.查询所有上架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2.查询类目(一次性查询)
//        List<Integer> categoryTypeList = new ArrayList<>();
        //从所有上架的商品中取出Type(类目),未上架的商品所属的类目不需要展示
        List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3.数据拼装
        //遍历数据最外层
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            //遍历商品详情
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                //商品的type与类目的type相等时才进行赋值
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            //商品信息添加进类目信息
            productVO.setProductInfoVOList(productInfoVOList);
            //类目信息添加进最外层
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }
}
