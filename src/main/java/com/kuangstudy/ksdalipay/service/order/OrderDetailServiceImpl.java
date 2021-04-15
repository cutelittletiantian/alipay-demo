package com.kuangstudy.ksdalipay.service.order;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuangstudy.ksdalipay.entity.OrderDetail;
import com.kuangstudy.ksdalipay.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: xuke
 * @time: 2021/4/2 23:16
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
