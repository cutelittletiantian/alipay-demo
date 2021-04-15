package com.kuangstudy.ksdalipay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @description:
 * @author: xuke
 * @time: 2020/9/8 23:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PayVo implements java.io.Serializable{
    // 支付用户
    private String userId;
    // 支付产品id
    private String courseid;
}
