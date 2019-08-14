package com.hider.order.service.impl;

import com.hider.order.dataobject.SellerInfo;
import com.hider.order.repository.SellerInfoRepository;
import com.hider.order.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
