package com.lsh.smsverification;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

import static android.content.ContentValues.TAG;

/**
 * Created by "小灰灰"
 * on 3/3/2017 17:07
 * 邮箱：www.adonis_lsh.com
 */

public class SmsAPI {

    public static final int SUCCESS_CODE = 1;
    private String mPhoneNum;
    private Send_State mSend_state;
    private String Tag = "SmsAPI";
    private Context mContext;
    private String mCountryCode;
    private String mVerificationCode;
    private Verification_State mVerification_state;

    /**
     * 初始化SDK,ShareSDK是可以多次初始化的,如何在应用中多次调用
     *
     * @param context 这里面最好传入app的Context,这样不容易造成内存泄漏
     * @param appKey
     * @param appSecret
     * @return
     */
    public SmsAPI initSDK(Context context, String appKey, String appSecret) {
        mContext = context;
        SMSSDK.initSDK(context, appKey, appSecret);
        SMSSDK.registerEventHandler(eh);
        mCountryCode = SmsUtil.getCurrentCountryCode(mContext);
        return this;
    }

    /**
     * 如果不调用这个方法,将使用默认的国家码
     * @param countryCode
     * @return
     */
    public SmsAPI setCountryCode(String countryCode) {
        mCountryCode = countryCode;
        return this;
    }

    /**
     * @param phoneNum   要发送验证码的手机号
     * @param send_state 发送验的状态
     */
    public SmsAPI sendVerifyPhoneNum(String phoneNum, Send_State send_state) {
        mPhoneNum = phoneNum;
        mSend_state = send_state;
        Log.e(Tag, mCountryCode);
        SMSSDK.getVerificationCode(mCountryCode, mPhoneNum);
        return this;
    }

    /**
     *
     * @param verificationCode
     * @param verification_state
     * @return
     */
    public SmsAPI sendVerificationCode(String verificationCode, Verification_State verification_state) {
        mVerificationCode = verificationCode;
        mVerification_state = verification_state;
        if (TextUtils.isEmpty(mCountryCode)) {
            Log.e(Tag, "请调用initSDK()初始化手机号");
        } else if (TextUtils.isEmpty(mPhoneNum)) {
            Log.e(Tag, "请先调用sendVerifyPhoneNum(String phoneNum,Send_State send_state)方法,初始化手机号");
        } else {
            SMSSDK.submitVerificationCode(mCountryCode, mPhoneNum, verificationCode);
        }
        return this;
    }

    EventHandler eh = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            Log.e(TAG,"event = "+event+" result = "+result+" data = " +data.toString());
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            SMSHandler.sendMessage(msg);
        }

    };

    Handler SMSHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    Log.e(TAG, "提交验证码成功" + data.toString());
                    if (mVerification_state != null) {
                        mVerification_state.sendVerificationCodeResult(SUCCESS_CODE,"成功");
                    }
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    Log.e(TAG, "获取验证码成功" + data.toString());
                    if (mSend_state != null) {
                        mSend_state.sendPhoneResult(SUCCESS_CODE,"成功");
                    }
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                    Log.e(TAG, "返回支持发送验证码的国家列表" + data.toString());
                }
            } else {
                    int status;
                    try {
                        ((Throwable) data).printStackTrace();
                        Throwable throwable = (Throwable) data;
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");
                        status = object.optInt("status");
                        Log.e(TAG, status + "");
                        if (!TextUtils.isEmpty(des)) {
                            if (mSend_state != null) {
                                mSend_state.sendPhoneResult(status,des);
                            }
                            if (mVerification_state != null) {
                                mVerification_state.sendVerificationCodeResult(status,des);
                            }
                            return;
                        }
                    } catch (Exception e) {
                        SMSLog.getInstance().w(e);
                    }
            }
            super.handleMessage(msg);
        }
    };

    public SmsAPI cancelCall() {
        SMSSDK.unregisterAllEventHandler();
        return this;
    }

    /**
     * 服务器发送验证码的状态
     */
    public interface Send_State {
        void sendPhoneResult(int stateCode,String des);
    }

    public interface Verification_State {
        void sendVerificationCodeResult(int stateCode,String des);
    }

}
