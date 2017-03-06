package com.lsh.smsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lsh.smsverification.SmsAPI;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.phoneNum)
    EditText mPhoneNum;
    @Bind(R.id.nextStep)
    Button mNextStep;
    @Bind(R.id.VerificationCode)
    EditText mVerificationCode;
    @Bind(R.id.verification)
    Button mVerification;

    private SmsAPI mSmsAPI;
    private String mPhoneNumber;
    private String mVerCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {

    }

    @OnClick({R.id.nextStep, R.id.verification,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nextStep:
                mPhoneNumber = mPhoneNum.getText().toString();
                mSmsAPI = new SmsAPI()
                        .initSDK(getApplicationContext(), "1bd484b4fb8c4", "367da09d8416e043792d3271c89af64c")
                        .sendVerifyPhoneNum(mPhoneNumber, new SmsAPI.Send_State() {
                            @Override
                            public void sendPhoneResult(int stateCode, String des) {
                                if (stateCode != SmsAPI.SUCCESS_CODE) {
                                    Log.e(stateCode + "", des);
                                    //重新发送手机号
                                }
                            }
                        });
                break;
            case R.id.verification:
                mVerCode = mVerificationCode.getText().toString();
                mSmsAPI.sendVerificationCode(mVerCode, new SmsAPI.Verification_State() {
                    @Override
                    public void sendVerificationCodeResult(int stateCode, String des) {
                        if (stateCode == SmsAPI.SUCCESS_CODE) {
                            Log.e(stateCode + "", des);
                            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        } else {
                            //重新发送验证码
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSmsAPI.cancelCall();
    }
}
