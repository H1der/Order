package com.hider.order.converter;

import com.hider.order.dataobject.OrderMaster;
import com.hider.order.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDTOConverter {
    public static OrderDTO converter(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();

        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> converter(List<OrderMaster> orderMasterList) {
        return orderMasterList.stream().map(e -> converter(e)).collect(Collectors.toList());
    }

}
