package com.itheima.laosiji.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.laosiji.R;

public class PayWayActivity extends AppCompatActivity implements View.OnClickListener {
    int iscash = 1;
    int ispos = 2;
    int ispaybao = 3;
    int preway = 1;
    private ImageView view_cash;
    private ImageView view_pos;
    private ImageView view_paybao;
    String sureway = "到付-现金";
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_way);
        intiview();
        intidata();
    }

    private void intidata() {
        sp = getSharedPreferences("zhifuway",MODE_PRIVATE);
        int paywayid = sp.getInt("payway", 1);
        switch (paywayid) {
            case 1:
                closePreWay();
                view_cash.setImageResource(R.drawable.isclick);
                preway = iscash;
                sureway = "到付-现金";
                break;
            case 2:
                closePreWay();
                view_pos.setImageResource(R.drawable.isclick);
                preway = ispos;
                sureway = "到付-pos";
                break;
            case 3:
                closePreWay();
                view_paybao.setImageResource(R.drawable.isclick);
                preway = ispaybao;
                sureway = "支付宝";
                break;
        }
    }

    private void intiview() {
        RelativeLayout cash = (RelativeLayout) findViewById(R.id.rl_cash);
        RelativeLayout pos = (RelativeLayout) findViewById(R.id.rl_pos);
        RelativeLayout paybao = (RelativeLayout) findViewById(R.id.rl_paybao);
        view_cash = (ImageView) findViewById(R.id.iv_cash);
        view_pos = (ImageView) findViewById(R.id.iv_pos);
        view_paybao = (ImageView) findViewById(R.id.iv_paybao);
        TextView sure = (TextView) findViewById(R.id.tv_sure);
        TextView paycnter = (TextView) findViewById(R.id.tv_jiesuan_center);
        cash.setOnClickListener(this);
        pos.setOnClickListener(this);
        paybao.setOnClickListener(this);
        sure.setOnClickListener(this);
        paycnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_cash:
                closePreWay();
                view_cash.setImageResource(R.drawable.isclick);
                preway = iscash;
                sureway = "到付-现金";
                sp.edit().putInt("payway",1).commit();
                break;
            case R.id.rl_pos:
                closePreWay();
                view_pos.setImageResource(R.drawable.isclick);
                preway = ispos;
                sureway = "到付-pos";
                sp.edit().putInt("payway",2).commit();
                break;
            case R.id.rl_paybao:
                closePreWay();
                view_paybao.setImageResource(R.drawable.isclick);
                preway = ispaybao;
                sureway = "支付宝";
                sp.edit().putInt("payway",3).commit();
                break;
            case R.id.tv_sure:
                Intent intent2 = new Intent();
                intent2.putExtra("payway",sureway);
                setResult(200,intent2);
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
                view_cash.setImageResource(R.drawable.noclick);
                break;
            case 2:
                view_pos.setImageResource(R.drawable.noclick);
                break;
            case 3:
                view_paybao.setImageResource(R.drawable.noclick);
                break;
        }
    }
}
