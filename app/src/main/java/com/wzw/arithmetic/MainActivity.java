package com.wzw.arithmetic;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Toast;

import com.baidu.android.voicedemo.activity.ActivityRecog;
import com.baidu.android.voicedemo.activity.setting.OnlineSetting;
import com.baidu.android.voicedemo.recognization.CommonRecogParams;
import com.baidu.android.voicedemo.recognization.RecogResult;
import com.baidu.android.voicedemo.recognization.online.OnlineRecogParams;

public class MainActivity extends ActivityRecog {
    {
        DESC_TEXT = "在线普通识别功能(含长语音)\n" +
                "请保持设备联网，对着麦克风说出日常用语即可\n" +
                "普通录音限制60s。使用长语音无此限制， 开启长语音选项时，请选择长句（输入法）模型。\n" +
                "\n" +
                "集成指南：\n" +
                "1. PID 参数需要额外输入，Demo中已经有PidBuilder可以根据参数设置PID \n" +
                "2. ASR_START 不可连续调用，需要引擎空闲或者ASR_CANCEL输入事件后调用。详细请参见文档ASR_START的描述\n" ;
        layout = R.layout.activity_main;
    }

    QuestionGenerator questionGenerator;

    public MainActivity(){
        super();
        settingActivityClass = OnlineSetting.class;
    }

    @Override
    protected CommonRecogParams getApiParams() {
        return new OnlineRecogParams(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.heart_fly_view)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HeartFlyView heartFlyView = (HeartFlyView) v;
                        heartFlyView.startAnimation();
                    }
                });

        int maxValue = 100;
        int[] types = {0, 1, 2, 3};
        questionGenerator = new QuestionGenerator(maxValue, types);
    }

    ArithmeticItem arithmeticItem;

    @Override
    protected void start() {
        super.start();
        arithmeticItem = questionGenerator.generate();
        txtLog.append(arithmeticItem.toCalculateString() + "\n");
    }

    protected void handleMsg(Message msg) {
        if (msg.what == STATUS_PART_RESULT) {
            String[] result = (String[]) msg.obj;
            if (arithmeticItem != null && result != null && result.length > 0) {
                for (String string : result) {
                    try {
                        int value = Integer.parseInt(string);
                        if (value == arithmeticItem.result) {
                            Toast.makeText(this, "答对了", Toast.LENGTH_SHORT).show();
                            // // TODO: 2017/9/8 停止语音
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return;
        }
        if (txtLog != null && msg.obj != null) {
            txtLog.append(msg.obj.toString() + "\n");
        }
    }
}
