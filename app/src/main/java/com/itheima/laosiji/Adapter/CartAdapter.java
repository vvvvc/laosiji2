package com.itheima.laosiji.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.itheima.laosiji.Bean.MyCartBean;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xx on 2016/9/20.
 */
public class CartAdapter extends BaseAdapter {
    private static final int ADD    = 1;
    private static final int REDUCE = 2;

    public static final int ISCHECK = 1;
    public static final int NOCHECK = 2;


    public static final boolean CHOOSEALL =true ;
    private static final boolean CHOOSEALLOFF = true;
    public boolean chooseAll    =false;
    public boolean chooseAllOff    =false;





    private int click;      //增加减少的
    private int                       check  = ISCHECK;     //是否选中的  默认选中
    private List<MyCartBean.CartBean> mDatas = new ArrayList<>();
    private Context mContext;

    private TextView mName;
    private TextView mPrize;
    private String baseUrl = Constant.baseUrl;
    private ImageView mProductView;
    private Button    mAdd;
    private Button    mReduce;
    private TextView  mProductnum;
    private int prePoitionNum = 1;
    private int       prePoition;
    private int       checkPreposition;
    private onGetNum  mOnGetNum;
    private ImageView mIsCheck;
    private int mPrecheck;
    private int count=0;

    public CartAdapter(Context context, List<MyCartBean.CartBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public MyCartBean.CartBean getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.cart_item, null);
        }





        MyCartBean.CartBean mBean = mDatas.get(position);


        //final TextView mProductnum = (TextView) convertView.findViewById(R.id.tv_productnum);
        mProductnum = (TextView) convertView.findViewById(R.id.tv_productnum);
        mName = (TextView) convertView.findViewById(R.id.tv_name);
        mPrize = (TextView) convertView.findViewById(R.id.tv_prize);

        mIsCheck = (ImageView) convertView.findViewById(R.id.iv_check);

        mAdd = (Button) convertView.findViewById(R.id.bt_add);
        mReduce = (Button) convertView.findViewById(R.id.bt_reduce);

        mProductView = (ImageView) convertView.findViewById(R.id.iv_product);
        //当没有获取到数据的时候,显示 没有评论

        //获取数目
        //bean.getProdNum();
        //获取信息
        //bean.getProduct().getBuyLimit();
        //bean.getProduct().getNumber();
        //设置信息  分别是名字  数目  价格 图片


        String name = mBean.getProduct().getName();


        if (chooseAll==CHOOSEALL){
            count++;
            mIsCheck.setTag(ISCHECK);
            System.out.println(position + "mm"+count);

            //TODO 如果用parent.getchildcount()会有点问题 ;
            if(count==(mDatas.size())){
                count=0;
                chooseAll=false;
               // System.out.println("结束"+parent.getChildCount());
            }
        }

        if (chooseAllOff==CHOOSEALLOFF){

            count++;
            mIsCheck.setTag(NOCHECK);
           // System.out.println(position + "nn"+count);
            if(count==(mDatas.size())){
                count=0;
                chooseAllOff=false;

            }

        }


        mName.setText(name);

        final int mNum = mBean.getProdNum();
        int curcheck ;          //第一次进来  会是拿不到getTag  所以得try catch
            try {
               curcheck = (int) mIsCheck.getTag();
            }catch (Exception e){
               curcheck =ISCHECK;
            }
        if (curcheck == ISCHECK) {
            int i = Integer.parseInt((String) mProductnum.getText());
            mIsCheck.setImageResource(R.mipmap.ischeck);
            if (mOnGetNum != null) {
                mOnGetNum.getCheck(position,curcheck,i);
            }


        } else if(curcheck==NOCHECK) {
            mIsCheck.setImageResource(R.mipmap.nocheck);
            int i = Integer.parseInt((String) mProductnum.getText());
            if (mOnGetNum != null) {
                mOnGetNum.getCheck(position,curcheck,i);
            }
        }




            if (position == prePoition && click == ADD) {
                int i = Integer.parseInt((String) mProductnum.getText());


                mProductnum.setText(i + 1 + "");
                if (mOnGetNum != null) {


                    mOnGetNum.getNum(position, i + 1);

                }
                click = 0;//清空当前的加或者减得状态

            } else if (position == prePoition && click == REDUCE) {
                int i = Integer.parseInt((String) mProductnum.getText());

                int currentNum = i - 1;
                if (currentNum < 1) {
                    currentNum = 1;
                    Toast.makeText(mContext, "不能再少了,亲,别减了", Toast.LENGTH_SHORT).show();
                }
                mProductnum.setText(currentNum + "");

                if (mOnGetNum != null) {
                    mOnGetNum.getNum(position, currentNum);
                }
                click = 0;//清空当前的加或者减得状态
            }
            //获取商品价格
            String prize = mBean.getProduct().getPrice() + "";
            mPrize.setText(prize);

            //这是详情展示的图片  因为和上面的图片一样 所以直接使用下面的代码
            //ImageView iv = new ImageView(mContext);
            String pic = mBean.getProduct().getPic();
            Glide.with(mContext).load(baseUrl + pic).into(mProductView);

            mAdd.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    prePoition = position;
                    click = ADD;

                    notifyDataSetChanged();
                }
            });
            mReduce.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    prePoition = position;
                    click = REDUCE;
                    notifyDataSetChanged();

                }
            });


        mIsCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                       mPrecheck = (int)v.getTag();
                   }catch (Exception e){
                       mPrecheck =ISCHECK;
                   }
                    Toast.makeText(mContext, "mPrecheck:" + mPrecheck, Toast.LENGTH_SHORT).show();

                    if (mPrecheck == ISCHECK) {
                        mPrecheck = NOCHECK;
                        v.setTag(mPrecheck);
                        notifyDataSetChanged();
                    } else if (mPrecheck == NOCHECK) {
                        mPrecheck = ISCHECK;
                        v.setTag(mPrecheck);
                        notifyDataSetChanged();
                    }else{

                    }




                }
            });


            //TODO  优化再写

     /*   } catch (Exception e) {

            TextView tv = new TextView(mContext);
            tv.setText("没有数据");
            convertView = tv;

        }*/


            return convertView;
        }


        public interface onGetNum {
            public void getNum(int position, int num);

            public void getCheck(int position,int check,int num);
        }

    public void setOnGetNum(onGetNum listener) {
        mOnGetNum = listener;
    }

    public void ChooseAll(boolean choose){

        chooseAll =choose;
        notifyDataSetChanged();


    }

    public void ChooseAllOff(boolean choose){

        chooseAllOff =choose;
        notifyDataSetChanged();

    }

}











/*  商品信息  注释是因为偷懒 ,没有写更多信息
            List<MyCartBean.CartBean.ProductBean.ProductPropertyBean> propertyList = bean.getProduct().getProductProperty();
            for (int i = 0; i < propertyList.size(); i++) {
                propertyList.get(i).getV(); //获取颜色或者尺码
                //propertyList.get(i).getK(); //获取颜色或者尺码

                switch (propertyList.get(i).getK()) {
                    case "尺码":


                        break;
                    case "颜色":


                        break;

                    default:
                        break;

                }

            }*/
