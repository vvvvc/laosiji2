package com.itheima.laosiji.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.laosiji.Bean.UseCenterBean;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.R;
import com.itheima.laosiji.View.ChosePhotoDiolog;
import com.itheima.laosiji.View.IsOutDiolog;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Headers;

public class ActivityZhanghuCenter extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_name;
    private TextView tv_level;
    private TextView tv_jifen;
    private TextView tv_dingdan_number;
    private TextView tv_favorite;
    private  String baseurl = Constant.baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_zhanghu_center);
        intiview();
        intidate();
    }

    private void intidate() {
        getdata();
    }
    public  void getdata(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
        result result = retrofit.create(result.class);
        Call<UseCenterBean> call = result.getUseInfor();
        call.enqueue(new Callback<UseCenterBean>() {
            @Override
            public void onResponse(Response<UseCenterBean> response, Retrofit retrofit) {
                UseCenterBean bean = response.body();
                UseCenterBean.UserInfoBean userInfoBean = bean.getUserInfo();
                tv_level.setText("会员等级:" + userInfoBean.getLevel());
                tv_jifen.setText("总积分:" + userInfoBean.getBonus());
                tv_dingdan_number.setText("订单数:" + userInfoBean.getOrderCount());
                tv_favorite.setText("收藏夹:" + userInfoBean.getFavoritesCount());
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_privilige:
                Intent intent = new Intent(getApplicationContext(), MyPriviligeAcitivity.class);
                startActivity(intent);
                break;
            case R.id.rl_favorite:
                Intent intent1 = new Intent(getApplicationContext(), MyCollectionActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_out:
                IsOutDiolog isOutDiolog = new IsOutDiolog(ActivityZhanghuCenter.this);
                isOutDiolog.show();
                break;
            case R.id.iv_photo:
                ChosePhotoDiolog chosePhotoDiolog = new ChosePhotoDiolog(ActivityZhanghuCenter.this);
                chosePhotoDiolog.show();
        }
    }

    public interface  result{
        @Headers("userid:20428")
        @GET("userinfo")
        Call<UseCenterBean>getUseInfor();
    }
    private void intiview() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_level = (TextView) findViewById(R.id.tv_level);
        tv_jifen = (TextView) findViewById(R.id.tv_jifen);
        tv_dingdan_number = (TextView) findViewById(R.id.tv_dingdan_number);
        tv_favorite = (TextView) findViewById(R.id.tv_favorite);
        RelativeLayout rl_myprivilige = (RelativeLayout) findViewById(R.id.rl_privilige);
        rl_myprivilige.setOnClickListener(this);
        RelativeLayout rl_favourite = (RelativeLayout) findViewById(R.id.rl_favorite);
        rl_favourite.setOnClickListener(this);
        TextView tv_isout = (TextView) findViewById(R.id.tv_out);
        tv_isout.setOnClickListener(this);
        ImageView iv_phtot = (ImageView) findViewById(R.id.iv_photo);
        iv_phtot.setOnClickListener(this);
    }

}
