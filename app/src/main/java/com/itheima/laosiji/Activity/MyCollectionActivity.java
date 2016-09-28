package com.itheima.laosiji.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itheima.laosiji.Bean.MyCollectionBean;
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
import retrofit.http.Headers;
import retrofit.http.Query;

public class MyCollectionActivity extends AppCompatActivity implements View.OnClickListener {

    private GridView gv_collectiom;
    private String baseurl = Constant.baseUrl;
    private ArrayList<MyCollectionBean.ProductListBean> productList;
    private MyCollectionActivity.myadapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        intiview();
        intidata();
    }

    private void intidata() {
        getCollection();
    }

    private void intiview() {
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        ImageView iv_clean = (ImageView) findViewById(R.id.iv_clean);
        gv_collectiom = (GridView) findViewById(R.id.gv_collection);
        iv_back.setOnClickListener(this);
        iv_clean.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_clean:
                productList.clear();
                myadapter.notifyDataSetChanged();
                break;
        }
    }
    //http://localhost:8080/market/favorites?page=1&pageNum=10
    public interface result{
        @Headers("userid:20428")
        @GET("favorites")
        Call<MyCollectionBean>getColection(@Query("page") int page,
                                           @Query("pageNum") int pagenum);
    }
    public void getCollection(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
        result result = retrofit.create(result.class);
        Call<MyCollectionBean> call = result.getColection(1, 10);
        call.enqueue(new Callback<MyCollectionBean>() {

            @Override
            public void onResponse(Response<MyCollectionBean> response, Retrofit retrofit) {
                MyCollectionBean myCollectionBean = response.body();
                productList = (ArrayList<MyCollectionBean.ProductListBean>) myCollectionBean.getProductList();
                myadapter = new myadapter(productList);
                gv_collectiom.setAdapter(myadapter);
            }

            @Override
            public void onFailure(Throwable throwable) {
            }
        });
    }
    public class myadapter extends BaseAdapter{
        private ArrayList<MyCollectionBean.ProductListBean> datas;

        public myadapter(ArrayList<MyCollectionBean.ProductListBean> datas) {
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.my_collection_item, null);
            ImageView pic = (ImageView) view.findViewById(R.id.iv_pic);
            TextView name = (TextView) view.findViewById(R.id.tv_name);
            TextView market = (TextView) view.findViewById(R.id.tv_market_price);
            TextView price = (TextView) view.findViewById(R.id.tv_price);
            MyCollectionBean.ProductListBean productListBean = datas.get(position);
            Glide.with(getApplicationContext()).load(baseurl + productListBean.getPic()).into(pic);
            name.setText(productListBean.getName());
            market.setText("市场价:" + productListBean.getMarketPrice());
            price.setText("会员价:" + productListBean.getPrice());
            return view;
        }
    }
}
