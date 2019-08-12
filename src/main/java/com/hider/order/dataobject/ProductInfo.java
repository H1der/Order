package com.hider.order.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hider.order.enums.ProductStatusEnum;
import com.hider.order.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 */
@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class ProductInfo {

    @Id
    private String productId;

    //商品名称
    private String productName;
    //单价
    private BigDecimal productPrice;
    //库存
    private Integer productStock;
    //描述
    private String productDescription;
    //小图
    private String productIcon;
    //状态,0正常,1下架
    private Integer productStatus;
    //类目编号
    private Integer categoryType;

    private Date createTime;
    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }
}
