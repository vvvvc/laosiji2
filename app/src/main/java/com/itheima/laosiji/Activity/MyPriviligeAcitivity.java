package com.itheima.laosiji.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.laosiji.R;

public class MyPriviligeAcitivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_christmas;
    private ImageView iv_october;
    private ImageView iv_september;
    private boolean isseptember = false;
    private boolean isoctober = false;
    private boolean ischaristmas = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection_acitivity);
        intiviwe();
    }

    private void intiviwe() {
        TextView tv_back = (TextView) findViewById(R.id.tv_back);
        iv_september = (ImageView) findViewById(R.id.iv_september);
        iv_october = (ImageView) findViewById(R.id.iv_october);
        iv_christmas = (ImageView) findViewById(R.id.iv_christmas);
        TextView tv_sure = (TextView) findViewById(R.id.tv_sure);
        tv_back.setOnClickListener(this);
        iv_september.setOnClickListener(this);
        iv_october.setOnClickListener(this);
        iv_christmas.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                onBackPressed();
                break;
            case R.id.iv_september:
                if (isseptember) {
                    iv_september.setImageResource(R.drawable.noclick);
                }else {
                    iv_september.setImageResource(R.drawable.isclick);
                }
                isseptember = !isseptember;
                break;
            case R.id.iv_october:
                if (isoctober) {
                    iv_october.setImageResource(R.drawable.noclick);
                }else {
                    iv_october.setImageResource(R.drawable.isclick);
                }
                isoctober = !isoctober;
                break;
            case R.id.iv_christmas:
                if (ischaristmas) {
                    iv_christmas.setImageResource(R.drawable.noclick);
                }else {
                    iv_christmas.setImageResource(R.drawable.isclick);
                }
                ischaristmas = !ischaristmas;
                break;
            case R.id.tv_sure:
                finish();
                break;
        }
    }
}
