package org.minbox.framework.api.boot.plugin.apibootpluginweipay.Exception;

/**
 * 错误提示，不查找C语音的堆栈错误，可以提高运行速度
 *
 * @author：神绮H亚里亚 - 罗玉波
 * <p>
 * DateTime：2019-06-14 14:13
 * Blog：https://blog.csdn.net/qq_25861361
 */
public class ApiBootWeiPayException extends RuntimeException{

    public ApiBootWeiPayException(String msg) {
        super(msg);
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
    @Override
    public String toString() {
        return "出现了一个异常："+getMessage();
    }
}
