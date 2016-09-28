package com.itheima.laosiji.Activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.itheima.laosiji.Adapter.NewAdapter;
import com.itheima.laosiji.Bean.NewBean;
import com.itheima.laosiji.Bean.NewProductBean;
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

public class NewActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageView mIv_fan;
    private TextView mTv_fan;
    private TextView tev_common;
    private TextView tev_price;
    private TextView tev_sales;
    private TextView tev_sj;
    private TextView tev_sprice;
    private PopupWindow pw;
    private ImageView mTv_order;
    private ListView mLv;
    private List<NewBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        initView();
    }

    private void initView() {
        mDatas = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.39.38:8080/market/").addConverterFactory(GsonConverterFactory.create()).build();
        Result result = retrofit.create(Result.class);
        Call<NewProductBean> call = result.getNewproduct(1, 10, "saleDown");
        call.enqueue(new Callback<NewProductBean>() {
            @Override
            public void onResponse(Response<NewProductBean> response, Retrofit retrofit) {
                NewProductBean newProductBean = response.body();
                //TODO 首先拿到商品的集合,遍历,并添加到bean包里面
                List<NewProductBean.ProductListBean> productList = newProductBean.getProductList();
                for (int i = 0; i < productList.size(); i++) {
                     NewBean bean = new NewBean();
                    bean.marketPrice =productList.get(i).getMarketPrice();
                    bean.name = productList.get(i).getName();
                    bean.pic = productList.get(i).getPic();
                    mDatas.add(bean);
                }
                initData();

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });


    }

    private void initData() {
        mIv_fan = (ImageView) findViewById(R.id.iv_fan);
        mTv_fan = (TextView) findViewById(R.id.tv_fan);
        mTv_order = (ImageView) findViewById(R.id.tv_order);
        mLv = (ListView) findViewById(R.id.lv);
        mIv_fan.setOnClickListener(this);
        mTv_order.setOnClickListener(this);
        mTv_fan.setOnClickListener(this);

        //有参构造,传参数
        NewAdapter adapter = new NewAdapter(this,mDatas);

        mLv.setAdapter(adapter);

        //listView设置条目点击事件
        mLv.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    //接口
    public interface Result{
        //http://localhost:8080/market/newproduct?page=1&pageNum=10&orderby=saleDown
        @GET("newproduct")
        Call<NewProductBean> getNewproduct(@Query("page") int page, @Query("pageNum")int pageNum, @Query("orderby")String saleDown );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fan:
//                startActivity(new Intent(NewActivity.this, HomeFragment.class));
                finish();

                break;
            case R.id.tv_fan:
              //  startActivity(new Intent(NewActivity.this, HomeFragment.class));
                finish();
                break;
            case R.id.tv_order:
                //点击对应条目后，显示出popupwindow
                View view = View.inflate(NewActivity.this, R.layout.order_view, null);

                tev_common = (TextView) findViewById(R.id.tev_common);
                tev_price = (TextView) findViewById(R.id.tev_price);
                tev_sales = (TextView) findViewById(R.id.tev_sales);
                tev_sj = (TextView) findViewById(R.id.tev_sj);
                tev_sprice = (TextView) findViewById(R.id.tev_sprice);
                mTv_order = (ImageView) findViewById(R.id.tv_order);

                //PopupWindow会抢占全屏的焦点,说有的按钮不能点了
                int width = ViewGroup.LayoutParams.WRAP_CONTENT;
                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                //建立PopuWindow
                pw = new PopupWindow(view, width, height, true);
                //注意,一定要设置pw的背景(随便设置)
                pw.setBackgroundDrawable(new ColorDrawable());
                pw.showAsDropDown(mTv_order,-20,0);
                break;
        }
    }
}
