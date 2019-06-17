package org.minbox.framework.api.boot.plugin.apibootpluginweipay.config;

import java.math.BigDecimal;

/**
 * 微信参数
 *
 * @author：神绮H亚里亚 - 罗玉波
 * <p>
 * DateTime：2019-06-14 14:13
 * Blog：https://blog.csdn.net/qq_25861361
 */
public class WxpayParam {

    /** 微信支付的金额是String类型 并且是以分为单位
     * */
    BigDecimal totalPrice  ; 

    private String body ;


    public WxpayParam(String body){
        this.body=body;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTotalFee() {
        return totalPrice.multiply(new BigDecimal(100)).toBigInteger().toString();
    }

}
