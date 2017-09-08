package com.wzw.arithmetic;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.voicedemo.activity.ActivityRecog;
import com.baidu.android.voicedemo.activity.setting.OnlineSetting;
import com.baidu.android.voicedemo.recognization.CommonRecogParams;
import com.baidu.android.voicedemo.recognization.RecogResult;
import com.baidu.android.voicedemo.recognization.online.OnlineRecogParams;

import java.util.ArrayList;

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

    TextView tvQuestion;
    TextView tvAnwser;
    TextView tvTempResult;

    HeartFlyView heartFlyView;

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

        tvQuestion = (TextView) findViewById(R.id.tv_question);
        tvAnwser = (TextView) findViewById(R.id.tv_answer);
        tvTempResult = (TextView) findViewById(R.id.tv_temp_result);
        tvTempResult.setVisibility(View.GONE);

        heartFlyView = (HeartFlyView) findViewById(R.id.heart_fly_view);
        findViewById(R.id.btn_qiang_left)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        start();
                    }
                });
        findViewById(R.id.btn_qiang_right)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        start();
                    }
                });

        findViewById(R.id.btn_question)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arithmeticItem = questionGenerator.generate();
                        tvQuestion.setText(arithmeticItem.toCalculateString());
                    }
                });

        Bundle bundle = getIntent().getExtras();
        int maxValue = bundle.getInt("max_value");
        ArrayList<Integer> list = (ArrayList<Integer>) bundle.getSerializable("types");
        questionGenerator = new QuestionGenerator(maxValue, list.toArray(new Integer[list.size()]));
    }

    ArithmeticItem arithmeticItem;

    @Override
    protected void start() {
        super.start();
        tvTempResult.setText("");
        tvTempResult.setVisibility(View.GONE);
        txtLog.append(arithmeticItem.toCalculateString() + "\n");
    }

    protected void handleMsg(Message msg) {
        if (msg.what == STATUS_PART_RESULT) {
            String[] result = (String[]) msg.obj;
            if (arithmeticItem != null && result != null && result.length > 0) {
                for (String string : result) {
                    tvTempResult.setVisibility(View.VISIBLE);
                    tvTempResult.setText(string);
                    int value;
                    try {
                        value = Integer.parseInt(string);
                    } catch (Exception e) {
                        value = Integer.MIN_VALUE;
                        e.printStackTrace();
                    }
                    if (value == arithmeticItem.result || string.contains(arithmeticItem.chineseResult)) {
                        heartFlyView.startAnimation();
                        Toast.makeText(this, "答对了", Toast.LENGTH_SHORT).show();
                        // 停止语音
                        stop();
                        break;
                    }
                }
            }
            return;
        }
        if (msg.what == STATUS_FINISHED) {
            stop();
        }
        if (txtLog != null && msg.obj != null) {
            txtLog.append(msg.obj.toString() + "\n");
        }
    }
}
