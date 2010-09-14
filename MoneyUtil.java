package com.hundsun.network.post.settle.web.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.hundsun.itrans.common.util.StringUtils;
import com.hundsun.network.melody.common.util.Money;

/**
 * 处理金额的util类
 *
 * @author daodao
 *
 */
public class MoneyUtil {
    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     *
     * @param intMoney
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(String strMoney) {
        return getFormatMoney(strMoney, null);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     *
     * @param strMoney
     * @param formatStr
     *            格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
     *            ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(String strMoney, String formatStr) {
        return getFormatMoney(strMoney, formatStr, Locale.US);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     *
     * @param strMoney
     * @param formatStr
     *            格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
     *            ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale
     *            使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(String strMoney, String formatStr, Locale locale) {
        Double doubleMoney;
        if (strMoney == null || strMoney.trim().equals("")) {
            strMoney = "0";
        }
        try {
            doubleMoney = Double.valueOf(strMoney);
        } catch (Exception e) {
            return strMoney;
        }
        return getFormatMoney(doubleMoney, formatStr, locale);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     *
     * @param intMoney
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Integer intMoney) {
        return getFormatMoney(intMoney, null);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     *
     * @param intMoney
     * @param formatStr
     *            格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
     *            ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Integer intMoney, String formatStr) {
        return getFormatMoney(intMoney, formatStr, Locale.US);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     *
     * @param intMoney
     * @param formatStr
     *            格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
     *            ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale
     *            使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Integer intMoney, String formatStr, Locale locale) {
        if (intMoney == null) {
            intMoney = Integer.parseInt("0");
        }
        if (null == formatStr || formatStr.trim().equals("")) {
            formatStr = "￥#,##0.00";
        }
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);// 设置使用美国数字格式
        // (
        // 千分位
        // )
        df.applyPattern(formatStr);// 设置应用金额格式
        return df.format(intMoney);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     *
     * @param doubleMoney
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Double doubleMoney) {
        return getFormatMoney(doubleMoney, null);
    }

    /**
     * 转换成金钱的字符串,带格式
     *
     * @param doubleMoney
     * @param formatStr
     *            格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
     *            ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Double doubleMoney, String formatStr) {
        if (doubleMoney == null) {
            doubleMoney = Double.valueOf(0);
        }
        return getFormatMoney(doubleMoney, formatStr, Locale.US);
    }

    /**
     * 转换成金钱的字符串,带格式
     *
     * @param doubleMoney
     * @param formatStr
     *            格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
     *            ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale
     *            使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Double doubleMoney, String formatStr, Locale locale) {
        if (doubleMoney == null) {
            doubleMoney = Double.valueOf(0);
        }
        if (null == formatStr || formatStr.trim().equals("")) {
            formatStr = "￥#,##0.00";
        }
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);// 设置使用美国数字格式
        // (
        // 千分位
        // )
        df.applyPattern(formatStr);// 设置应用金额格式
        return df.format(doubleMoney);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     *
     * @param bigDecimalMoney
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(BigDecimal bigDecimalMoney) {
        return getFormatMoney(bigDecimalMoney, null);
    }

    /**
     * 转换成金钱的字符串,带格式
     *
     * @param bigDecimalMoney
     * @param formatStr
     *            格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
     *            ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale
     *            使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(BigDecimal bigDecimalMoney, String formatStr) {
        return getFormatMoney(bigDecimalMoney, formatStr, Locale.US);
    }

    /**
     * 转换成金钱的字符串,带格式
     *
     * @param bigDecimalMoney
     * @param formatStr
     *            格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
     *            ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale
     *            使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(BigDecimal bigDecimalMoney, String formatStr, Locale locale) {
        if (bigDecimalMoney == null) {
            bigDecimalMoney = BigDecimal.valueOf(0.00);
        }
        if (null == formatStr || formatStr.trim().equals("")) {
            formatStr = "￥#,##0.00";
        }
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);// 设置使用美国数字格式
        // (
        // 千分位
        // )
        df.applyPattern(formatStr);// 设置应用金额格式
        return df.format(bigDecimalMoney);
    }

    /**
     * 获取money类型的字符串
     *
     * @param cent
     * @return
     */
    public static String getMoneyStr(Long cent) {
        Money money = new Money();
        money.setCent(cent);
        return money.toString();
    }

    /**
     * 输入分的字符串，得到元的字符串
     * @param cent
     * @return
     */
    public static String getMoneyStr(String cent) {
        if (cent != null && StringUtils.isNumeric(cent)) {
            Money money = new Money();
            money.setCent(Long.valueOf(cent));
            return money.toString();
        }
        return null;

    }
}
