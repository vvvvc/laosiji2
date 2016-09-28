package com.itheima.laosiji.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.laosiji.Bean.LoginBean;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.Fragment.MyFragment;
import com.itheima.laosiji.R;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public class MyLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mLogin_num;
    private EditText mLogin_pwd;
    private String login_num;
    private String login_pwd;
    private Button mBt_back_main;
    private Button mBt_loging;
    private Call<ResponseBody> call;
    private TextView mBt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_login);
        initView();
    }

    private void initView() {
        //找到控控件
        mLogin_num = (EditText) findViewById(R.id.login_num);
        mLogin_pwd = (EditText) findViewById(R.id.login_pwd);
        mBt_loging = (Button) findViewById(R.id.bt_login);
        mBt_back_main = (Button) findViewById(R.id.bt_back_main);
        mBt_register = (TextView) findViewById(R.id.bt_register);

        mBt_back_main.setOnClickListener(this);
        mBt_register.setOnClickListener(this);
        mBt_loging.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击返回主页面
            case R.id.bt_back_main:
                startActivity(new Intent(MyLoginActivity.this,MyFragment.class));
                break;
            //点击跳到注册页面
            case R.id.bt_register:
               startActivity(new Intent(MyLoginActivity.this,MyRegister.class));
                break;

            //点击登录
            case R.id.bt_login:
                login_num = mLogin_num.getText().toString().trim();
                login_pwd = mLogin_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(login_num) || TextUtils.isEmpty(login_pwd)) {
                    Toast.makeText(getApplicationContext(), "密码账号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    mBt_loging.setText("登陆...");

                    String baseUrl = Constant.baseUrl;
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
                    Result result = retrofit.create(Result.class);
                    Call<LoginBean> call = result.postLogin(login_num, login_pwd);
                    call.enqueue(new Callback<LoginBean>() {
                        @Override
                        public void onResponse(Response<LoginBean> response, Retrofit retrofit) {
                           LoginBean json = response.body();
                            String login_id = json.getUserInfo().getUserid();
                            Toast.makeText(MyLoginActivity.this,login_id,Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                        }
                    });

                    startActivity(new Intent(MyLoginActivity.this,ActivityZhanghuCenter.class));
                    finish();

                }
                break;
        }
    }

    //get接口
    public interface  Result {
        //http://localhost:8080/market/login
        @FormUrlEncoded
        @POST("login")
        Call<LoginBean> postLogin(@Field("username") String username, @Field("password") String password);

    }


}

