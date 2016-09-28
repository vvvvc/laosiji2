package com.itheima.laosiji.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itheima.laosiji.Bean.MyProductBean;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.R;
import com.itheima.laosiji.View.BuyDialog;
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button maddToCart;//加入购物车的点击按钮
    private Button mBuy;//购买的点击按钮
    private Button mCollect;//加入收藏的点击按钮

    // private String                   baseUrl = "http://192.168.191.1:8080/market/";

    //private String  baseUrl = "http://192.168.39.62:8080/market/";
    // private String                   baseUrl = "http://192.168.191.5:8080/market/";
    private String baseUrl = Constant.baseUrl;


    private ArrayList<MyProductBean> al = new ArrayList<MyProductBean>();
    private ViewPager      mProductVp;
    private List<String>   mPics;
    private TextView       mOriginalPrize;
    private TextView       mPercentPrize;
    private TextView       mProductName;
    private RelativeLayout mProductComment;
    private LinearLayout   mDetail;
    private int            mCommentCount;

    public static final int ADD = 1;
    public static final int BUY = 2;

    private int addorBuy = -1;

    //这是相当于pId获取哪件衣服,商品的id,来自于上层,还得继续向下层传递
    private int receiveId;


    private int    mLimitPrice;
    private int    mMarketPrice;
    private String mName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        receiveId = id;
        getProductBean();
      /*  getProductBean(2);
        getProductBean(3);*/

    }

    /**
     * 初始化操作
     */
    private void init() {
        initView();
        initEvent();
        initData();
    }

    /**
     * 初始化布局上的控件
     */
    private void initView() {

        mProductVp = (ViewPager) findViewById(R.id.vp_productview);

        //三个最下面的按钮
        maddToCart = (Button) findViewById(R.id.bt_add_shoppingcart);
        mBuy = (Button) findViewById(R.id.bt_buy);
        mCollect = (Button) findViewById(R.id.bt_collect);

        //现价 原价
        mOriginalPrize = (TextView) findViewById(R.id.tv_originalprize);
        mPercentPrize = (TextView) findViewById(R.id.tv_percentprize);
        mProductName = (TextView) findViewById(R.id.tv_product_name);
        //商品详情点击
        mProductComment = (RelativeLayout) findViewById(R.id.product_comment);

        //TODO 商品图片
        mDetail = (LinearLayout) findViewById(R.id.details);


    }

    /**
     * 初始化事件
     */
    private void initEvent() {

    }

    /**
     * 初始化数据
     */


    //该方法通过Retrofit获取服务器上的信息 将他保存为bean对象 ,并放入集合  通过不同的Pid得到不同服装的信息
    public void getProductBean() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();


        BeanResult beanResult = retrofit.create(BeanResult.class);

        Call<MyProductBean> product = beanResult.getProduct(receiveId);

        product.enqueue(new Callback<MyProductBean>() {
            @Override
            public void onResponse(Response<MyProductBean> response, Retrofit retrofit) {
                MyProductBean productbean = response.body();

                //将获取到的bean放到al集合里面

                //TODO  这里有一个小问题就是如果在这里这几al.add(0,bean)会出现角标越界异常  这里调用一个addBean的方法在外面添加
                // productbean.getProduct().

                al.add(productbean);
                init();


            }

            @Override
            public void onFailure(Throwable throwable) {

            }


        });

    }


    private void initData() {


        MyProductBean bean1 = al.get(0);//第一件衣服的Bean信息

        //商品的现价
        mLimitPrice = bean1.getProduct().getLimitPrice();
        mPercentPrize.setText(mLimitPrice + "");

        //商品的市场价
        mMarketPrice = bean1.getProduct().getMarketPrice();
        mOriginalPrize.setText(mMarketPrice + "");

        //商品的名字
        mName = bean1.getProduct().getName();
        mProductName.setText(mName);


       /* System.out.println(bean1.getProduct().getBuyLimit());*/
        mCommentCount = bean1.getProduct().getCommentCount();


        mPics = bean1.getProduct().getPics();

        if (mPics.size() == 0 || mPics == null) {
            mPics = bean1.getProduct().getBigPic();
        }





        for (int i = 0; i < mPics.size(); i++) {

            ImageView iv = getPic(i);

            mDetail.addView(iv);
        }
        Bitmap drawingCache = getPic(0).getDrawingCache();
        //点击事件放在这里是因为  不放在这里mPics里面的数据还没被加载 无法显示
        mBuy.setOnClickListener(this);
        maddToCart.setOnClickListener(this);
        mProductComment.setOnClickListener(this);


        //图片展示的适配器
        mProductVp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mPics.size();
            }


            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }


            //TODO   图片修改

            @Override
            public Object instantiateItem(ViewGroup container, int position) {


                ImageView iv = getPic(position);
                container.addView(iv);


                return iv;
            }
        });


    }


    public ImageView getPic(int position) {
        //这是详情展示的图片  因为和上面的图片一样 所以直接使用下面的代码
        ImageView iv = new ImageView(getApplicationContext());
        String img = mPics.get(position);

        Glide.with(getApplicationContext()).load(baseUrl + img).into(iv);
        return iv;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();


        switch (id) {
            case R.id.bt_buy:

                addorBuy = BUY;
                //TODO   没办法修改图片  没办法直接船体对象 只能传递id

                //分别向dialog传递了 图片的地址,价格,和商品名字,商品id
                BuyDialog buyDialog = new BuyDialog(this, mPics.get(0), mLimitPrice, mName, receiveId, addorBuy);
                buyDialog.show();


                break;
            case R.id.bt_add_shoppingcart:
                addorBuy = ADD;
                BuyDialog addDialog = new BuyDialog(this, mPics.get(0), mLimitPrice, mName, receiveId, addorBuy);
                addDialog.show();


                break;

            case R.id.product_comment:

                // 进入评价详情界面
                Intent intent = new Intent(this, CommentActivity.class);

                /*intent.putExtra();
                startActivity();
*/

                startActivity(intent);
                break;
            // bt_collect:

        }

    }


    public interface Result {

        @GET("product")
        Call<ResponseBody> getProduct(@Query("pId") int pId);
    }


    //获取bean的接口
    public interface BeanResult {

        @GET("product")
        Call<MyProductBean> getProduct(@Query("pId") int pId);
    }


}
