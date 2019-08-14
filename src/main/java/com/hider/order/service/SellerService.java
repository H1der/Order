package com.hider.order.service;

import com.hider.order.dataobject.SellerInfo;

public interface SellerService {

    /**
     * 通过openid查询卖家信息
     *
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}
