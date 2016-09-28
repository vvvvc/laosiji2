package com.itheima.laosiji.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.laosiji.R;

public class FaPiaoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView jisuancenter;
    private ImageView iv_geren;
    private ImageView iv_danwei;
    private ImageView iv_book;
    private ImageView iv_cloth;
    private ImageView iv_haocai;
    private ImageView iv_software;
    private ImageView iv_material;
    private TextView tv_sure;
    private EditText et_danwei;
    private int prefercontent = R.id.iv_book;
    private  String title = "个人";
    private String contant = "图书";
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa_piao);
        intiview();
        intidata();
    }

    private void intidata() {
        sp = getSharedPreferences("fapiao",MODE_PRIVATE);
        int fapiaotitle = sp.getInt("fapiaotitle", R.id.iv_geren);
        int fapiaocontant = sp.getInt("fapiaocontant", R.id.iv_book);
        if (fapiaocontant != R.id.iv_book){
            hideprefercontent(R.id.iv_book);
        }
        prefercontent = fapiaocontant;
        showtitle(fapiaotitle);
        showcontact(fapiaocontant);
    }

    private void intiview() {
        jisuancenter = (TextView) findViewById(R.id.tv_paycenter);
        iv_geren = (ImageView) findViewById(R.id.iv_geren);
        iv_danwei = (ImageView) findViewById(R.id.iv_danwei);
        iv_book = (ImageView) findViewById(R.id.iv_book);
        iv_cloth = (ImageView) findViewById(R.id.iv_cloth);
        iv_haocai = (ImageView) findViewById(R.id.iv_haocai);
        iv_software = (ImageView) findViewById(R.id.iv_software);
        iv_material = (ImageView) findViewById(R.id.iv_material);
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        et_danwei = (EditText) findViewById(R.id.et_danwei);
        jisuancenter.setOnClickListener(this);
        iv_geren.setOnClickListener(this);
        iv_danwei.setOnClickListener(this);
        iv_book.setOnClickListener(this);
        iv_cloth.setOnClickListener(this);
        iv_haocai.setOnClickListener(this);
        iv_software.setOnClickListener(this);
        iv_material.setOnClickListener(this);
        tv_sure.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_paycenter:
                break;
            case R.id.iv_geren:
                showtitle(R.id.iv_geren);
                break;
            case R.id.iv_danwei:
                showtitle(R.id.iv_danwei);
                break;
            case R.id.iv_book:
                showcontact(R.id.iv_book);
                break;
            case R.id.iv_cloth:
                showcontact(R.id.iv_cloth);
                break;
            case R.id.iv_haocai:
                showcontact(R.id.iv_haocai);
                break;
            case R.id.iv_software:
                showcontact(R.id.iv_software);
                break;
            case R.id.iv_material:
                showcontact(R.id.iv_material);
                break;
            case R.id.tv_sure:
                String name = et_danwei.getText().toString().trim();

                if (name==null||name.length()==0){
                    name="无";
                }

                Intent intent = new Intent();
                String s1 = title + "|" + contant+"|"+name;
                intent.putExtra("fapiao",s1);
                setResult(400,intent);
                finish();
                break;
        }
    }

    private void showcontact(int id) {
        switch (id) {
            case R.id.iv_book:
                hideprefercontent(prefercontent);
                iv_book.setImageResource(R.drawable.isclick);
                prefercontent = R.id.iv_book;
                contant = "图书";
                sp.edit().putInt("fapiaocontant",R.id.iv_book).commit();
                break;
            case R.id.iv_cloth:
                hideprefercontent(prefercontent);
                iv_cloth.setImageResource(R.drawable.isclick);
                prefercontent = R.id.iv_cloth;
                contant = "服装";
                sp.edit().putInt("fapiaocontant",R.id.iv_cloth).commit();
                break;
            case R.id.iv_haocai:
                hideprefercontent(prefercontent);
                iv_haocai.setImageResource(R.drawable.isclick);
                prefercontent = R.id.iv_haocai;
                contant = "耗材";
                sp.edit().putInt("fapiaocontant",R.id.iv_haocai).commit();
                break;
            case R.id.iv_software:
                hideprefercontent(prefercontent);
                iv_software.setImageResource(R.drawable.isclick);
                prefercontent = R.id.iv_software;
                sp.edit().putInt("fapiaocontant",R.id.iv_software).commit();
                contant = "软件";
                break;
            case R.id.iv_material:
                hideprefercontent(prefercontent);
                iv_material.setImageResource(R.drawable.isclick);
                prefercontent = R.id.iv_material;
                contant = "资料";
                sp.edit().putInt("fapiaocontant",R.id.iv_material).commit();
                break;
        }
    }

    private void hideprefercontent(int id) {
        switch (id) {
            case R.id.iv_book:
                iv_book.setImageResource(R.drawable.noclick);
                break;
            case R.id.iv_cloth:
                iv_cloth.setImageResource(R.drawable.noclick);
                break;
            case R.id.iv_haocai:
                iv_haocai.setImageResource(R.drawable.noclick);
                break;
            case R.id.iv_software:
                iv_software.setImageResource(R.drawable.noclick);
                break;
            case R.id.iv_material:
                iv_material.setImageResource(R.drawable.noclick);
                break;
        }
    }

    private void showtitle(int id) {
        switch (id) {
            case R.id.iv_geren:
                iv_danwei.setImageResource(R.drawable.noclick);
                iv_geren.setImageResource(R.drawable.isclick);
                title = "个人";
                et_danwei.setVisibility(View.GONE);
                sp.edit().putInt("fapiaotitle",R.id.iv_geren).commit();
                break;
            case R.id.iv_danwei:
                iv_danwei.setImageResource(R.drawable.isclick);
                iv_geren.setImageResource(R.drawable.noclick);
                et_danwei.setVisibility(View.VISIBLE);
                title = "单位";
                sp.edit().putInt("fapiaotitle",R.id.iv_danwei).commit();
                break;
        }
    }
}
