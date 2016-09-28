package com.itheima.laosiji.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.laosiji.Bean.OrderBean;
import com.itheima.laosiji.R;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

public class Myorder extends AppCompatActivity implements View.OnClickListener {

	private Button mBack;
	private Button mBt_first;
	private Button mBt_second;
	private Button mBt_last;
	private ListView mLv_first;
	private ListView mLv_second;
	private ListView mLv_last;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorder);
		initView();
		initData();
	}

	private void initData() {

	}

	private void initView() {
		mBack = (Button) findViewById(R.id.bt_order_back);
		mBt_first = (Button) findViewById(R.id.bt_timeorder_first);
		mBt_second = (Button) findViewById(R.id.bt_timeorder_second);
		mBt_last = (Button) findViewById(R.id.bt_timeorder_last);
		mLv_first = (ListView) findViewById(R.id.lv_timeorder_first);
		mLv_second = (ListView) findViewById(R.id.lv_timeorder_second);
		mLv_last = (ListView) findViewById(R.id.lv_timeorder_last);
		mBack.setOnClickListener(this);
		mBt_first.setOnClickListener(this);
		mBt_second.setOnClickListener(this);
		mBt_last.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.bt_order_back:
				finish();
				break;
			case R.id.bt_timeorder_first:
				mBt_first.setTextColor(Color.WHITE);
				mBt_first.setBackgroundResource(R.drawable.ordertime_tableft_pressed_shape);
				mBt_second.setTextColor(Color.BLACK);
				mBt_second.setBackgroundResource(R.drawable.ordertime_tablemid_normal_shape);
				mBt_last.setTextColor(Color.BLACK);
				mBt_last.setBackgroundResource(R.drawable.ordertime_tablerg_normal_shape);


				break;
			case R.id.bt_timeorder_second:
				mBt_second.setTextColor(Color.WHITE);
				mBt_second.setBackgroundResource(R.drawable.ordertime_tablemid_pressed_shape);
				mBt_first.setTextColor(Color.BLACK);
				mBt_first.setBackgroundResource(R.drawable.ordertime_tableft_normal_shape);
				mBt_last.setTextColor(Color.BLACK);
				mBt_last.setBackgroundResource(R.drawable.ordertime_tablerg_normal_shape);
				break;
			case R.id.bt_timeorder_last:
				mBt_last.setTextColor(Color.WHITE);
				mBt_last.setBackgroundResource(R.drawable.ordertime_tablerg_pressed_shape);
				mBt_first.setTextColor(Color.BLACK);
				mBt_first.setBackgroundResource(R.drawable.ordertime_tableft_normal_shape);
				mBt_second.setTextColor(Color.BLACK);
				mBt_second.setBackgroundResource(R.drawable.ordertime_tablemid_normal_shape);
				System.out.println("");
				break;
		}
	}
//	http://localhost:8080/market/ordersumbit
	public interface Result {
		@POST("ordersumbit")
		Call<OrderBean> getOrder();
	}
}
