package com.itheima.laosiji.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.itheima.laosiji.R;

/**
 * Created by Administrator on 2016/9/23.
 */
public class HelpActivity extends Activity implements View.OnClickListener {
    private ImageButton back;
    private TextView how_tuikuan;
    private TextView how_contact;
    private TextView how_back;
    private TextView way1;
    private TextView way2;
    private TextView way3;
    private boolean isway1show = false;
    private boolean isway2show = false;
    private boolean isway3show = false;
    private int preferid = -1;
    private TextView call;
    private TextView zhinan;
    private TextView peisong;
    private TextView shouhou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        intiview();
        intidata();
    }

    private void intidata() {

    }

    private void intiview() {
        back = (ImageButton) findViewById(R.id.ib_back);
        how_tuikuan = (TextView) findViewById(R.id.tv_how_tuikuan);
        how_contact = (TextView) findViewById(R.id.tv_how_contact);
        how_back = (TextView) findViewById(R.id.tv_how_back);
        zhinan = (TextView) findViewById(R.id.tv_zhinan);
        peisong = (TextView) findViewById(R.id.tv_peisong);
        shouhou = (TextView) findViewById(R.id.tv_shouhou);

        way1 = (TextView) findViewById(R.id.tv_way1);
        way2 = (TextView) findViewById(R.id.tv_way2);
        way3 = (TextView) findViewById(R.id.tv_way3);
        call = (TextView) findViewById(R.id.tv_call);
        back.setOnClickListener(this);
        how_tuikuan.setOnClickListener(this);
        how_contact.setOnClickListener(this);
        how_back.setOnClickListener(this);
        call.setOnClickListener(this);
        zhinan.setOnClickListener(this);
        peisong.setOnClickListener(this);
        shouhou.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                onBackPressed();
                break;
            case R.id.tv_how_tuikuan:
                showway(R.id.tv_how_tuikuan);
                break;
            case R.id.tv_how_contact:
                showway(R.id.tv_how_contact);
                break;
            case R.id.tv_how_back:
                showway(R.id.tv_how_back);
                break;
            case R.id.tv_call:
                callshoper();
                break;
            case R.id.tv_zhinan:
                Intent intent1 = new Intent(getApplicationContext(),Help_zhinan_activity.class);
                startActivity(intent1);
                break;
            case R.id.tv_peisong:
                Intent intent2 = new Intent(getApplicationContext(),Help_peisong_activity.class);
                startActivity(intent2);
                break;
            case R.id.tv_shouhou:
                Intent intent3 = new Intent(getApplicationContext(),Help_shouhou_activity.class);
                startActivity(intent3);
                break;
        }
    }

    private void callshoper() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + "40080088888"));
        startActivity(intent);
    }

    private void showway(int id) {
        switch (id) {
            case R.id.tv_how_tuikuan:
                if (preferid == R.id.tv_how_tuikuan) {
                    if (isway1show) {
                        way1.setVisibility(View.GONE);
                    } else {
                        way1.setVisibility(View.VISIBLE);
                    }
                    isway1show = !isway1show;
                } else {
                    closepreway();
                    way1.setVisibility(View.VISIBLE);
                    isway1show = true;
                }
                preferid = R.id.tv_how_tuikuan;
                break;
            case R.id.tv_how_contact:
                if (preferid == R.id.tv_how_contact) {
                    if (isway2show) {
                        way2.setVisibility(View.GONE);
                    } else {
                        way2.setVisibility(View.VISIBLE);
                    }
                    isway2show = !isway2show;
                } else {
                    closepreway();
                    way2.setVisibility(View.VISIBLE);
                   isway2show = true;
                }
                preferid = R.id.tv_how_contact;
                break;
            case R.id.tv_how_back:
                if (preferid == R.id.tv_how_back) {
                    if (isway3show) {
                        way3.setVisibility(View.GONE);
                    } else {
                        way3.setVisibility(View.VISIBLE);
                    }
                    isway3show = !isway3show;
                } else {
                    closepreway();
                    way3.setVisibility(View.VISIBLE);
                    isway3show = true;
                }
                preferid = R.id.tv_how_back;
                break;
        }


    }

    private void closepreway() {
        switch (preferid) {
            case R.id.tv_how_tuikuan:
                way1.setVisibility(View.GONE);
                break;
            case R.id.tv_how_contact:
                way2.setVisibility(View.GONE);
                break;
            case R.id.tv_how_back:
                way3.setVisibility(View.GONE);
                break;
        }
    }

}
