package com.itheima.laosiji.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itheima.laosiji.Bean.SalesPromotionBean;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.GlideRoundTransform;
import com.itheima.laosiji.R;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

public class SalesPromotionActivity extends AppCompatActivity {

    private GridView mGridView;
    private List<SalesPromotionBean.TopicBean> mBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_promotion);
        initView();
        getServerData();
    }

    private void getServerData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        SalesPromotion salesPromotion = retrofit.create(SalesPromotion.class);
        Call<SalesPromotionBean> call = salesPromotion.getSalesPromotion(1, 8);
        call.enqueue(new Callback<SalesPromotionBean>() {
            @Override
            public void onResponse(Response<SalesPromotionBean> response, Retrofit retrofit) {
                mBeanList = response.body().getTopic();
                mGridView.setAdapter(new SalesPromotionAdapter());
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    private void initView() {
        mGridView = (GridView) findViewById(R.id.sales_promotion_gridview);
    }

    public interface SalesPromotion {
        //http://localhost:8080/market/topic?page=1&pageNum=8
        @GET("topic")
        Call<SalesPromotionBean> getSalesPromotion(@Query("page") int page,
                                                   @Query("pageNum") int pageNum);
    }

    class SalesPromotionAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mBeanList.size();
        }

        @Override
        public SalesPromotionBean.TopicBean getItem(int position) {
            return mBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(SalesPromotionActivity.this, R.layout.item_salespromotion, null);
            }
            SalesPromotionBean.TopicBean item = getItem(position);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.item_salespromotion_iv);
            TextView textView = (TextView) convertView.findViewById(R.id.item_salespromotion_tv);
            Glide.with(SalesPromotionActivity.this).load(Constant.baseUrl + item.getPic()).transform(new GlideRoundTransform(getApplicationContext(),50)).into(imageView);
            textView.setText(item.getName());
            return convertView;
        }


    }
}
