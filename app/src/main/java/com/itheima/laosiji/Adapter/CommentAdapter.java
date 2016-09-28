package com.itheima.laosiji.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itheima.laosiji.Bean.MyCommentBean;
import com.itheima.laosiji.R;

import java.util.List;

/**
 * Created by xx on 2016/9/20.
 */
public class CommentAdapter extends BaseAdapter {
    private  List<MyCommentBean.CommentBean> mDatas;
    private  Context mContext;
    private TextView mCommentView;
    private TextView mName;
    private TextView mTime;
    public CommentAdapter(Context context, List<MyCommentBean.CommentBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public MyCommentBean.CommentBean getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext,R.layout.comment_item, null);//该方法会导致布局文件的第一层控件的布局参数有效
            mCommentView = (TextView) convertView.findViewById(R.id.tv_comment);
            mName = (TextView) convertView.findViewById(R.id.tv_name);
            mTime = (TextView) convertView.findViewById(R.id.tv_time);
        }

        MyCommentBean.CommentBean bean = mDatas.get(position);


        //当没有获取到数据的时候,显示 没有评论
        try {
            mCommentView.setText(bean.getContent());
            mTime.setText(bean.getTime() + "");
            mName.setText(bean.getUsername());
        }catch(Exception e){
            mCommentView.setText(bean.getContent());
        }





        return convertView;
    }
}
