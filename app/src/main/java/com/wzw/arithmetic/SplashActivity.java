package com.wzw.arithmetic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    RadioGroup rgCountRange;
    CheckBox cbPlus, cbSub, cbMultiply, cbDivide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 计算范围
        // 次数
        // 发音
        // 四则运算

        rgCountRange = (RadioGroup) findViewById(R.id.rg_count_range);
        cbPlus = (CheckBox) findViewById(R.id.cb_plus);
        cbSub = (CheckBox) findViewById(R.id.cb_sub);
        cbMultiply = (CheckBox) findViewById(R.id.cb_multiply);
        cbDivide = (CheckBox) findViewById(R.id.cb_divide);

        findViewById(R.id.btn_start)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        if (rgCountRange.getCheckedRadioButtonId() == R.id.rb_range_10) {
                            intent.putExtra("max_value", 10);
                        } else {
                            intent.putExtra("max_value", 100);
                        }
                        ArrayList<Integer> list = new ArrayList();
                        if (cbPlus.isChecked()) {
                            list.add(0);
                        }
                        if (cbSub.isChecked()) {
                            list.add(1);
                        }
                        if (cbMultiply.isChecked()) {
                            list.add(2);
                        }
                        if (cbDivide.isChecked()) {
                            list.add(3);
                        }
                        if (list.size() == 0) {
                            Toast.makeText(SplashActivity.this, "至少选择一种运算", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        intent.putExtra("types", list);
                        startActivity(intent);
                    }
                });

    }
}
