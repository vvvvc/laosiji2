package com.itheima.laosiji.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itheima.laosiji.Bean.NewBean;
import com.itheima.laosiji.R;

import java.util.List;

/**
 * Created by John on 2016/9/27.
 */
public class NewAdapter extends BaseAdapter {
    private List<NewBean> mDatas;
    private Context mContext;

    public NewAdapter(Context context, List<NewBean> datas) {
        this.mDatas = datas;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public NewBean getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.new_item, null);
            //TODO 2
            holder = new ViewHolder();
            holder.mIv_piv = (ImageView) convertView.findViewById(R.id.iv_pic);
            holder.mTv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.mTv_price = (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //要的到当前条目的位置
        NewBean bean = mDatas.get(position);
        holder.mTv_name.setText(bean.name);
        //只能在展示String型的.
        holder.mTv_price.setText(bean.marketPrice+"");
        Glide.with(mContext).load("http://192.168.39.38:8080/market/"+bean.pic).centerCrop().into(holder.mIv_piv);



        return convertView;
    }

    //TODO 1
    static class ViewHolder {
        ImageView mIv_piv;
        TextView mTv_name;
        TextView mTv_price;
    }
}
