package com.itheima.laosiji.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.laosiji.Adapter.CartAdapter;
import com.itheima.laosiji.Bean.MyCartBean;
import com.itheima.laosiji.Constant.CONSTAN_PRODUCT;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.R;
import com.itheima.laosiji.Utils.SpUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Administrator on 2016/9/26.
 */
public class ShopcarActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private String baseUrl = Constant.baseUrl;
    private String sku;
    public static final boolean ChooseAll = true;
    private String[] productItem;
    private ArrayList<Integer> mNoUseSku = new ArrayList<>();
    private List<MyCartBean.CartBean> mCartList;

    private ListView mCollectView;
    private int      mTotalCount;
    private int mTotalPrice = 0;
    private String mSNextSku;
    private TreeMap<Integer, Integer> tm = new TreeMap<>();
    private TextView    mTv_all;
    private TextView    mReconmandBrand;
    private TextView    mThankView;
    private CartAdapter mAdapter;
    private ImageView   mAllCheck;
    private boolean choose = false;
    private ArrayList mPicAl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcar);

        initView();

        //对应 BuyDiaLog往这边传数据,获取到相应的 sku信息
        //服务器提交   应该是从数据库读取
        sku = SpUtil.getString(this, CONSTAN_PRODUCT.Cart_SKU, null);
        if (sku == null) {
            //显示购物车是空的界面
            System.out.println(sku);
        } else {
            //显示购物车是由东西的界面 不能直接用|
            productItem = sku.split("\\|");


            //获取到    每个放在购物车中的 sku数据
            System.out.println(sku);


        }


        submitOrder();

    }

    private void initView() {


        mReconmandBrand = (TextView) findViewById(R.id.tv_support_brand);

        //结算
        mThankView = (TextView) findViewById(R.id.thank);
        //总价
        mTv_all = (TextView) findViewById(R.id.tv_all);
        //ListView
        mCollectView = (ListView) findViewById(R.id.lv_product);
        //打钩
        mAllCheck = (ImageView) findViewById(R.id.iv_quanxuan);

    }

    private void getSku() {

        //TODO  需要重新拼接
        StringBuffer nextSku = new StringBuffer();
        for (int i = 0; i < mCartList.size(); i++) {
            //如果当前位子 是没有被打钩的 就不放入 sku
            if (!mNoUseSku.contains(i)) {
                RelativeLayout childAt = (RelativeLayout) mCollectView.getChildAt(i);
                // System.out.println(mCollectView.getChildCount()+"222woww ");
                TextView numView = (TextView) childAt.findViewById(R.id.tv_productnum);
                String num = (String) numView.getText();
                 /*   String numm = num.trim();
                    int nn = Integer.parseInt(numm);*/
                System.out.println(num);
                String property = productItem[i];
                String[] ppp = property.split(":");

                String sku = ppp[0] + ":" + num + ":" + ppp[2];


                if (i != mCartList.size() - 1) {
                    nextSku.append(sku + "|");
                } else {
                    nextSku.append(sku);
                }


            }
        }
        mSNextSku = nextSku.toString();
    }

    private void initClick() {


        mThankView.setOnClickListener(this);
        mReconmandBrand.setOnClickListener(this);
        mAllCheck.setOnClickListener(this);
        mCollectView.setOnItemClickListener(this);
   




        /*for (int i = 0; i <mCartList.size() ; i++) {
            CollectView.getChildAt(0);

        }*/


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_support_brand:
                onBackPressed();
                break;
            case R.id.thank:


                getSku();

                Intent intent = new Intent(this, SureOrderformActivity.class);

                //1:3:1,2,3,4|2:2:2,3|2:2:1

              /*  System.out.println(productItem.length + ".");
                System.out.println(productItem[0] + ".");*/
                //图片的逻辑  下个界面
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mPicAl.size(); i++) {
                    String pic = (String) mPicAl.get(i);

                    if (pic != null) {
                        if (i != mPicAl.size() - 1) {
                            sb.append(pic + "|");
                        } else {
                            sb.append(pic);
                        }
                    }
                }
                String sbb = sb.toString();


                intent.putExtra("from", 0);
                intent.putExtra(CONSTAN_PRODUCT.PRODUCT_SKU, mSNextSku);
                intent.putExtra(CONSTAN_PRODUCT.PRODUCT_ALLPIC, sbb);
                intent.putExtra(CONSTAN_PRODUCT.PRODUCT_TOTALCOUNT, mTotalCount);
                intent.putExtra(CONSTAN_PRODUCT.PRODUCT_TOTALPRICE, mTotalPrice);


                startActivity(intent);
                finish();
                break;
            //TODO  全选
            case R.id.iv_quanxuan:

                //选择全部
                if (choose == ChooseAll) {

                    mAdapter.ChooseAll(choose);
                    mAllCheck.setImageResource(R.mipmap.ischeck);
                    choose = false;

                } else {
                    //取消选择全部
                    mAdapter.ChooseAllOff(!choose);
                    mAllCheck.setImageResource(R.mipmap.nocheck);
                    choose = true;
                }

                break;


            //TODO  编辑见面

            



        }


    }


    private void submitOrder() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        Result result = retrofit.create(Result.class);

        Call<MyCartBean> inFo = result.postInfo(sku);

        inFo.enqueue(new Callback<MyCartBean>() {
            @Override
            public void onResponse(Response<MyCartBean> inFo, Retrofit retrofit) {
                MyCartBean cartBean = inFo.body();

                //获取总数

                mTotalCount = cartBean.getTotalCount();

                //mCartList.get(0).

                // cartBean.getTotalPoint();
                //获取总价
                mTotalPrice = cartBean.getTotalPrice();
                mTv_all.setText(mTotalPrice + "");

                mCartList = cartBean.getCart();


                goTo();

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    //TODO  接口回调
    public void goTo() {
        mAdapter = new CartAdapter(this, mCartList);

        for (int i = 0; i < mCartList.size(); i++) {

            tm.put(i, mCartList.get(i).getProdNum());

        }
        //TODo  小问题 图片没被选中的还是会显示
        //获得图片
        mPicAl = new ArrayList();
        for (int i = 0; i < mCartList.size(); i++) {
            String pic = mCartList.get(i).getProduct().getPic();
            mPicAl.add(pic);
        }


        mAdapter.setOnGetNum(new CartAdapter.onGetNum() {


            //不断被调用的

            @Override
            public void getNum(int position, int num) {
                //Toast.makeText(ShopcarActivity.this, "num:" + num+"position:" + position,Toast.LENGTH_SHORT).show();
                //getSku();
                //sku= mSNextSku;
                //submitOrder();
                //Toast.makeText(ShopcarActivity.this, mSNextSku, Toast.LENGTH_SHORT).show();
                //把这几个数字存起来

                tm.put(position, num);

                //初始化 数目和价格
                mTotalPrice = 0;
                mTotalCount = 0;
                for (int i = 0; i < tm.size(); i++) {
                    Integer nums = tm.get(i);            //拿到商品数量 做加法
                    int price = mCartList.get(i).getProduct().getPrice();

                    mTotalPrice += nums * price;             //价格乘以数量
                    mTotalCount += nums;
                }
                mTv_all.setText(mTotalPrice + "");

                //mTotalCount

            }

            //TODO 打钩所有 相关在这里
            //把数目一起传过来,
            @Override
            public void getCheck(int position, int check, int num) {
                if (check == CartAdapter.ISCHECK) {
                    getNum(position, num);
                    //mAllCheck.setImageResource(R.mipmap.nocheck);
                    String pic = mCartList.get(position).getProduct().getPic();

                    if(mNoUseSku.contains(position)&&mNoUseSku!=null) {
                        mNoUseSku.remove(position);
                    }

                    mPicAl.set(position, pic);

                } else if (check == CartAdapter.NOCHECK) {
                    mPicAl.set(position, null);

                    //记录不进入计算的 sku;

                    mNoUseSku.add(position);

                    getNum(position, 0);
                    mAllCheck.setImageResource(R.mipmap.nocheck);
                    choose = true;

                }
            }
        });
        mCollectView.setAdapter(mAdapter);
        initClick();


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        int idd = mCartList.get(position).getProduct().getId();
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("id", idd);

        startActivity(intent);
        finish();
    }


    public interface Result {


        @FormUrlEncoded//如果是post请求必须设置该注解
        @POST("cart")
        Call<MyCartBean> postInfo(
                @Field("sku") String productInfo
        );
    }
}
