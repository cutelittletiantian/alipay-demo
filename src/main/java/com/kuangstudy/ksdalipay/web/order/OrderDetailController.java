package com.kuangstudy.ksdalipay.web.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuangstudy.ksdalipay.entity.OrderDetail;
import com.kuangstudy.ksdalipay.service.order.OrderDetailService;
import com.kuangstudy.ksdalipay.vo.PayVo;
import com.kuangstudy.ksdalipay.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: xuke
 * @time: 2021/4/2 23:59
 */
@RestController
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/api/order/detail/callback")
    public R listenerCallback(@RequestBody PayVo payVo){
        QueryWrapper<OrderDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid","1");
        queryWrapper.eq("courseid",payVo.getCourseid());
        int count = orderDetailService.count(queryWrapper);
        return count > 0 ?R.ok():R.error();
    }
}
