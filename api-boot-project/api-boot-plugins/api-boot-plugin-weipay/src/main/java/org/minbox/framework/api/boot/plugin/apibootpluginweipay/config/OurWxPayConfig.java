package org.minbox.framework.api.boot.plugin.apibootpluginweipay.config;

import com.github.wxpay.sdk.WXPayConfig;
import lombok.Getter;
import org.minbox.framework.api.boot.plugin.apibootpluginweipay.Exception.ApiBootWeiPayException;
import org.minbox.framework.api.boot.plugin.apibootpluginweipay.Exception.Validation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * 微信的配置文件
 *
 * @author：神绮H亚里亚 - 罗玉波
 * <p>
 * DateTime：2019-06-14 14:13
 * Blog：https://blog.csdn.net/qq_25861361
 */
@Getter
public class OurWxPayConfig implements WXPayConfig {
    /**
     * appid
     * */
    private String appId;
    /**
     *商户id
     * */
    private String mchId;
    /**
     *key
     * */
    private String key;
    /**
     *客户端地址
     * */
    private String spbillCreateIp;
    /**
     *支付的时候订单上面显示的内容
     * */
    private String body="ApiBoot";

    private InputStream mInputStream=null;

    public OurWxPayConfig(String appId,String mchId,String key,String CertStreamPath, String mWeiPayType,String spbillCreateIp){
        Validation.NotNull(appId,"AppId未配置");
        Validation.NotNull(mchId,"MchID未配置");
        Validation.NotNull(key,"Key未配置");
        if(CertStreamPath!=null&&CertStreamPath.trim().length()!=0){
            try {
                mInputStream = new FileInputStream(new File(CertStreamPath.trim()));
            } catch (FileNotFoundException e) {
                throw new ApiBootWeiPayException("错误：配置了微信证书,但对应位置却没有找到证书");
            }
        }
        this.appId=appId;
        this.mchId=mchId;
        this.key=key;
        if(spbillCreateIp==null||spbillCreateIp.trim().length()==0) {
            try {
                spbillCreateIp = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                throw new ApiBootWeiPayException("系统ip获取失败，请手动配置spbillCreateIp");
            }
        }
        this.spbillCreateIp=spbillCreateIp;
    }
    @Override
    public String getAppID() {
        return appId;
    }

    @Override
    public String getMchID() {
        return mchId;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public InputStream getCertStream() {
        return mInputStream;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 0;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 0;
    }
}
