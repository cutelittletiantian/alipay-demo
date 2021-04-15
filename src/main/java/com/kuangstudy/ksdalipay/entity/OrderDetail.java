package com.kuangstudy.ksdalipay.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.util.Date;
/**
 * @author 徐柯
 * @Title:
 * @Package
 * @Description:
 * @date 2021/4/114:16
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("kss_order_detail")
public class OrderDetail {
    // 主键
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    // 支付课程id
    private String courseid;
    // 支付课程标题
    private String coursetitle;
    // 支付课程封面
    private String courseimg;
    // 支付价格
    private String price;
    // 支付用户
    private String userid;
    // 支付用户昵称
    private String username;
    // 支付流水订单号
    private String ordernumber;
    // 支付交易号
    private String tradeno;
    // 1 alipay 2 weixin
    private String paymethod;
    // 课程创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    // 课程更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}