package org.minbox.framework.api.boot.plugin.apibootpluginweipay;


import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.AllArgsConstructor;
import org.minbox.framework.api.boot.plugin.apibootpluginweipay.config.OurWxPayConfig;
import org.minbox.framework.api.boot.plugin.apibootpluginweipay.config.WxpayParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * ApiBoot 微信支付功能
 * 通过该注入该类实现微信支付
 *
 * @author：神绮H亚里亚 - 罗玉波
 * <p>
 * DateTime：2019-06-14 14:13
 * Blog：https://blog.csdn.net/qq_25861361
 */
public class ApiBootPluginWeipayService {

    @Autowired
    private OurWxPayConfig mOurWxPayConfig;


    /**
     * @param ordersId 商户订单号
     * @nonce_str 随机字符串，可空，默认为当前毫秒时间戳
     * @notifyUrl 微信回调地址
     * @total_fee 订单金额
     * */
    public String WeiPayPayment(String ordersId,String nonce_str, String notifyUrl, BigDecimal total_fee) throws Exception{
        WxpayParam mWxpayParam = new  WxpayParam(mOurWxPayConfig.getBody());
        if(notifyUrl==null||notifyUrl.trim().length()==0) {
            notifyUrl = System.currentTimeMillis() + "";
        }
        WXPay wxPay = new WXPay(mOurWxPayConfig);
        Map<String,String> data = new HashMap<>();
        data.put("appid",mOurWxPayConfig.getAppID());
        data.put("mch_id",mOurWxPayConfig.getMchID());
        data.put("trade_type",mOurWxPayConfig.toString());
        data.put("notify_url",notifyUrl);
        data.put("spbill_create_ip",mOurWxPayConfig.getSpbillCreateIp());
        data.put("total_fee",mWxpayParam.getTotalFee());
        data.put("fee_type","CNY");
        data.put("out_trade_no",ordersId);
        data.put("body",mWxpayParam.getBody());
        data.put("nonce_str",nonce_str);
        String s = WXPayUtil.generateSignature(data, mOurWxPayConfig.getKey());
        data.put("sign",s);

        /** wxPay.unifiedOrder 这个方法中调用微信统一下单接口 */
        Map<String, String> respData = wxPay.unifiedOrder(data);
        if (respData.get("return_code").equals("SUCCESS")){
            //返回给APP端的参数，APP端再调起支付接口
            Map<String,String> repData = new HashMap<>();
            repData.put("appid",mOurWxPayConfig.getAppID());
            repData.put("partnerid",mOurWxPayConfig.getMchID());
            repData.put("prepayid",respData.get("prepay_id"));
            repData.put("noncestr",nonce_str);
            repData.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
            repData.put("package","Sign=WXPay");
            repData.put("sign",WXPayUtil.generateSignature(repData,mOurWxPayConfig.getKey()));
            return repData.toString();
        }else{
            throw new Exception("微信支付失败："+respData.get("return_msg"));
        }

    }
    /**
     * @param ordersId 商户订单号
     * @notifyUrl 微信回调地址
     * @total_fee 订单金额
     * */
    public String WeiPayPayment(String ordersId, String notifyUrl, BigDecimal total_fee) throws Exception{
        return  WeiPayPayment(ordersId,null,notifyUrl,total_fee);
    }

}
