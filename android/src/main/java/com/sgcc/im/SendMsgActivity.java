package com.sgcc.im;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yuntongxun.ecsdk.ECChatManager;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.im.ECTextMessageBody;

public class SendMsgActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etAccount;
    private EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);
        etAccount = findViewById(R.id.et_account);
        etContent = findViewById(R.id.et_content);
        findViewById(R.id.bt_send).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String account = etAccount.getText().toString().trim();
        String content = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(content)) {
            Toast.makeText(this, "消息接受账号或者消息不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            //创建一个待发送的消息ECmessage消息体
            ECMessage msg = ECMessage.createECMessage(ECMessage.Type.TXT);
            //设置消息接收者,如果是发送群组消息，则接收者设置群组ID
            msg.setTo(account);
            //创建一个文本消息体，并添加到消息对象中
            ECTextMessageBody msgBody = new ECTextMessageBody(content);
            //将消息体存放到ECMessage中
            msg.setBody(msgBody);
            //调用SDK发送接口发送消息到服务器
            ECChatManager manager = ECDevice.getECChatManager();
            manager.sendMessage(msg, new ECChatManager.OnSendMessageListener() {
                @Override
                public void onSendMessageComplete(ECError error, ECMessage message) {
                    Log.e("sendMessage", "onSendMessageComplete");
                    Toast.makeText(SendMsgActivity.this,"发送成功",Toast.LENGTH_LONG).show();
                    // 处理消息发送结果
                    if (message == null) {
                        return;
                    }
                    // 将发送的消息更新到本地数据库并刷新UI
                }

                @Override
                public void onProgress(String msgId, int totalByte, int progressByte) {
                    // 处理文件发送上传进度（尽上传文件、图片时候SDK回调该方法）
                    Log.e("sendMessage","onProgress");
                }

            });
        } catch (Exception e) {
            // 处理发送异常
            Log.e("sendMessage", "send message fail , e=" + e.getMessage());
        }
    }
}