package com.kuangstudy.ksdalipay.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.kuangstudy.ksdalipay.config.AlipayConfig;
import com.kuangstudy.ksdalipay.entity.ProductCourse;
import com.kuangstudy.ksdalipay.qrcode.QRCodeUtil;
import com.kuangstudy.ksdalipay.qrcode.QrCodeResponse;
import com.kuangstudy.ksdalipay.qrcode.QrResponse;
import com.kuangstudy.ksdalipay.service.course.ProductCourseService;
import com.kuangstudy.ksdalipay.util.GenerateNum;
import com.kuangstudy.ksdalipay.vo.PayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author 徐柯
 * @Title:
 * @Package
 * @Description:
 * @date 2021/3/2922:18
 */
@Service
public class AlipayServiceImpl implements  AlipayService{


    @Autowired
    private AlipayConfig alipayConfig;
    @Autowired
    private ProductCourseService productCourseService;
    
    /**
     * @return byte[]
     * @Author xuke
     * @Description 阿里支付接口
     * @Date 1:05 2020/9/9
     * @Param [payVo]
     **/
    public byte[] alipay(PayVo payVo){
        try {
            // 业务数据
            // 1：支付的用户
            String userId = payVo.getUserId();
            ProductCourse productCourse = productCourseService.getById(payVo.getCourseid());
            if(productCourse==null)return null;
            // 2: 支付金额
            String money = productCourse.getPrice().toString();
            // 3: 支付的产品
            String title = productCourse.getTitle();
            // 4: 支付的订单编号
            String orderNumber = GenerateNum.generateOrder();
            // 5：支付宝携带的参数在回调中可以通过request获取
            JSONObject json = new JSONObject();
            json.put("userId", userId);
            json.put("orderNumber", orderNumber);
            json.put("money", money);
            json.put("ptype","productcourse");// 支付类型
            json.put("courseId",payVo.getCourseid());
            String params = json.toString();

            // 支付信息的参数
            // 6：设置支付相关的信息
            AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
            model.setOutTradeNo(orderNumber); // 自定义订单号
            model.setTotalAmount(money);// 支付金额
            model.setSubject(title);// 支付的产品名称
            model.setBody(params);// 支付的请求体参数
            model.setTimeoutExpress("30m");// 支付的超时时间
            model.setStoreId(userId+"");// 支付的库存id
            QrCodeResponse qrCodeResponse = qrcodePay(model);


            //7 二维码合成
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            String logopath = "";
            try {
                logopath = ResourceUtils.getFile("classpath:favicon.png").getAbsolutePath();
            }catch (Exception ex){
                logopath = new java.io.File("/www/web/favicon.png").getAbsolutePath();
            }

            BufferedImage buffImg = QRCodeUtil.encode(qrCodeResponse.getQr_code(), logopath, false);//获取二维码
            ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
            ImageIO.write(buffImg, "JPEG", imageOut);
            imageOut.close();
            ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
            return FileCopyUtils.copyToByteArray(input);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * 扫码运行代码
     * 验签通过返回QrResponse
     * 失败打印日志信息
     * 参考地址：https://opendocs.alipay.com/apis/api_1/alipay.trade.app.pay
     *
     * @param model
     * @return
     */
    public QrCodeResponse qrcodePay(AlipayTradePrecreateModel model) {
        // 1: 获取阿里请求客户端
        AlipayClient alipayClient = getAlipayClient();
        // 2: 获取阿里请求对象
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        // 3：设置请求参数的集合，最大长度不限
        request.setBizModel(model);
        // 设置异步回调地址
        request.setNotifyUrl(alipayConfig.getNotify_url());
        // 设置同步回调地址
        request.setReturnUrl(alipayConfig.getReturn_url());
        AlipayTradePrecreateResponse alipayTradePrecreateResponse = null;
        try {
            alipayTradePrecreateResponse = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        QrResponse qrResponse = JSON.parseObject(alipayTradePrecreateResponse.getBody(), QrResponse.class);
        return qrResponse.getAlipay_trade_precreate_response();
    }
    /**
     * 获取AlipayClient对象
     *
     * @return
     */
    private AlipayClient getAlipayClient() {
        AlipayClient alipayClient =
                new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getApp_id(), alipayConfig.getMerchant_private_key(),
                        "JSON", alipayConfig.getCharset(), alipayConfig.getAlipay_public_key(), alipayConfig.getSign_type()); //获得初始化的AlipayClient
        return alipayClient;
    }
}