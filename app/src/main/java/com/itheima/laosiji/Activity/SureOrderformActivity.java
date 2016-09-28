package com.itheima.laosiji.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.itheima.laosiji.Bean.MyOrderInfoBean;
import com.itheima.laosiji.Constant.CONSTAN_PRODUCT;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.R;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

public class SureOrderformActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int ADDRESS_REQUEST_CODE = 77;

    private static final int PAW_REQUEST_CODE        = 100;
    private static final int TRANSPORT_REQUEST_CODED = 200;
    private static final int FAPIAO_REQUEST_CODED    = 300;
    private int      mProductNum;
    private int      mProductColor;
    private int      mProductSize;
    private TextView mViewById;
    private TextView mTv_Prize;
    private TextView mTv_Fright;
    private TextView mToTal;
    private int      mProductPrize;
    private String   mProductName;
    private Button   mSubmit;


    private String baseUrl = Constant.baseUrl;

    // private String baseUrl = "http://192.168.191.1:8080/market/";

    //用户名

    //TODO  需要靠别人传进来 登录页面的传给我  我才能拿到
    private int userid = 20428;

    //商品详情
    private String sku;
    //地址传过来
    private int addressId = 139;

    //支付方式
    private int paymentType = 1;

    //快递方式
    private int deliveryType = 1;

    //这三个归发票
    private int    invoiceType    = 1;
    private String invoiceTitle   = "传智慧播客教育科技有限公司";
    private int    invoiceContent = 1;


    private int       mProductId;
    private String    mImg;
    private ImageView mIv_Product;

    private TextView     mTv_Num;
    private int          mTotalcount;
    private int          mTotalprice;
    private TextView     surepayway;
    private TextView     suresendtime;
    private TextView     surefapiao;
    private ImageView    mSkip_to_address;
    private TextView     mTv_name;
    private TextView     mTv_tele;
    private TextView     mTv_address;
    private String       mAllImg;
    private LinearLayout mLlPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sure_orderform);
        initView();
        Intent intent = getIntent();
        //获取选择的地址
      /*  String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String telNum = intent.getStringExtra("telNum");
        mTv_name.setText(name);
        mTv_address.setText(address);
        mTv_tele.setText(telNum);*/

        int from = intent.getIntExtra("from", -1);
        if (from == 0) {
            //说明是购物车跳转过来的
            String cartSku = intent.getStringExtra(CONSTAN_PRODUCT.PRODUCT_SKU);
            sku = cartSku;

            System.out.println(sku + "我我我");
            mTotalcount = intent.getIntExtra(CONSTAN_PRODUCT.PRODUCT_TOTALCOUNT, -1);
            System.out.println(mTotalcount + "");
            mTotalprice = intent.getIntExtra(CONSTAN_PRODUCT.PRODUCT_TOTALPRICE, -1);
            mAllImg = intent.getStringExtra(CONSTAN_PRODUCT.PRODUCT_ALLPIC);
            initDat();

        } else if (from == 1) {
            mProductId = intent.getIntExtra(CONSTAN_PRODUCT.PRODUCT_ID, -1);
            mProductNum = intent.getIntExtra(CONSTAN_PRODUCT.PRODUCT_NUM, -1);
            mProductColor = intent.getIntExtra(CONSTAN_PRODUCT.PRODUCT_COLOR, -1);
            mProductSize = intent.getIntExtra(CONSTAN_PRODUCT.PRODUCT_SIZE, -1);
            mProductPrize = intent.getIntExtra(CONSTAN_PRODUCT.PRODUCT_PRIZE, -1);
            mProductName = intent.getStringExtra(CONSTAN_PRODUCT.PRODUCT_NAME);
            mImg = intent.getStringExtra(CONSTAN_PRODUCT.PRODUCT_PIC);

            initData();
        }


    }

    private void initData() {

        //分别代表的是商品Id,商品数目,(商品颜色,商品尺码)
        //sku="1:3:1,2,3,4|2:2:2,3"; // 已更新


        //1,2,3,4 黄蓝绿红  L,XL,XLL 5,6,7


        sku = mProductId + ":" + mProductNum + ":" + mProductColor + "," + mProductSize;

        //如果不用伪数据 就用下面这个 勉强可以符合(黄色 红色 算红色  ,绿色,蓝色算 绿色)  已更新
        //考虑到还有数目问题  所以得拼接   已更新
        // sku=mProductId+":"+mProductNum+":"+((mProductColor+2)/2)+","+(mProductSize+3);
        mTv_Num.setText("共" + mProductNum + "件");

        mTv_Prize.setText("¥ " + mProductNum * mProductPrize);
        // mTv_Prize.setText("\n ¥ "+mProductNum*mProductPrize);
        mToTal.setText("\n\n ¥" + (mProductNum * mProductPrize + 10));
        //mIv_Product.set;

        //加载图片
        Glide.with(getApplicationContext()).load(baseUrl + mImg).into(mIv_Product);

        //如果不用伪数据 就用下面这个 勉强可以符合(黄色 红色 算红色  ,绿色,蓝色算 绿色)
        sku = mProductId + ":" + mProductNum + ":" + ((mProductColor + 2) / 2) + "," + (mProductSize + 3);

    }

    //TODO  加上图片
    private void initDat() {
        Toast.makeText(this, "共" + mTotalcount + "件", Toast.LENGTH_SHORT).show();

        mIv_Product.setVisibility(View.GONE);
        String[] split = mAllImg.split("\\|");
        for (int i = 0; i < split.length; i++) {
            ImageView iv = new ImageView(this);
            Glide.with(getApplicationContext()).load(baseUrl + split[i]).into(iv);
            mLlPic.addView(iv);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)iv.getLayoutParams();
            layoutParams.width = 50;
            layoutParams.width = 50;
            layoutParams.setMargins(10,0,10,0);
            iv.setLayoutParams(layoutParams);

        }

        mTv_Num.setText("共" + mTotalcount + "件");
        mTv_Prize.setText("¥ " + mTotalprice);
        mToTal.setText("\n\n ¥" + (mTotalprice + 10));


    }


    private void initView() {
        mTv_address = (TextView) findViewById(R.id.tv_address);
        mTv_tele = (TextView) findViewById(R.id.tv_tele);
        mTv_name = (TextView) findViewById(R.id.tv_name);

        mLlPic = (LinearLayout) findViewById(R.id.ll_pic);
        mSkip_to_address = (ImageView) findViewById(R.id.skip_to_address);
        mSkip_to_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SureOrderformActivity.this, AddressActivity.class);
                intent.setAction("SureOrderformActivity");
                startActivityForResult(intent, ADDRESS_REQUEST_CODE);
            }
        });

        mTv_Num = (TextView) findViewById(R.id.tv_num);
        mIv_Product = (ImageView) findViewById(R.id.iv_product);

        //显示价格
        mTv_Prize = (TextView) findViewById(R.id.tv_prize);
        mTv_Fright = (TextView) findViewById(R.id.tv_freight);
        mToTal = (TextView) findViewById(R.id.tv_total);

        //提交
        mSubmit = (Button) findViewById(R.id.bt_submit);
        mTv_Num.setText("共" + mProductNum + "件");

        mTv_Prize.setText("¥ " + mProductNum * mProductPrize);
        // mTv_Prize.setText("\n ¥ "+mProductNum*mProductPrize);
        mToTal.setText("\n\n ¥" + (mProductNum * mProductPrize + 10));
        //mIv_Product.set;

        //加载图片
        Glide.with(getApplicationContext()).load(baseUrl + mImg).into(mIv_Product);


        mSubmit.setOnClickListener(this);

        RelativeLayout payway = (RelativeLayout) findViewById(R.id.rl_payway);
        payway.setOnClickListener(this);
        surepayway = (TextView) findViewById(R.id.tv_payway);

        RelativeLayout sendway = (RelativeLayout) findViewById(R.id.rl_sendway);
        sendway.setOnClickListener(this);
        suresendtime = (TextView) findViewById(R.id.tv_sendtime);

        RelativeLayout fapiao = (RelativeLayout) findViewById(R.id.rl_fapiao);
        fapiao.setOnClickListener(this);
        surefapiao = (TextView) findViewById(R.id.tv_fapiao);


    }

    //TODO  在这里跳转页面
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.bt_submit:
                submitOrder();
                break;
            case R.id.rl_payway:
                Intent intent = new Intent(getApplicationContext(), PayWayActivity.class);
                startActivityForResult(intent, PAW_REQUEST_CODE);
                break;
            case R.id.rl_sendway:
                Intent intent2 = new Intent(getApplicationContext(), TransportWayActivity.class);
                startActivityForResult(intent2, TRANSPORT_REQUEST_CODED);
                break;
            case R.id.rl_fapiao:
                Intent intent3 = new Intent(getApplicationContext(), FaPiaoActivity.class);
                startActivityForResult(intent3, FAPIAO_REQUEST_CODED);
                break;


            default:
                break;

        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //服务器提交
    private void submitOrder() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        Result result = retrofit.create(Result.class);

        Call<MyOrderInfoBean> inFo = result.postInfo(userid, sku, addressId, paymentType, deliveryType, invoiceType, invoiceTitle, invoiceContent);

        inFo.enqueue(new Callback<MyOrderInfoBean>() {
            @Override
            public void onResponse(Response<MyOrderInfoBean> inFo, Retrofit retrofit) {
                MyOrderInfoBean inFoBean = inFo.body();
                //获取到了订单码
                String orderId = inFoBean.getOrderInfo().getOrderId();

                //获取支付总价,向下一级传播
                int price = inFoBean.getOrderInfo().getPrice();


                //获取支付方式,向下一级传播
                inFoBean.getOrderInfo().getPaymentType();

                Intent intent = new Intent(SureOrderformActivity.this, SuccessActivity.class);

                intent.putExtra(CONSTAN_PRODUCT.ORDERID, orderId);
                intent.putExtra(CONSTAN_PRODUCT.FINAL_PRIZE, price);

                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    //TODO  目前的工作
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 200:
                String payway = data.getStringExtra("payway");
                String s1 = "\n" + payway;
                switch (payway) {
                    case "到付-现金":
                        paymentType = 1;
                        break;
                    case "到付-pos":
                        paymentType = 2;
                        break;
                    case "支付宝":
                        paymentType = 3;
                        break;
                }

                surepayway.setText(s1);
                break;
            case 300:
                String sendtime = data.getStringExtra("transportway");
                String s2 = "\n" + sendtime;
                switch (sendtime) {
                    case "工作日,双休日均可":
                        paymentType = 1;
                        break;
                    case "只双休节假日送货":
                        paymentType = 2;
                        break;
                    case "只工作日送货":
                        paymentType = 3;
                        break;
                }


                suresendtime.setText(s2);
                break;
            case 400:
                String fapiao = data.getStringExtra("fapiao");
                String s3 = "\n" + fapiao;

                //那边传过来的格式  String s1 = title + "/" + contant;

                String[] split = fapiao.split("\\|");
                Toast.makeText(this, split[0], Toast.LENGTH_SHORT).show();
                Toast.makeText(this, split[1], Toast.LENGTH_SHORT).show();

                System.out.println(split[0]);

                switch (split[1]) {
                    case "图书":
                        invoiceContent = 1;
                        break;
                    case "服装":
                        invoiceContent = 2;
                        break;
                    case "耗材":
                        invoiceContent = 3;
                        break;

                    case "软件":
                        invoiceContent = 4;
                        break;
                    case "资料":
                        invoiceContent = 5;
                        break;

                    default:
                        break;

                }
                switch (split[0]) {
                    case "个人":
                        invoiceContent = 1;
                        break;
                    case "单位":
                        invoiceContent = 2;
                        break;


                    default:
                        break;

                }


                invoiceTitle = split[2];

                surefapiao.setText(s3);
                break;
            case 500:
                Log.e("cjw", "case 77");
                String name = data.getStringExtra("name");
                String address = data.getStringExtra("address");
                String telNum = data.getStringExtra("telNum");
                mTv_name.setText(name);
                mTv_tele.setText(telNum);
                mTv_address.setText(address);
                break;
        }
    }

    public interface Result {

        // @Header("userid")
      /*  以下按顺序分别是userId  用户账号
        商品详情
        地址
        支付方式
        快递方式
        发票方式
        发票标题
        发票内容*/
        @FormUrlEncoded//如果是post请求必须设置该注解
        @POST("ordersumbit")
        Call<MyOrderInfoBean> postInfo(@Header("userid") int userid,
                                       @Field("sku") String productInfo,
                                       @Field("addressId") int addressId,
                                       @Field("paymentType") int paymentType,
                                       @Field("deliveryType") int deliveryType,
                                       @Field("invoiceType") int invoiceType,
                                       @Field("invoiceTitle") String invoiceTitle,
                                       @Field("invoiceContent") int invoiceContent
        );
    }


}
