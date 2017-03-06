package com.lsh.smsverification;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import cn.smssdk.SMSSDK;

/**
 * Created by "小灰灰"
 * on 3/3/2017 16:51
 * 邮箱：www.adonis_lsh.com
 */

public class SmsUtil {

    // 默认使用中国区号
    private static final String DEFAULT_COUNTRY_ID = "42";


    public static String getCurrentCountryCode(Context context) {
        String mcc = getMCC(context);
        String[] countryArr = null;
        if (!TextUtils.isEmpty(mcc)) {
            countryArr = SMSSDK.getCountryByMCC(mcc);
        }
        if (countryArr == null) {
            Log.w("SMSSDK", "no country found by MCC: " + mcc);
            countryArr = SMSSDK.getCountry(DEFAULT_COUNTRY_ID);
        }
        return countryArr[1];
    }

    private static String getMCC(Context context) {
        TelephonyManager tm = (TelephonyManager)context
                .getSystemService(Context.TELEPHONY_SERVICE);
        // 返回当前手机注册的网络运营商所在国家的MCC+MNC. 如果没注册到网络就为空.
        String networkOperator = tm.getNetworkOperator();
        if (!TextUtils.isEmpty(networkOperator)) {
            return networkOperator;
        }
        // 返回SIM卡运营商所在国家的MCC+MNC. 5位或6位. 如果没有SIM卡返回空
        return tm.getSimOperator();
    }
}
