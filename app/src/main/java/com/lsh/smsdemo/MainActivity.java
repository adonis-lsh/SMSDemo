package com.lsh.smsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lsh.smsverification.SmsAPI;

public class MainActivity extends AppCompatActivity {

    private SmsAPI mSmsAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSmsAPI = new SmsAPI()
                .initSDK(getApplicationContext(), "1bd484b4fb8c4", "367da09d8416e043792d3271c89af64c")
                .sendVerifyPhoneNum("15901458056", new SmsAPI.Send_State() {
                    @Override
                    public void sendPhoneResult(int stateCode, String des) {
                        if (stateCode != SmsAPI.SUCCESS_CODE) {
                            Log.e(stateCode + "", des);
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSmsAPI.cancelCall();
    }
}
