package com.lsh.smsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lsh.smsverification.SmsAPI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SmsAPI smsAPI = new SmsAPI()
                .initSDK(getApplicationContext(), "1bd484b4fb8c4", "367da09d8416e043792d3271c89af64c")
                .sendVerifyPhoneNum("15901458055", new SmsAPI.Send_State() {
                    @Override
                    public void sendPhoneResult(int stateCode) {
                        //这里面一般做读秒操作
                        Log.e("1111111111", stateCode + "");
                    }
                });
    }
}
