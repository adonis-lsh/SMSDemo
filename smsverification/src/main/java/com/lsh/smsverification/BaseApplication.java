package com.lsh.smsverification;

import android.app.Application;

import cn.smssdk.SMSSDK;

/**
 * Created by "小灰灰"
 * on 3/3/2017 16:48
 * 邮箱：www.adonis_lsh.com
 */

public abstract class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SMSSDK.initSDK(this, "您的appkey", "您的appsecret");
    }
}
