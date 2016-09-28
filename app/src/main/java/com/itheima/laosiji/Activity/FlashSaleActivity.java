package com.itheima.laosiji.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itheima.laosiji.Bean.LitmitBuyBean;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

public class FlashSaleActivity extends AppCompatActivity implements View.OnClickListener {
    private String baseUrl = Constant.baseUrl;

    private List<LitmitBuyBean.ProductListBean> mBeanList = new ArrayList<>();
    private GridView mGridView;
    private ImageView mLimitbuy_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_sale);
        getServerData();
        initView();
        getServerData();

    }

    private void initView() {
        mGridView = (GridView) findViewById(R.id.limitbuy_gridview);
        mLimitbuy_back = (ImageView) findViewById(R.id.limitbuy_back);
        mLimitbuy_back.setOnClickListener(this);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent data = new Intent(FlashSaleActivity.this,ProductDetailsActivity.class);
                data.putExtra("id",mBeanList.get(position).getId());


                // TODO: 2016/9/25 zhou


                startActivity(data);

            }
        });

    }

    private void getServerData() {
        //http://localhost:8080/market/limitbuy?page=1&pageNum=10
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        Result result = retrofit.create(Result.class);
        Call<LitmitBuyBean> call = result.getLimitBuy(1, 10);
        call.enqueue(new Callback<LitmitBuyBean>() {
            @Override
            public void onResponse(Response<LitmitBuyBean> response, Retrofit retrofit) {
                LitmitBuyBean body = response.body();
                int listCount = body.getListCount();
                mBeanList = body.getProductList();
                mGridView.setAdapter(new GridViewAdapter());
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.limitbuy_back:
               // startActivity(new Intent(this, HomeActivity.class));
                finish();
                break;
        }
    }

    class GridViewAdapter extends BaseAdapter implements View.OnClickListener {
        @Override
        public int getCount() {
            return mBeanList.size();
        }

        @Override
        public LitmitBuyBean.ProductListBean getItem(int position) {
            return mBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(FlashSaleActivity.this,R.layout.item_gridview_flashsale,null);
            }
            ImageView limitbuy_iv = (ImageView) convertView.findViewById(R.id.limitbuy_iv);
            TextView limitbuy_tv_desc = (TextView) convertView.findViewById(R.id.limitbuy_tv_desc);
            TextView limitbuy_tv_count = (TextView) convertView.findViewById(R.id.limitbuy_tv_count);
            TextView limitbuy_tv_price1 = (TextView) convertView.findViewById(R.id.limitbuy_tv_price1);
            TextView limitbuy_tv_price2 = (TextView) convertView.findViewById(R.id.limitbuy_tv_price2);

            Button bt = (Button) convertView.findViewById(R.id.limitbuy_buynow);
            bt.setOnClickListener(this);
            LitmitBuyBean.ProductListBean bean = getItem(position);
            Glide.with(getApplicationContext()).load(baseUrl + bean.getPic()).into(limitbuy_iv);
            limitbuy_tv_desc.setText(bean.getName());
            //$ 18.90
            limitbuy_tv_price1.setText("$  " + bean.getLimitPrice());
            limitbuy_tv_price2.setText("$  " + bean.getLimitPrice());
            return convertView;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.limitbuy_buynow:
                    break;
            }
        }
    }

    public interface Result{
        @GET("limitbuy")
        Call<LitmitBuyBean> getLimitBuy(@Query("page") int page, @Query("pageNum") int pageNum);
    }
}
