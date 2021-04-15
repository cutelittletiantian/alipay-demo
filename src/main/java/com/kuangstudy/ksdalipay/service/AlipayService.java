package com.kuangstudy.ksdalipay.service;

import com.kuangstudy.ksdalipay.vo.PayVo;

/**
 * @author 徐柯
 * @Title:
 * @Package
 * @Description:
 * @date 2021/3/2922:18
 */
public interface AlipayService {
    /**
     * @return byte[]
     * @Author xuke
     * @Description 阿里支付接口
     * @Date 1:05 2020/9/9
     * @Param [payVo]
     **/
    byte[] alipay(PayVo payVo);
}