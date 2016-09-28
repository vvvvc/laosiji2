package com.itheima.laosiji.Fragment;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.itheima.laosiji.Activity.GoodsActivity;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.R;
import com.itheima.laosiji.Bean.GrpBean;
import com.itheima.laosiji.Bean.SortBean;
import com.itheima.laosiji.Bean.TypeBean;
import com.itheima.laosiji.View.Grid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;

/**
 * Created by Marlboro丶 on 2016/9/24.
 */
public class CatagoryFragment extends Fragment {
	public String baseUrl = Constant.baseUrl;
	private List<SortBean.CategoryBean> mCg = new ArrayList<>();
	private List<TypeBean> mType = new ArrayList<>();
	private ListView mLv_sort_item;
	private ListView mLv_sort_item2;
	private LvAdapter mLvAdapter;
	private LvAdapter2 mLvAdapter2;
	private Context mContent;
	private View mCatagroyFragmentView;
	private List<Fragment> mFragmentLists = new ArrayList<>();

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContent = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mCatagroyFragmentView = inflater.inflate(R.layout.activity_sort, null);
		initView();
		initData();

		return mCatagroyFragmentView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void initView() {
		mLv_sort_item2 = (ListView) mCatagroyFragmentView.findViewById(R.id.lv_sort_item2);
		mLv_sort_item = (ListView) mCatagroyFragmentView.findViewById(R.id.lv_sort_item);
		//TODO
		mLvAdapter = new LvAdapter();
		mLv_sort_item.setAdapter(mLvAdapter);
		mLvAdapter2 = new LvAdapter2();
		mLv_sort_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mLv(position);
			}
		});

	}

	private void mLv(int position) {
		mLvAdapter2.setType(mType.get(position).list);
		mLv_sort_item2.setAdapter(mLvAdapter2);
	}
	private void initData() {
		Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
		Result result = retrofit.create(Result.class);
		final Call<SortBean> category = result.getCategory();
		category.enqueue(new Callback<SortBean>() {
			@Override
			public void onResponse(Response<SortBean> response, Retrofit retrofit) {
				SortBean body = response.body();
				mCg = body.category;
				mType.clear();
				for (SortBean.CategoryBean Bean : mCg) {
					if (Bean.parentId == 0)
						mType.add(new TypeBean(Bean));
				}

				for (SortBean.CategoryBean Bean : mCg) {
					for (TypeBean type : mType) {
						if (Bean.parentId == type.id)
							type.list.add(new GrpBean(Bean));
					}
				}
				for (SortBean.CategoryBean Bean : mCg) {
					for (TypeBean type : mType) {
						for (GrpBean grp : type.list) {
							if (grp.id == Bean.parentId)
								grp.list.add(Bean);
						}
					}
				}
//				mLvAdapter2.notifyDataSetChanged();
				mLvAdapter.notifyDataSetChanged();
//				mLv_sort_item.setAdapter(new );
				mLv(0);
			}

			@Override
			public void onFailure(Throwable throwable) {
			}
		});
	}

	public class LvAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mType.size();
		}

		@Override
		public TypeBean getItem(int position) {
			return mType.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			view = View.inflate(mContent, R.layout.lv_sort_item, null);
			TextView tv_sort_item = (TextView) view.findViewById(R.id.tv_sort_item);
			tv_sort_item.setText(mType.get(position).name);
			return view;
		}
	}

	public class LvAdapter2 extends BaseAdapter {
		List<GrpBean> grp;

		@Override
		public int getCount() {
			return grp.size();
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
		public View getView(int position, View view, ViewGroup parent) {
			view = View.inflate(mContent, R.layout.lv_sort_item2, null);
			TextView item_tv_title = (TextView) view.findViewById(R.id.sort_item2);
			Grid gv_item2 = (Grid) view.findViewById(R.id.gv_item2);
			item_tv_title.setText(grp.get(position).name);
			gv_item2.setAdapter(new GvAdapter(grp.get(position).list));
			//TODO gv的点击事件
			gv_item2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					startActivity(new Intent(mContent, GoodsActivity.class));
				}
			});

			return view;
		}

		public void setType(List<GrpBean> grp) {
			this.grp = grp;
		}
	}
	//TODO

	public class GvAdapter extends BaseAdapter {
		private List<SortBean.CategoryBean> goods;

		public GvAdapter(List<SortBean.CategoryBean> goods1) {
			goods = goods1;
		}

		@Override
		public int getCount() {
			Log.e("GetCount", "" + goods.size());
			return goods.size();
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
			convertView = View.inflate(mContent, R.layout.gv_sort_iteam, null);
			TextView item_tv_title = (TextView) convertView.findViewById(R.id.item_tv_title);
			ImageView item_iv_icon = (ImageView) convertView.findViewById(R.id.item_iv_icon);
			item_tv_title.setText(goods.get(position).name);
			Glide.with(mContent).load(baseUrl + goods.get(position).pic).into(item_iv_icon);
			return convertView;
		}
	}

	public interface Result {
		@GET("category")
		Call<SortBean> getCategory();
	}


}
