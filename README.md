# 只需三行代码就能实现短信的验证(ShareSDK的简单封装)
## 先上图:
![](http://i.imgur.com/yyf5OY8.png)
## 使用方法
-  在项目中的build.gradle下面加上


```java
		 compile 'com.lsh.smsverification:smsverification:1.0.0'
```

- 初始化短信

```java 
		 new SmsAPI().initSDK(getApplicationContext(), appkey, appSecret)
```

-  发送短信的时候调用:


```java
		 sendVerifyPhoneNum(mPhoneNumber, new SmsAPI.Send_State() {
                            @Override
                            public void sendPhoneResult(int stateCode, String des) {
                                if (stateCode == SmsAPI.SUCCESS_CODE) {
                                    Log.e(stateCode + "", des);
                                }
                            }
                        });
```	


-  发送验证码调用


```java
		 sendVerificationCode(mVerCode, new SmsAPI.Verification_State() {
            @Override
            public void sendVerificationCodeResult(int stateCode, String des) {
                if (stateCode == SmsAPI.SUCCESS_CODE) {
                    Log.e(stateCode + "", des);
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                }
            }
        })
```

- 最后记得取消调用

```java
		    @Override
    		protected void onDestroy() {
        			super.onDestroy();
        		mSmsAPI.cancelCall();
    		}
```

### 对于短信的智能验证后面会陆续加上去,对于常见错误码请看ShareSdk,也可以看demo中的
![](http://i.imgur.com/oJO9h5t.png)
![](http://i.imgur.com/VSFrvVF.png)
