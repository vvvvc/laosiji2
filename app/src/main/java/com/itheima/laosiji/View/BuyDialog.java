package com.itheima.laosiji.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.itheima.laosiji.Activity.ProductDetailsActivity;
import com.itheima.laosiji.Activity.SureOrderformActivity;
import com.itheima.laosiji.Constant.CONSTAN_PRODUCT;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.R;
import com.itheima.laosiji.Utils.SpUtil;

/**
 * @ 创建者   zhou
 * @ 创建时间   2016/9/24 9:49
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2016/9/24$
 * @ 更新描述  ${TODO}
 */
public class BuyDialog extends Dialog implements View.OnClickListener {

    private Button      mSure;
    private ImageButton mGreen;
    private ImageButton mYello;
    private ImageButton mRed;
    private ImageButton mBlue;
    private TextView    mSizeL;
    private TextView    mSizeXL;
    private TextView    mSizeXLL;
    private Button      mAdd;
    private Button      mReduce;
    private TextView    mProductNum;


    public final static int PRODUCT_GREEN  = 3;
    public final static int PRODUCT_RED    = 4;
    public final static int PRODUCT_YELLOW = 1;
    public final static int PRODUCT_BLUE   = 2;

    public int currentColor;
    public int currentSize;
    public int currentNum;


    public final static int PRODUCT_L   = 5;
    public final static int PRODUCT_XL  = 6;
    public final static int PRODUCT_XLL = 7;
    private ImageView mIv_1;
    private ImageView mIv_2;
    private ImageView mIv_3;
    private ImageView mIv_4;
    private ImageView mIv_5;
    private ImageView mIv_6;
    private ImageView mIv_7;
    private ImageView mSmallProduct;

    private String baseUrl = Constant.baseUrl;

    private Context  mContext;
    private int      maddorBuy;
    private int      mLimitPrice;
    private String   mName;
    private String   mImg;
    private TextView mNameView;
    private TextView mPrizeView;
    private int      receiveId;

    public BuyDialog(Context context, String img, int limitPrice, String name, int receiveId, int addorBuy) {
        super(context, R.style.location_dialog_style);

        this.receiveId = receiveId;
        this.mImg = img;
        this.mLimitPrice = limitPrice;
        this.mName = name;
        this.mContext = context;
        //mSmallProduct.set
        this.maddorBuy = addorBuy;

       Window window = getWindow();
////        // 获取当前装载dialog窗口的布局参数
        WindowManager.LayoutParams layoutParams = window.getAttributes();
////        //        Display d = m.getDefaultDisplay();
////        //        layoutParams.height = (int) (d.getHeight() * 0.6);
////        //        layoutParams.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.95
        layoutParams.gravity = Gravity.BOTTOM;
////        DisplayMetrics d = context.getResources().getDisplayMetrics();
////        //TODO  问题很大 问题巨大!!!!!!!!!
////        //layoutParams.width = d.widthPixels;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
       layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
       window.setAttributes(layoutParams);

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //setContentView( View.inflate(getContext(),R.layout.product_buy_dialog,null),params);
        setContentView(R.layout.product_buy_dialog);

        initView();
        initData();


    }

    private void initData() {


        if (maddorBuy == ProductDetailsActivity.BUY) {
            currentNum = SpUtil.getInt(getContext(), CONSTAN_PRODUCT.PRODUCT_NUM, 1);
            currentColor = SpUtil.getInt(getContext(), CONSTAN_PRODUCT.PRODUCT_COLOR, -1);
            currentSize = SpUtil.getInt(getContext(), CONSTAN_PRODUCT.PRODUCT_SIZE, -1);
            // mSmallProduct=mTitle;

            //加载图片
            Glide.with((ProductDetailsActivity) mContext).load(baseUrl + mImg).into(mSmallProduct);
            //TODO  无法获得传过来的imgaview的图像

            mNameView.setText(mName);
            mPrizeView.setText(mLimitPrice + "");

            upDateView();
        } else if (maddorBuy == ProductDetailsActivity.ADD) {
            currentNum = SpUtil.getInt(getContext(), CONSTAN_PRODUCT.PRODUCT_ADDNUM, 1);
            currentColor = SpUtil.getInt(getContext(), CONSTAN_PRODUCT.PRODUCT_ADDCOLOR, -1);
            currentSize = SpUtil.getInt(getContext(), CONSTAN_PRODUCT.PRODUCT_ADDSIZE, -1);
            // mSmallProduct=mTitle;
            Glide.with((ProductDetailsActivity) mContext).load(baseUrl + mImg).into(mSmallProduct);
            //TODO  无法获得传过来的imgaview的图像

            mNameView.setText(mName);
            mPrizeView.setText(mLimitPrice + "");

            upDateView();
        } else {
            Toast.makeText(mContext, "出现错误", Toast.LENGTH_SHORT).show();
        }


    }

