package com.itheima.laosiji.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itheima.laosiji.Bean.SearchResultBean;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.R;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

public class SearchRseultActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private EditText mEt_goods_search;
    private GridView mGridView;
    private String mSearchname;
    private List<SearchResultBean.ProductListBean> mProductList;
    private String baseUrl = Constant.baseUrl;
    private ImageView mEmpty_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rseult);
        initView();
        getServerData();
    }

    private void getServerData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        SearchResult searchResult = retrofit.create(SearchResult.class);
        Call<SearchResultBean> call = searchResult.getSerachResult(0, 10, "saleDown", mSearchname);
        call.enqueue(new Callback<SearchResultBean>() {
            @Override
            public void onResponse(Response<SearchResultBean> response, Retrofit retrofit) {
                mProductList = response.body().getProductList();
                mGridView.setEmptyView(mEmpty_iv);
                mGridView.setAdapter(new SearchResultAdapter());
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    private void initView() {
        mEt_goods_search = (EditText) findViewById(R.id.et_goods_search);
        mEmpty_iv = (ImageView) findViewById(R.id.empty_iv);
        Intent intent = getIntent();
        mSearchname = intent.getStringExtra("serachname");
        mEt_goods_search.setText(mSearchname);
        mGridView = (GridView) findViewById(R.id.gv_goods);
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent data = new Intent(SearchRseultActivity.this,ProductDetailsActivity.class);
        data.putExtra("id",mProductList.get(position).getId());
        startActivity(data);
    }

    class SearchResultAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mProductList.size();
        }

        @Override
        public SearchResultBean.ProductListBean getItem(int position) {
            return mProductList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(SearchRseultActivity.this, R.layout.item_searchresult, null);
            }
            SearchResultBean.ProductListBean item = getItem(position);

            ImageView iv = (ImageView) convertView.findViewById(R.id.searchresult_iv);
            TextView brand = (TextView) convertView.findViewById(R.id.searchresult_name);
            TextView marketPrice = (TextView) convertView.findViewById(R.id.searchresult_marketprice);
            TextView price = (TextView) convertView.findViewById(R.id.searchresult_price);

            Glide.with(SearchRseultActivity.this).load(baseUrl + item.getPic()).into(iv);
            brand.setText(item.getName());
            marketPrice.setText("市场价：" + item.getMarketPrice());
            price.setText("会员价：" + item.getPrice());
            return convertView;
        }
    }

    //http://localhost:8080/market/search?page=0&pageNum=10&orderby=saleDown&keyword=女裙
    public interface SearchResult {
        @GET("search")
        Call<SearchResultBean> getSerachResult(@Query("page") int page,
                                               @Query("pageNum") int pageNum,
                                               @Query("orderby") String orderby,
                                               @Query("keyword") String keyword);
    }

}
