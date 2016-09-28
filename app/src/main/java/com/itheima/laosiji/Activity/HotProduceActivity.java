package com.itheima.laosiji.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.itheima.laosiji.Adapter.MyRecyclerAdapter;
import com.itheima.laosiji.Bean.HotProductBean;
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

public class HotProduceActivity extends AppCompatActivity {

    private ListView mHotProduce;
    private String baseUrl = Constant.baseUrl;
    private List<HotProductBean.ProductListBean> mProductList;
    private RecyclerView mRecyclerView;
    private List<String> lists;
    private MyRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_produce);
        getServerData();


        initData();
        mRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration();//设置分割线
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));//设置RecyclerView布局管理器为2列垂直排布



    }

    private void initData() {
        lists = new ArrayList();
        for (int i = 0; i < 100; i++) {
            lists.add("" + i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void getServerData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        HotProduct hotProduct = retrofit.create(HotProduct.class);
        Call<HotProductBean> call = hotProduct.getHotProduct(1,10,"saleDown");
        call.enqueue(new Callback<HotProductBean>() {
            @Override
            public void onResponse(Response<HotProductBean> response, Retrofit retrofit) {
                HotProductBean body = response.body();
                mProductList = body.getProductList();
                adapter = new MyRecyclerAdapter(HotProduceActivity.this,mProductList);
                mRecyclerView.setAdapter(adapter);
                adapter.setOnClickListener(new MyRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void ItemClickListener(View view, int postion) {
                        Intent data = new Intent(HotProduceActivity.this, ProductDetailsActivity.class);
                        data.putExtra("id",mProductList.get(postion).getId());
                        startActivity(data);
                    }
                    @Override
                    public void ItemLongClickListener(View view, int postion) {

                    }
                });
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }



    // http://localhost:8080/market/hotproduct?page=1&pageNum=10&orderby=saleDown
    public interface HotProduct {
        @GET("hotproduct")
        Call<HotProductBean> getHotProduct(@Query("page") int page,
                                           @Query("pageNum") int pageNum,
                                           @Query("orderby") String orderby);
    }



}
