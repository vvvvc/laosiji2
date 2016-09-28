package com.itheima.laosiji.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itheima.laosiji.Bean.HotProductBean;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.R;

import java.util.ArrayList;
import java.util.List;


public class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<HotProductBean.ProductListBean> lists;
    private Context context;
    private List<Integer> heights;
    private OnItemClickListener mListener;
    public MyRecyclerAdapter(Context context, List<HotProductBean.ProductListBean> lists) {
        this.context = context;
        this.lists = lists;
        getRandomHeight(this.lists);
    }
    private void getRandomHeight(List<HotProductBean.ProductListBean> lists){//得到随机item的高度
        heights = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            if (i == 1) {
                heights.add(200);
            }else{
                heights.add(400);
            }
        }
    }
    public interface OnItemClickListener{
        void ItemClickListener(View view, int postion);
        void ItemLongClickListener(View view, int postion);
    }
    public void setOnClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotproduct,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ViewGroup.LayoutParams params =  holder.itemView.getLayoutParams();//得到item的LayoutParams布局参数
        params.height = heights.get(position);//把随机的高度赋予item布局
        holder.itemView.setLayoutParams(params);//把params设置给item布局

       // holder.mTv.setText(lists.get(position));//为控件绑定数据
        Glide.with(context).load(Constant.baseUrl + lists.get(position).getPic()).into(holder.iv);
        holder.mTv.setText(lists.get(position).getName());
        holder.mTv1.setText("会员价：" + lists.get(position).getPrice());
        holder.mTv2.setText("市场价：" + lists.get(position).getMarketPrice());
        if(mListener!=null){//如果设置了监听那么它就不为空，然后回调相应的方法
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                mListener.ItemClickListener(holder.itemView,pos);//把事件交给我们实现的接口那里处理
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                    mListener.ItemLongClickListener(holder.itemView,pos);//把事件交给我们实现的接口那里处理
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{
    TextView mTv;
    TextView mTv1;
    TextView mTv2;
    ImageView iv;
    public MyViewHolder(View itemView) {
        super(itemView);
        mTv = (TextView) itemView.findViewById(R.id.hotproduct_name);
        mTv1 = (TextView) itemView.findViewById(R.id.hotproduct_price);
        mTv2 = (TextView) itemView.findViewById(R.id.hotproduct_marketprice);
        iv = (ImageView) itemView.findViewById(R.id.hotproduct_iv);

    }
}