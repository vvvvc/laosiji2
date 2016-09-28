package com.itheima.laosiji.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itheima.laosiji.Bean.RegisterBean;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.R;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public class MyRegister extends AppCompatActivity implements View.OnClickListener {

    private EditText mRegister_num;
    private EditText mRegister_pwd;
    private EditText mRegister_qpwd;
    private Button mBt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_register);

        initView();
    }

    private void initView() {
        mRegister_num = (EditText) findViewById(R.id.register_num);
        mRegister_pwd = (EditText) findViewById(R.id.register_pwd);
        mRegister_qpwd = (EditText) findViewById(R.id.register_qpwd);
        mBt_register = (Button) findViewById(R.id.bt_register);

        mBt_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register:
                String num = mRegister_num.getText().toString().trim();
                String pwd = mRegister_pwd.getText().toString().trim();
                String qpwd = mRegister_qpwd.getText().toString().trim();
               String baseUrl = Constant.baseUrl;
                if (TextUtils.isEmpty(num) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(qpwd)) {
                    Toast.makeText(getApplicationContext(), "密码账号不能为空", Toast.LENGTH_SHORT).show();
                }else if(!pwd.equals(qpwd)) {
                    Toast.makeText(getApplicationContext(), "你输入的密码不一致", Toast.LENGTH_SHORT).show();
                }else{


                    Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
                    Result result = retrofit.create(Result.class);
                    Call<RegisterBean> call = result.postRegister(num, pwd);
                    call.enqueue(new Callback<RegisterBean>() {
                        @Override
                        public void onResponse(Response<RegisterBean> response, Retrofit retrofit) {
                            RegisterBean registerBean = response.body();
                            try {
                                String userid = registerBean.getUserInfo().getUserid();
                                Toast.makeText(MyRegister.this, userid, Toast.LENGTH_SHORT).show();
                                mBt_register.setText("注册成功,为您转接页面");
                                startActivity(new Intent(MyRegister.this,MyLoginActivity.class));
                                finish();
                            } catch (Exception e) {
                                Toast.makeText(MyRegister.this, "账号已注册！", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }

                        @Override
                        public void onFailure(Throwable throwable) {

                        }
                    });


                }
                break;
        }
    }

    public interface  Result{
        //http://localhost:8080/market/register
        @FormUrlEncoded
        @POST("register")
        Call<RegisterBean> postRegister(@Field("username") String username, @Field("password") String password);
    }
}
