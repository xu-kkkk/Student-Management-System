package com.tiankai.ssm.utils;

/**
 * 需要用到的一些工具类
 *
 * @author: xutiankai
 * @date: 7/31/2021 1:48 PM
 */
public class WebUtils {
    /**
     * 将String转换成int类型并返回。若转换时发生异常，则返回用户传入的默认值。
     *
     * @param parseValue   要转换成int的字符串
     * @param defaultValue 发生异常时返回的默认值
     * @return 转换后的int或默认值
     */
    public static int parseInt(String parseValue, int defaultValue) {
        int returnValue;

        try {
            returnValue = Integer.parseInt(parseValue);
        } catch (NumberFormatException e) {
//            e.printStackTrace();
            returnValue = defaultValue;
        }

        return returnValue;
    }
}
