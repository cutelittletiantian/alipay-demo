package com.kuangstudy.ksdalipay.service.pay;
import com.alibaba.fastjson.JSONObject;
import com.kuangstudy.ksdalipay.entity.OrderDetail;
import com.kuangstudy.ksdalipay.entity.ProductCourse;
import com.kuangstudy.ksdalipay.mapper.OrderDetailMapper;
import com.kuangstudy.ksdalipay.mapper.ProductCourseMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * @author 徐柯
 * @Title:
 * @Package
 * @Description:
 * @date 2021/3/2221:06
 */
@Component
@Log4j2
public class PayCommonService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ProductCourseMapper productCourseMapper;

    /**
     * 支付课程回调封装
     * @param jsonObject
     * @param userId
     * @param orderNumber
     * @param transaction_id
     * @param paymethod
     */
    public void payproductcourse(JSONObject jsonObject, String userId, String orderNumber, String transaction_id, String paymethod) {
        // 支付的课程
        String courseId = jsonObject.getString("courseId");
        // 支付的金额
        String money = jsonObject.getString("money");
        // 根据产品查询产品信息
        ProductCourse productCourse = productCourseMapper.selectById(courseId);
        if (productCourse == null) {
            log.info("【" + (paymethod.equals("2") ? "微信" : "支付宝") + "】你支付的课程被删除了：{}", courseId);
            return;
        }
        // 保存订单明细表
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setUserid(userId);
        orderDetail.setCourseid(courseId);
        orderDetail.setUsername("飞哥");
        orderDetail.setPaymethod(paymethod);
        orderDetail.setCoursetitle(productCourse.getTitle());
        orderDetail.setCourseimg(productCourse.getImg());
        orderDetail.setOrdernumber(orderNumber);
        orderDetail.setTradeno(transaction_id);
        orderDetail.setPrice(money == null ? "0.01" : money);
        orderDetailMapper.insert(orderDetail);
    }


    public void payuserSvip(JSONObject jsonObject, String userId, String orderNumber, String transaction_id, String paymethod) {

    }

    public void awardPay(JSONObject jsonObject, String userId, String orderNumber, String transaction_id, String paymethod) {

    }
}