    private void upDateView() {


        upDateColor();
        upDateNum();
        updateSize();


    }

    private void upDateNum() {
        if (currentNum >= 1) {
            mProductNum.setText(currentNum + "");
        } else {
            currentNum = 1;
            Toast.makeText(mContext, "不能再少了", Toast.LENGTH_SHORT).show();
        }


    }

    private void updateSize() {

        if (currentSize != -1) {

            mIv_5.setVisibility(View.INVISIBLE);
            mIv_6.setVisibility(View.INVISIBLE);
            mIv_7.setVisibility(View.INVISIBLE);

            switch (currentSize) {
                case PRODUCT_L:
                    mIv_5.setVisibility(View.VISIBLE);
                    break;
                case PRODUCT_XL:
                    mIv_6.setVisibility(View.VISIBLE);
                    break;
                case PRODUCT_XLL:
                    mIv_7.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    private void upDateColor() {

        if (currentColor != -1) {
            mIv_1.setVisibility(View.INVISIBLE);
            mIv_2.setVisibility(View.INVISIBLE);
            mIv_3.setVisibility(View.INVISIBLE);
            mIv_4.setVisibility(View.INVISIBLE);

            switch (currentColor) {
                case PRODUCT_RED:
                    mIv_1.setVisibility(View.VISIBLE);
                    break;
                case PRODUCT_YELLOW:
                    mIv_2.setVisibility(View.VISIBLE);
                    break;
                case PRODUCT_BLUE:
                    mIv_3.setVisibility(View.VISIBLE);
                    break;
                case PRODUCT_GREEN:

                    System.out.println("我是绿色");
                    mIv_4.setVisibility(View.VISIBLE);
                    break;
            }
        }

    }

    private void initView() {

        mSure = (Button) findViewById(R.id.bt_makesure);


        //衣服缩小图
        mSmallProduct = (ImageView) findViewById(R.id.iv_smallproduct);
        //衣服名字
        mNameView = (TextView) findViewById(R.id.tv_name);
        //衣服价格
        mPrizeView = (TextView) findViewById(R.id.tv_prize);

        //颜色得
        mGreen = (ImageButton) findViewById(R.id.bt_green);
        mYello = (ImageButton) findViewById(R.id.bt_yello);
        mRed = (ImageButton) findViewById(R.id.bt_red);
        mBlue = (ImageButton) findViewById(R.id.bt_blue);

        //尺寸的
        mSizeL = (TextView) findViewById(R.id.tv_sizeL);
        mSizeXL = (TextView) findViewById(R.id.tv_sizeXL);
        mSizeXLL = (TextView) findViewById(R.id.tv_sizeXLL);

        //减少的
        mAdd = (Button) findViewById(R.id.bt_add);
        mReduce = (Button) findViewById(R.id.bt_reduce);


        //显示的数字
        mProductNum = (TextView) findViewById(R.id.tv_productnum);

        mIv_1 = (ImageView) findViewById(R.id.iv_1);
        mIv_2 = (ImageView) findViewById(R.id.iv_2);
        mIv_3 = (ImageView) findViewById(R.id.iv_3);
        mIv_4 = (ImageView) findViewById(R.id.iv_4);
        mIv_5 = (ImageView) findViewById(R.id.iv_5);
        mIv_6 = (ImageView) findViewById(R.id.iv_6);
        mIv_7 = (ImageView) findViewById(R.id.iv_7);


        mSure.setOnClickListener(this);

        mGreen.setOnClickListener(this);
        mYello.setOnClickListener(this);
        mRed.setOnClickListener(this);
        mBlue.setOnClickListener(this);

        mSizeL.setOnClickListener(this);
        mSizeXL.setOnClickListener(this);
        mSizeXLL.setOnClickListener(this);

        mAdd.setOnClickListener(this);
        mReduce.setOnClickListener(this);

        mProductNum.setOnClickListener(this);

    }


    @Override
    public void dismiss() {


        SaveToSp();


        super.dismiss();

    }

    private void SaveToSp() {


        if (maddorBuy == ProductDetailsActivity.BUY) {

            SpUtil.putInt(getContext(), CONSTAN_PRODUCT.PRODUCT_COLOR, currentColor);
            SpUtil.putInt(getContext(), CONSTAN_PRODUCT.PRODUCT_SIZE, currentSize);
            SpUtil.putInt(getContext(), CONSTAN_PRODUCT.PRODUCT_NUM, currentNum);
        } else if (maddorBuy == ProductDetailsActivity.ADD) {
            SpUtil.putInt(getContext(), CONSTAN_PRODUCT.PRODUCT_ADDCOLOR, currentColor);
            SpUtil.putInt(getContext(), CONSTAN_PRODUCT.PRODUCT_ADDSIZE, currentSize);
            SpUtil.putInt(getContext(), CONSTAN_PRODUCT.PRODUCT_ADDNUM, currentNum);
        } else {
            Toast.makeText(mContext, "存储错误", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            //颜色
            case R.id.bt_green:
                currentColor = PRODUCT_GREEN;
                upDateColor();
                System.out.println("绿色");

                break;
            case R.id.bt_blue:
                currentColor = PRODUCT_BLUE;
                upDateColor();
                break;
            case R.id.bt_yello:

                currentColor = PRODUCT_YELLOW;
                upDateColor();

                break;
            case R.id.bt_red:
                currentColor = PRODUCT_RED;
                upDateColor();

                break;
            //加减
            case R.id.bt_add:

                currentNum += 1;
                upDateNum();

                break;

            case R.id.bt_reduce:


                currentNum -= 1;
                upDateNum();

                break;
            case R.id.tv_sizeL:
                currentSize = PRODUCT_L;

                updateSize();
                break;
            case R.id.tv_sizeXL:
                currentSize = PRODUCT_XL;
                updateSize();
                break;
            case R.id.tv_sizeXLL:
                currentSize = PRODUCT_XLL;
                updateSize();

                break;
            case R.id.bt_makesure:


                if (maddorBuy == ProductDetailsActivity.BUY) {
                    Intent intent = new Intent(getContext(), SureOrderformActivity.class);
                   //用来判断是哪个界面跳转过去的
                    intent.putExtra("from",1);

                    intent.putExtra(CONSTAN_PRODUCT.PRODUCT_NUM, currentNum);
                    intent.putExtra(CONSTAN_PRODUCT.PRODUCT_COLOR, currentColor);
                    intent.putExtra(CONSTAN_PRODUCT.PRODUCT_SIZE, currentSize);
                    intent.putExtra(CONSTAN_PRODUCT.PRODUCT_PIC, mImg);

                    intent.putExtra(CONSTAN_PRODUCT.PRODUCT_PRIZE, mLimitPrice);
                    intent.putExtra(CONSTAN_PRODUCT.PRODUCT_NAME, mName);
                    intent.putExtra(CONSTAN_PRODUCT.PRODUCT_ID, receiveId);
                    getContext().startActivity(intent);
                } else {

                    //TODO  没有具体实现 加入购物车功能

                    addToCart();

                    dismiss();
                }

                break;


        }


    }

    private void addToCart() {

        //加入购物车 就是写一个sku就可以了
        //TODO   这是一个商品的商品信息  用来加到购物车
        String sku;
        sku = receiveId + ":" + currentNum + ":" + currentColor + "," + currentSize;


        //原有的购物车东西内容
        String string = SpUtil.getString(getContext(), CONSTAN_PRODUCT.Cart_SKU, null);

        //如果是空的用来加上 不是空的就在这个了
        if (string != null) {
            string = string + "|" + sku;
        } else {
            string = sku;
        }
        SpUtil.putString(getContext(), CONSTAN_PRODUCT.Cart_SKU, string);

    }


}
