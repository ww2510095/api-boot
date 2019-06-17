package org.minbox.framework.api.boot.plugin.apibootpluginweipay.Exception;

/**
 * 效验文件
 *
 * @author：神绮H亚里亚 - 罗玉波
 * <p>
 * DateTime：2019-06-14 14:13
 * Blog：https://blog.csdn.net/qq_25861361
 */
public class Validation {

    public static void NotNull(Object obj,String msg){
        if(obj==null) {
            throw new ApiBootWeiPayException(msg);
        }
        if(obj instanceof String){
            if(((String)obj).trim().length()==0) {
                throw new ApiBootWeiPayException(msg);
            }
        }

    }
}
