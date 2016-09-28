package com.itheima.laosiji.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itheima.laosiji.Activity.HomeActivity;
import com.itheima.laosiji.Activity.ShopcarActivity;
import com.itheima.laosiji.Bean.SupportBrandBean;
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

/**
 * Created by Marlboro丶 on 2016/9/24.
 */
public class ShoppingFragment extends Fragment implements View.OnClickListener {

    private Context mcontext;
    private String baseurl = Constant.baseUrl;
    ArrayList<SupportBrandBean.BrandBean.ValueBean> yumalist = new ArrayList<>();
    ArrayList<SupportBrandBean.BrandBean.ValueBean> yinyanglist = new ArrayList<>();
    ArrayList<SupportBrandBean.BrandBean.ValueBean> babylist = new ArrayList<>();
    ArrayList<SupportBrandBean.BrandBean.ValueBean> childlist = new ArrayList<>();
    ArrayList<SupportBrandBean.BrandBean.ValueBean> fashionlist = new ArrayList<>();
    ArrayList<SupportBrandBean.BrandBean.ValueBean> adultlist = new ArrayList<>();
    ArrayList<SupportBrandBean.BrandBean.ValueBean> daylylist = new ArrayList<>();
    ArrayList<SupportBrandBean.BrandBean.ValueBean> huazhuanglist = new ArrayList<>();
    ArrayList<SupportBrandBean.BrandBean.ValueBean> cleanlist = new ArrayList<>();
    ArrayList<ArrayList<SupportBrandBean.BrandBean.ValueBean>> lists = new ArrayList<>();
    private GridView gv_yunma;
    private GridView gv_yinyang;
    private GridView gv_child;
    private GridView gv_baby;
    private GridView gv_fashion;
    private GridView gv_adult;
    private GridView gv_dayly;
    private GridView gv_huazhuang;
    private GridView gv_clean;
    private ImageButton back;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_gouwu, null);
        back = (ImageButton) view.findViewById(R.id.ib_back);
        back.setOnClickListener(this);
        gv_yunma = (GridView) view.findViewById(R.id.gv_yunma);
        gv_yinyang = (GridView) view.findViewById(R.id.gv_yinyang);
        gv_baby = (GridView) view.findViewById(R.id.gv_baby);
        gv_child = (GridView) view.findViewById(R.id.gv_child);
        gv_fashion = (GridView) view.findViewById(R.id.gv_fashion);
        gv_adult = (GridView) view.findViewById(R.id.gv_adult);
        gv_dayly = (GridView) view.findViewById(R.id.gv_dayly);
        gv_huazhuang = (GridView) view.findViewById(R.id.gv_huazhuang);
        gv_clean = (GridView) view.findViewById(R.id.gv_clean);
        TextView shopcar = (TextView) view.findViewById(R.id.tv_shopcar);
        shopcar.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //每次进来都会添加,所以要先清除
        clearlist();
        addalllist();
        getdata();

    }

    private void clearlist() {
        if (lists.size() > 0) {
            for (int i = 0; i < lists.size() - 1; i++) {
                lists.get(i).clear();
            }
            lists.clear();
        }
    }

    private void gridViewDddAdapter() {
        gv_yunma.setAdapter(new myadapter(yumalist));
        gv_yinyang.setAdapter(new myadapter(yinyanglist));
        gv_baby.setAdapter(new myadapter(babylist));
        gv_child.setAdapter(new myadapter(childlist));
        gv_fashion.setAdapter(new myadapter(fashionlist));
        gv_adult.setAdapter(new myadapter(adultlist));
        gv_dayly.setAdapter(new myadapter(daylylist));
        gv_huazhuang.setAdapter(new myadapter(huazhuanglist));
        gv_clean.setAdapter(new myadapter(cleanlist));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                FragmentActivity activity = getActivity();
                if (activity instanceof HomeActivity) {
                    ((HomeActivity) activity).getSupportFragmentManager().beginTransaction().replace(R.id.fl_home, new HomeFragment()).commit();
                }
                break;
            case R.id.tv_shopcar:
                Intent intent = new Intent(mcontext, ShopcarActivity.class);
                startActivity(intent);
                break;

        }
    }

    interface result {
        @GET("brand")
        Call<SupportBrandBean> getbrand();
    }

    public void getdata() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
        result result = retrofit.create(result.class);
        Call<SupportBrandBean> call = result.getbrand();
        call.enqueue(new Callback<SupportBrandBean>() {
            @Override
            public void onResponse(Response<SupportBrandBean> response, Retrofit retrofit) {
                SupportBrandBean bean = response.body();
                List<SupportBrandBean.BrandBean> brand = bean.getBrand();
                for (int i = 0; i < brand.size(); i++) {
                    List<SupportBrandBean.BrandBean.ValueBean> value = brand.get(i).getValue();
                    lists.get(i).addAll(value);
                }
                gridViewDddAdapter();

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    public void addalllist() {
        lists.add(yumalist);
        lists.add(yinyanglist);
        lists.add(babylist);
        lists.add(childlist);
        lists.add(fashionlist);
        lists.add(adultlist);
        lists.add(daylylist);
        lists.add(huazhuanglist);
        lists.add(cleanlist);

    }

    class myadapter extends BaseAdapter {
        private ArrayList<SupportBrandBean.BrandBean.ValueBean> datas;

        public myadapter(ArrayList<SupportBrandBean.BrandBean.ValueBean> datas) {
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
            View view = View.inflate(mcontext, R.layout.support_brand_item, null);
            ImageView iv = (ImageView) view.findViewById(R.id.im_item);
            TextView tv = (TextView) view.findViewById(R.id.tv_name);
            SupportBrandBean.BrandBean.ValueBean valueBean = datas.get(position);
            Glide.with(mcontext.getApplicationContext()).load(baseurl + valueBean.getPic()).into(iv);
            tv.setText(valueBean.getName());
            return view;
        }
    }

}
