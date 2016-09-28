package com.itheima.laosiji.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.bumptech.glide.Glide;
import com.itheima.laosiji.Activity.FlashSaleActivity;
import com.itheima.laosiji.Activity.HotProduceActivity;
import com.itheima.laosiji.Activity.NewActivity;
import com.itheima.laosiji.Activity.SalesPromotionActivity;
import com.itheima.laosiji.Activity.SerachActivity;
import com.itheima.laosiji.Bean.HomeViewPagerBean;
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
public class HomeFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private Context mContent;//上下文
    private ViewPager mViewPager;
    //192.168.253.1
    private final String path = Constant.baseUrl;
    private List<String> list = new ArrayList<>();

    private MyAdapter mAdapter;
    private LinearLayout mPointGroup;
    private int prePosition = 0;
    private int imgCount = 7;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    int currentItem = mViewPager.getCurrentItem();
                    mViewPager.setCurrentItem(currentItem + 1);
                    handler.sendEmptyMessageDelayed(0,2000);
            }
        }
    };
    private ImageView mCatagory_Home;
    private ImageView mCatagory_column;
    private LinearLayout mCatagory_my;
    private ImageView mCatagory_setting;
    private ImageView mCatagory_shopcart;
    private ImageView mCatagory_my_iv;
    private TextView mCatagory_my_tv;
    private ImageView mMove_to_menu;
    private boolean isMenuOpen = false;
    private View mHomeFragmentView;
    private View mVoice;

    //-------------------------------------------------------------
    private static final int REQUEST_UI = 1;

    private Button button_start;//开始按钮
    private EditText text_input;//语音识别对话框
    private BaiduASRDigitalDialog mDialog = null;
    private DialogRecognitionListener mDialogListener = null;
    private String API_KEY = "I2xMGLGkBXYjdRC2Sj7FHWr3";
    private String SECRET_KEY = "ca1b72510ceb4a7e48344f7aa83dac49";

    private onChangeFragment mOnChangeFragment;
    private EditText mSkip_search_page;

    //初始化方法
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mHomeFragmentView = inflater.inflate(R.layout.fragment_home, null);
        list.clear();
        handler.removeCallbacksAndMessages(null);
        return mHomeFragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initVoiceView();
        //使用retrofit获取数据

        Retrofit retrofit = new Retrofit.Builder().baseUrl(path).addConverterFactory(GsonConverterFactory.create()).build();
        Result result = retrofit.create(Result.class);
        Call<HomeViewPagerBean> call = result.getHome();
        call.enqueue(new Callback<HomeViewPagerBean>() {
            @Override
            public void onResponse(Response<HomeViewPagerBean> response, Retrofit retrofit) {
                HomeViewPagerBean homeViewPagerBean = response.body();
                List<HomeViewPagerBean.HomeTopicBean> homeTopic = homeViewPagerBean.getHomeTopic();
                for (HomeViewPagerBean.HomeTopicBean bean:homeTopic) {
                    String picUrl = bean.getPic();
                    list.add(path + picUrl);
                }
                initData();
            }
            @Override
            public void onFailure(Throwable throwable) {

            }
        });

    }

    private void initVoiceView() {
        if (mDialog == null) {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            Bundle params = new Bundle();

            params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, API_KEY);
            params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY, SECRET_KEY);

            params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME, BaiduASRDigitalDialog.THEME_BLUE_LIGHTBG);

            mDialog = new BaiduASRDigitalDialog(mContent, params);

            mDialogListener = new DialogRecognitionListener() {
                @Override
                public void onResults(Bundle mResults) {
                    ArrayList<String> rs = mResults != null ? mResults.getStringArrayList(RESULTS_RECOGNITION) : null;
                    if (rs != null && rs.size() > 0) {
                        Toast.makeText(mContent, rs.get(0), Toast.LENGTH_SHORT).show();
                    }
                }
            };
            mDialog.setDialogRecognitionListener(mDialogListener);
        }
    }

    private void initData() {
        mAdapter = new MyAdapter();
        mViewPager.setAdapter(mAdapter);
        for (int i = 0; i < list.size(); i++) {
            ImageView point = new ImageView(mContent);
            point.setImageResource(R.drawable.selector_point_bg);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            if (i != 0) {
                point.setEnabled(false);
            }
            params.leftMargin = 10;
            point.setLayoutParams(params);
            mPointGroup.addView(point);
        }
        handler.sendEmptyMessageDelayed(0,2000);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int pos = position % list.size();
                mPointGroup.getChildAt(prePosition).setEnabled(false);
                prePosition = pos;
                mPointGroup.getChildAt(pos).setEnabled(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.sendEmptyMessageDelayed(0,2000);
                        break;
                }
                return false;
            }
        });
    }

    private void initView() {
        //搜索点击事件
        mSkip_search_page = (EditText) getView().findViewById(R.id.skip_search_page);
        mSkip_search_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContent, SerachActivity.class);
                startActivity(intent);
            }
        });
        mViewPager = (ViewPager)getView().findViewById(R.id.home_viewpager);
        mPointGroup = (LinearLayout) getView().findViewById(R.id.pointgroup);
        //home主界面给语音按钮添加点击事件
        mVoice = getView().findViewById(R.id.home_head_voice);
        mVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
            }
        });
        //home左上角点击到menu页面
        mMove_to_menu = (ImageView) getView().findViewById(R.id.move_to_menu);
        mMove_to_menu.setOnClickListener(this);
        //获取中间的五个imagine设置点击事件
        ImageView home_category1 = (ImageView) getView().findViewById(R.id.home_category1);
        ImageView home_category2 = (ImageView) getView().findViewById(R.id.home_category2);
        ImageView home_category3 = (ImageView) getView().findViewById(R.id.home_category3);
        ImageView home_category4 = (ImageView) getView().findViewById(R.id.home_category4);
        ImageView home_category5 = (ImageView) getView().findViewById(R.id.home_category5);

        home_category1.setOnClickListener(this);
        home_category2.setOnClickListener(this);
        home_category3.setOnClickListener(this);
        home_category4.setOnClickListener(this);
        home_category5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_category1:
                Intent intent = new Intent(mContent, FlashSaleActivity.class);
                startActivity(intent);
                break;
            case R.id.home_category2:
                Intent salesPromotionintent = new Intent(mContent, SalesPromotionActivity.class);
                startActivity(salesPromotionintent);
                break;
            case R.id.home_category3:
                startActivity(new Intent(mContent,NewActivity.class));
                break;
            case R.id.home_category4:
                Intent hotProdeceIntent = new Intent(mContent, HotProduceActivity.class);
                startActivity(hotProdeceIntent);
                break;
            case R.id.home_category5:
                if (mOnChangeFragment != null) {
                    mOnChangeFragment.ChangeFragment(true);
                }
                break;
        }
    }



    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int pos = position % list.size();
            ImageView iv = new ImageView(mContent);
            Glide.with(mContent).load(list.get(pos)).into(iv);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    //创建接口对象
    public interface Result {
        @GET("home")
        Call<HomeViewPagerBean> getHome();
    }

    public interface onChangeFragment {
        public void ChangeFragment(boolean isSkip);
    }

    public void setOnChangeFragment(onChangeFragment listener) {
        this.mOnChangeFragment = listener;
    }

}


