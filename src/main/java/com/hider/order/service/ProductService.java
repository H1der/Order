package com.hider.order.service;

import com.hider.order.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品列表
 */
public interface ProductService {
    ProductInfo findById(String productId);

    //查询所有上架的商品列表
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //todo 加库存

    //todo 减库存

}
