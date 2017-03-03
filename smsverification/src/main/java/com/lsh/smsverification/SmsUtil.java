package com.lsh.smsverification;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by "小灰灰"
 * on 3/3/2017 16:51
 * 邮箱：www.adonis_lsh.com
 */

public class SmsUtil {
    public static class Config{
        // 您的appKey
        private String appkey;
        // 您的appsecret
        private String appsecret;

        public Config(String appkey, String appsecret) {
            this.appkey = appkey;
            this.appsecret = appsecret;
        }
    }
    EventHandler eh=new EventHandler(){

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                }
            }else{
                ((Throwable)data).printStackTrace();
            }
        }
    };
}
