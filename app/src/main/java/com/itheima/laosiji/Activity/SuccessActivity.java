package com.itheima.laosiji.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.itheima.laosiji.Constant.CONSTAN_PRODUCT;
import com.itheima.laosiji.R;


//如果是限时抢购的话 会有问题 因为服务器没有专门设置限时抢购的数据返还 所以这里直接适应了本地的
//数据传递  在订单处的话使用服务器数据 两者不一样
public class SuccessActivity extends AppCompatActivity implements View.OnClickListener {

    private int      mPrize;
    private String   mOrederid;
    private TextView mViewById;
    private TextView mOrderNum;
    private TextView mOrderTotal;
    private TextView mOrderType;
    private Button   mContinue;
    private Button   mCheck;
    private TextView mShopping;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Intent intent = getIntent();

        mPrize = intent.getIntExtra(CONSTAN_PRODUCT.FINAL_PRIZE, -1);
        mOrederid = intent.getStringExtra(CONSTAN_PRODUCT.ORDERID);

        initView();


    }

    private void initView() {
        mOrderNum = (TextView) findViewById(R.id.tv_orderNum);
        mOrderTotal = (TextView) findViewById(R.id.tv_orderTotal);
        mOrderType = (TextView) findViewById(R.id.order_type);
        mShopping = (TextView) findViewById(R.id.tv_shopping);

        mContinue = (Button) findViewById(R.id.bt_continue);
        mCheck = (Button) findViewById(R.id.bt_checkorder);

        mContinue.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mOrderNum.setText(mOrederid + "");
        mOrderTotal.setText(mPrize + "");
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.bt_continue:
                onBackPressed();

                break;
            case R.id.bt_checkorder:

                //检查订单入口写在这儿


                break;
        }


    }
}
