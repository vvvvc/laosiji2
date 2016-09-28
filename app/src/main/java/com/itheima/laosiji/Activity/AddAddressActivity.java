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

import com.itheima.laosiji.Bean.AddressBean;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.R;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;

public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mAddress_area;
    private EditText mDistrintNum;
    private EditText mAddress_name;
    private EditText mAddress_street;
    private EditText mAddress_telnum;
    private Button mAddress_Commit;
    private String baseUrl = Constant.baseUrl;
    private String mProvince;
    private String mCity;
    private String mDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initView();

    }

    private void initView() {
        Intent intent = getIntent();
        mProvince = intent.getStringExtra("province");
        mCity = intent.getStringExtra("city");
        mDistrict = intent.getStringExtra("district");
        Toast.makeText(this, mProvince + mCity + mDistrict, Toast.LENGTH_SHORT).show();
        //获取新建地址的页面控件
        mAddress_area = (TextView) findViewById(R.id.address_area);
        mDistrintNum = (EditText) findViewById(R.id.address_district_num);
        mAddress_name = (EditText) findViewById(R.id.address_name);
        mAddress_street = (EditText) findViewById(R.id.address_street);
        mAddress_telnum = (EditText) findViewById(R.id.address_telnum);

        mAddress_area.setText(mProvince + mCity + mDistrict);
        //拿到向服务器commit的button，设置点击事件
        mAddress_Commit = (Button) findViewById(R.id.address_commit);
        mAddress_Commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_commit:
                final String address = mAddress_area.getText().toString().trim();
                String street = mAddress_street.getText().toString().trim();
                String districtNum = mDistrintNum.getText().toString().trim();
                String name = mAddress_name.getText().toString().trim();
                String telNum = mAddress_telnum.getText().toString().trim();
                if (TextUtils.isEmpty(address) || TextUtils.isEmpty(street) || TextUtils.isEmpty(districtNum) || TextUtils.isEmpty(name) || TextUtils.isEmpty(telNum)) {
                    Toast.makeText(this, "数据不能为空！！！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, address + " " + street + " " + districtNum + " " + name + " " + telNum, Toast.LENGTH_SHORT).show();
                Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
                Result result = retrofit.create(Result.class);
                Call<AddressBean> call = result.addAdress(name, telNum, mProvince.trim(), mCity.trim(), mDistrict.trim(), street, "643109", "0");
                call.enqueue(new Callback<AddressBean>() {
                    @Override
                    public void onResponse(Response<AddressBean> response, Retrofit retrofit) {

                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });
                startActivity(new Intent(AddAddressActivity.this,AddressActivity.class));
                break;
        }
    }

    public interface Result {
        @Headers("userid:20428")
        @FormUrlEncoded
        @POST("addresssave")
        Call<AddressBean> addAdress(@Field("name") String name,
                                    @Field("phoneNumber") String phoneNumber,
                                    @Field("province") String province,
                                    @Field("city") String city,
                                    @Field("addressArea") String addressArea,
                                    @Field("addressDetail") String addressDetail,
                                    @Field("zipCode") String zipCode,
                                    @Field("isDefault") String isDefault);
    }
}
