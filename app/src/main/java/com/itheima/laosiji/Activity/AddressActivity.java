package com.itheima.laosiji.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.laosiji.Bean.AddressBean;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.R;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Headers;

public class AddressActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUESTCODE = 0;
    private TextView mAddress_Manage;
    private String baseUrl = Constant.baseUrl;
    private List<AddressBean.AddressListBean> mAddressList;
    private ListView mLv;
    private addressAdapter mAdapter;
    private boolean isOrder = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        Intent intent = getIntent();
        String action = intent.getAction();

        if (action.equals("SureOrderformActivity")) {
            isOrder = true;
        }
        initView();
        getServerData();
    }

    private void getServerData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        AddressResult addressResult = retrofit.create(AddressResult.class);
        Call<AddressBean> adress = addressResult.getAdress();
        adress.enqueue(new Callback<AddressBean>() {
            @Override
            public void onResponse(Response<AddressBean> response, Retrofit retrofit) {
                AddressBean body = response.body();
                mAddressList = body.getAddressList();
                mAdapter = new addressAdapter();
                mLv.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    private void initView() {
        mAddress_Manage = (TextView) findViewById(R.id.address_manage);
        mLv = (ListView) findViewById(R.id.address_lv);
        mAddress_Manage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_manage:
                Intent intent = new Intent(AddressActivity.this, AddressManageActivity.class);
                startActivity(intent);
                break;
        }
    }

    public interface AddressResult {
       // http://localhost:8080/market/addresslist
        @Headers("userid:20428")
        @GET("addresslist")
        Call<AddressBean> getAdress();
    }

    class addressAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mAddressList.size();
        }

        @Override
        public AddressBean.AddressListBean getItem(int position) {
            return mAddressList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_address, null);
            }
            final AddressBean.AddressListBean item = mAddressList.get(position);
            String adress = item.getAddressArea();
            TextView name = (TextView)convertView.findViewById(R.id.showaddress_name);
            TextView address = (TextView)convertView.findViewById(R.id.showaddress_address);
            TextView telNum = (TextView)convertView.findViewById(R.id.showaddress_num);
            LinearLayout ll_item_address = (LinearLayout) convertView.findViewById(R.id.ll_item_address);
            if (isOrder) {
                ll_item_address.setVisibility(View.VISIBLE);
            }
            ll_item_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),SureOrderformActivity.class);
                    intent.putExtra("name",item.getName());
                    intent.putExtra("address",item.getProvince()+item.getAddressArea()+item.getAddressDetail());
                    intent.putExtra("telNum",item.getPhoneNumber());
                    setResult(500,intent);
                    finish();
                }
            });
            name.setText(item.getName());
            address.setText(item.getProvince()+item.getAddressArea()+item.getAddressDetail());
            telNum.setText(item.getPhoneNumber());

            return convertView;
        }
    }


}
