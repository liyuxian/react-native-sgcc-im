package com.sgcc.im;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sgcc.im.util.CommomUtil;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECInitParams;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.OnChatReceiveListener;
import com.yuntongxun.ecsdk.im.ECMessageNotify;
import com.yuntongxun.ecsdk.im.group.ECGroupNoticeMessage;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnChatReceiveListener, ECDevice.OnECDeviceConnectListener {
    private EditText etAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initIm();

        findViewById(R.id.bt_login).setOnClickListener(this);
         etAccount = findViewById(R.id.et_account);
    }

    private void initIm() {
        //判断SDK是否已经初始化
        if (!ECDevice.isInitialized()) {
        /**
         initial: ECSDK 初始化接口
         * 参数：
         *     inContext - Android应用上下文对象
         *     inListener - SDK初始化结果回调接口，ECDevice.InitListener
         *
         * 说明：示例在应用程序创建时初始化 SDK引用的是Application的上下文，
         *       开发者可根据开发需要调整。
         */
            ECDevice.initial(getApplicationContext(), new ECDevice.InitListener() {
                @Override
                public void onInitialized() {
                    // SDK已经初始化成功
                    Log.i("", "初始化SDK成功");
                    //设置登录参数，可分为自定义方式和VoIP验密方式
                    //设置通知回调监听包含登录状态监听，接收消息监听，VoIP呼叫事件回调监听和设置接收VoIP来电事件通知Intent等
                    //验证参数是否正确，登陆SDK
                    initImListener();
                }

                @Override
                public void onError(Exception exception) {
                     //在初始化错误的方法中打印错误原因
                    Log.i("", "初始化SDK失败" + exception.getMessage());
                }
            });
        }
        // 已经初始化成功，后续开发业务代码。
        Log.i("imInit", "初始化SDK及登陆代码完成");
    }

    private void initImListener() {
        ECDevice.setOnChatReceiveListener(this);
        ECDevice.setOnDeviceConnectListener(this);
    }

    private void initLoginSDK(String account) {
        //创建登录参数对象
        ECInitParams params = ECInitParams.createParams();
        //设置用户登录账号
        params.setUserid(account);
        //设置AppId
        params.setAppKey("ff8080815dbc080c015dbc9d7cd4000d");
        //设置AppToken
        params.setToken("7f4fa6d320ab49739183af1d498adb6b");
        //设置登陆验证模式：自定义登录方式
        params.setAuthType(ECInitParams.LoginAuthType.NORMAL_AUTH);
        //LoginMode（强制上线：FORCE_LOGIN  默认登录：AUTO。使用方式详见注意事项）
        params.setMode(ECInitParams.LoginMode.FORCE_LOGIN);
         //验证参数是否正确
       if(!params.validate()) {
            Toast.makeText(this,"参数无效",Toast.LENGTH_LONG).show();
       }
        // 登录函数
        ECDevice.login(params);
    }


    @Override
    public void onClick(View v) {
        String account = etAccount.getText().toString().trim();
        if (TextUtils.isEmpty(account)){
            Toast.makeText(this,"账号不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        initServer();
        initLoginSDK(account);
    }

    private void initServer() {
        String serverStr = CommomUtil.setUpXml("47.97.231.84","8085",
                "47.97.231.84","8085","47.97.231.84","8085");
        ECDevice.initServer(this,serverStr);
    }

    @Override
    public void OnReceivedMessage(ECMessage ecMessage) {
        Log.d("ECDevice","OnReceivedMessage");
    }

    @Override
    public void onReceiveMessageNotify(ECMessageNotify ecMessageNotify) {
        Log.d("ECDevice","onReceiveMessageNotify");
    }

    @Override
    public void OnReceiveGroupNoticeMessage(ECGroupNoticeMessage ecGroupNoticeMessage) {
        Log.d("ECDevice","OnReceiveGroupNoticeMessage");
    }

    @Override
    public void onOfflineMessageCount(int i) {
        Log.d("ECDevice","onOfflineMessageCount");
    }

    @Override
    public int onGetOfflineMessage() {
        Log.d("ECDevice","onGetOfflineMessage");
        return 0;
    }

    @Override
    public void onReceiveOfflineMessage(List<ECMessage> list) {
        Log.d("ECDevice","onReceiveOfflineMessage");
    }

    @Override
    public void onReceiveOfflineMessageCompletion() {
        Log.d("ECDevice","onReceiveOfflineMessageCompletion");
    }

    @Override
    public void onServicePersonVersion(int i) {
        Log.d("ECDevice","onServicePersonVersion");
    }

    @Override
    public void onReceiveDeskMessage(ECMessage ecMessage) {
        Log.d("ECDevice","onReceiveDeskMessage");
    }

    @Override
    public void onSoftVersion(String s, int i) {
        Log.d("ECDevice","onSoftVersion");
    }

    @Override
    public void onConnect() {
        Log.d("ECDevice","onConnect");
    }

    @Override
    public void onDisconnect(ECError ecError) {
        Log.d("ECDevice","onDisconnect");
    }

    @Override
    public void onConnectState(ECDevice.ECConnectState ecConnectState, ECError ecError) {
        Log.d("ECDevice","onConnectState");
        if (200 ==ecError.errorCode){
            Toast.makeText(this,"登录成功",Toast.LENGTH_LONG).show();
            Log.d("ECDevice","登录成功");
            startActivity(new Intent(this,SendMsgActivity.class));
            finish();
        }else{
            Toast.makeText(this,"登录失败",Toast.LENGTH_LONG).show();
        }
    }
}
