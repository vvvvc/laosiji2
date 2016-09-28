package com.itheima.laosiji.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.laosiji.R;

public class TransportWayActivity extends AppCompatActivity implements View.OnClickListener {
    int isallDays = 1;
    int istwodays = 2;
    int isworkday = 3;
    int preway = 1;
    private ImageView view_alldays;
    private ImageView view_twodays;
    private ImageView view_workdays;
    String sureway = "工作日,双休日均可";
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_way);
        intiview();
        intidata();
    }

    private void intidata() {
        sp = getSharedPreferences("transport",MODE_PRIVATE);
        int paywayid = sp.getInt("transportway", 1);
        switch (paywayid) {
            case 1:
                closePreWay();
                view_alldays.setImageResource(R.drawable.isclick);
                preway = isallDays;
                sureway = "工作日,双休日均可";
                break;
            case 2:
                closePreWay();
                view_twodays.setImageResource(R.drawable.isclick);
                preway = istwodays;
                sureway = "只双休节假日送货";
                break;
            case 3:
                closePreWay();
                view_workdays.setImageResource(R.drawable.isclick);
                preway = isworkday;
                sureway = "只工作日送货";
                break;
        }
    }

    private void intiview() {
        RelativeLayout alldays = (RelativeLayout) findViewById(R.id.rl_alldays);
        RelativeLayout twodays = (RelativeLayout) findViewById(R.id.rl_twodays);
        RelativeLayout workdays = (RelativeLayout) findViewById(R.id.rl_workdays);
        view_alldays = (ImageView) findViewById(R.id.iv_alldays);
        view_twodays = (ImageView) findViewById(R.id.iv_twodays);
        view_workdays = (ImageView) findViewById(R.id.iv_workdays);
        TextView sure = (TextView) findViewById(R.id.tv_sure);
        TextView paycnter = (TextView) findViewById(R.id.tv_jiesuan_center);
        alldays.setOnClickListener(this);
        twodays.setOnClickListener(this);
        workdays.setOnClickListener(this);
        sure.setOnClickListener(this);
        paycnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_alldays:
                closePreWay();
                view_alldays.setImageResource(R.drawable.isclick);
                preway = isallDays;
                sureway = "工作日,双休日均可";
                sp.edit().putInt("transportway",1).commit();
                break;
            case R.id.rl_twodays:
                closePreWay();
                view_twodays.setImageResource(R.drawable.isclick);
                preway = istwodays;
                sureway = "只双休节假日送货";
                sp.edit().putInt("transportway",2).commit();
                break;
            case R.id.rl_workdays:
                closePreWay();
                view_workdays.setImageResource(R.drawable.isclick);
                preway = isworkday;
                sureway = "只工作日送货";
                sp.edit().putInt("transportway",3).commit();
                break;
            case R.id.tv_sure:
                Intent intent2 = new Intent();
                intent2.putExtra("transportway",sureway);
                setResult(300,intent2);
                finish();
                break;
            case R.id.tv_jiesuan_center:
                Intent intent1 = new Intent(getApplicationContext(), SureOrderformActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void closePreWay() {
        switch (preway) {
            case 1:
                view_alldays.setImageResource(R.drawable.noclick);
                break;
            case 2:
                view_twodays.setImageResource(R.drawable.noclick);
                break;
            case 3:
                view_workdays.setImageResource(R.drawable.noclick);
                break;
        }
    }
}
