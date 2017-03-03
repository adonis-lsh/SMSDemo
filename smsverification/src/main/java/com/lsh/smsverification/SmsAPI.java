package com.lsh.smsverification;

/**
 * Created by "小灰灰"
 * on 3/3/2017 17:07
 * 邮箱：www.adonis_lsh.com
 */

public class SmsAPI {

    /**
     *
     * @param phoneNum 要发送验证码的手机号
     * @param send_state 发送验的状态
     */
    public  SmsAPI sendVerifyPhoneNum(String phoneNum,Send_State send_state) {
        return this;
    }

    /**
     * @param verificationCode 输入的验证码
     * @param send_state 验证验证码是否正确
     */
    public  SmsAPI sendVerificationCode(String verificationCode,Send_State send_state) {
        return this;
    }


    /**
     * 服务器发送验证码的状态
     */
    public interface Send_State {
        void sendPhoneResult(int stateCode);
        void sendVerificationCodeResult(int stateCode);
    }
}
