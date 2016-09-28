package com.itheima.laosiji.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itheima.laosiji.Bean.GoodsBean;
import com.itheima.laosiji.Bean.SelectBean;
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

public class GoodsActivity extends AppCompatActivity implements View.OnClickListener {
	public String baseUrl = "http://192.168.39.113:8080/market/";
	private GridView mGv_goods;
	private List<GoodsBean.ProductListBean> mProductListBeen = new ArrayList<>();
	private gvAdapter mGvAdapter;
	private ImageView mIb_googs_back;
	private EditText mSearch;
	private TextView mSelect;
	private Button mClear;
	private Button mOk;
	private EditText mPric1;
	private EditText mPric2;
	private String mMoneg1;
	private String mMoney2;
	private GridView mBrand;
	private gvAdapter1 mGvAdapter1;
	private List<SelectBean.BrandBean.ValueBean> mValue = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods);
		initView();
		initData();
		initDatas();
	}

	private void initDatas() {
		Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
		Result result = retrofit.create(Result.class);
		Call<SelectBean> brand = result.getBrand();
		brand.enqueue(new Callback<SelectBean>() {
			@Override
			public void onResponse(Response<SelectBean> response, Retrofit retrofit) {
				SelectBean body = response.body();
				List<SelectBean.BrandBean> brand1 = body.getBrand();
				for (int i = 0; i < brand1.size(); i++) {
					mValue.addAll(brand1.get(i).getValue());
				}
			}

			@Override
			public void onFailure(Throwable throwable) {

			}
		});
	}

	private void initView() {
		mGv_goods = (GridView) findViewById(R.id.gv_goods);
		mIb_googs_back = (ImageView) findViewById(R.id.ib_googs_back);
		mSearch = (EditText) findViewById(R.id.et_goods_search);
		mSelect = (TextView) findViewById(R.id.tv_goods_select);
		mGvAdapter = new gvAdapter();
		mGv_goods.setAdapter(mGvAdapter);
		mIb_googs_back.setOnClickListener(this);


		mClear = (Button) findViewById(R.id.bt_select_clear);
		mOk = (Button) findViewById(R.id.bt_select_ok);
		mPric1 = (EditText) findViewById(R.id.et_select_pric1);
		mPric2 = (EditText) findViewById(R.id.et_select_pric2);
		mBrand = (GridView) findViewById(R.id.gv_brand);
		mClear.setOnClickListener(this);
		mOk.setOnClickListener(this);
		mPric1.setOnClickListener(this);
		mPric2.setOnClickListener(this);
		mSearch.setOnClickListener(this);
		mGvAdapter1 = new gvAdapter1();
		mBrand.setAdapter(mGvAdapter1);
		//TODO 商品gridview的点击事件
		mGv_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}
		});
		mSelect.setOnClickListener(this);
	}

	private void initData() {
		Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
		Goods goods = retrofit.create(Goods.class);
		Call<GoodsBean> goods1 = goods.getGoods(1, 10, "saleDown");

		goods1.enqueue(new Callback<GoodsBean>() {
			@Override
			public void onResponse(Response<GoodsBean> response, Retrofit retrofit) {
				GoodsBean body = response.body();
				mProductListBeen = body.getProductList();
				mGvAdapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure(Throwable throwable) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ib_googs_back:
				finish();
				break;
			case R.id.tv_goods_select:
				// TODO 商品筛选
//				startActivity(new Intent(this, SelectActivity.class));
				startActivity(new Intent(this,Myorder.class));
				break;
			case R.id.et_select_pric1:
				mMoneg1 = mPric1.getText().toString().trim();
				break;
			case R.id.et_select_pric2:
				mMoney2 = mPric2.getText().toString().trim();
				break;
			case R.id.bt_select_clear:
				if (!TextUtils.isEmpty(mMoneg1) && !TextUtils.isEmpty(mMoney2)) {

				} else {
					mPric1.setText("");
					mPric2.setText("");
					mPric1.requestFocus();
				}
				break;
			case R.id.bt_select_ok:
				finish();
				break;
			case R.id.et_goods_search:

				//TODO 跳转搜索界面

				break;
			// TODO 按价格排序
			case R.id.tv_goods_price:

				break;
		}
	}

	//	http://localhost:8080/market/hotproduct?page=1&pageNum=10&orderby=saleDown
	//	http://localhost:8080/market/productlist?page=1&pageNum=10&cId=125&orderby=saleDown
	public interface Goods {
		@GET("hotproduct")
		Call<GoodsBean> getGoods(@Query("page") int page, @Query("pageNum") int pageNum, @Query("orderby") String orderby);
	}

	private class gvAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mProductListBeen.size();
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
			convertView = View.inflate(getApplicationContext(), R.layout.goods_item, null);
			TextView goods_tv_title = (TextView) convertView.findViewById(R.id.goods_tv_title);
			ImageView goods_iv_icon = (ImageView) convertView.findViewById(R.id.goods_iv_icon);
			TextView tv_hot = (TextView) convertView.findViewById(R.id.tv_hot);
			TextView tv_prie = (TextView) convertView.findViewById(R.id.tv_prie);
			goods_tv_title.setText(mProductListBeen.get(position).getName());
			tv_prie.setText("$" + mProductListBeen.get(position).getPrice());
			tv_hot.setText("☆" + mProductListBeen.get(position).getMarketPrice());
			Glide.with(getApplicationContext()).load(baseUrl + mProductListBeen.get(position).getPic()).into(goods_iv_icon);
			return convertView;
		}
	}

	public class gvAdapter1 extends BaseAdapter {

		@Override
		public int getCount() {
			return mValue.size();
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
			convertView = View.inflate(getApplication(), R.layout.select_brands, null);
			TextView brands = (TextView) convertView.findViewById(R.id.bt_select_brands);
			brands.setText(mValue.get(position).getName());
			return convertView;
		}
	}

	public interface Result {
		@GET("brand")
		Call<SelectBean> getBrand();
	}
}
