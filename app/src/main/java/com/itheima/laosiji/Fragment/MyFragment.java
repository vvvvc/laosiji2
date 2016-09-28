package com.itheima.laosiji.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.LoginFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.itheima.laosiji.Activity.MyLoginActivity;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.R;

/**
 * Created by Marlboro丶 on 2016/9/24.
 */
public class MyFragment extends Fragment implements View.OnClickListener {

    private Context mConstext;
    private Button viewById;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConstext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(mConstext, R.layout.activity_my, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        viewById = (Button) getView().findViewById(R.id.bt_login1);
        //给注册设置点击事件
        viewById.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login1:
                startActivity(new Intent(mConstext, MyLoginActivity.class));
                break;
        }
    }
}
