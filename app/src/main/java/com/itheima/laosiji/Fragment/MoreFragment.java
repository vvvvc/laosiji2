package com.itheima.laosiji.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itheima.laosiji.Activity.AboutActivity;
import com.itheima.laosiji.Activity.FankuiActivity;
import com.itheima.laosiji.Activity.HelpActivity;
import com.itheima.laosiji.Activity.HistoryAcitivity;
import com.itheima.laosiji.R;

/**
 * Created by Marlboro丶 on 2016/9/24.
 */
public class MoreFragment extends Fragment implements View.OnClickListener {

    private Context mcontext;
    private TextView history;
    private TextView help;
    private TextView fankui;
    private TextView about;
    private TextView call;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_more, null);
        intiview();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    private void intiview() {
        history = (TextView)view. findViewById(R.id.tv_history);
        help = (TextView)view. findViewById(R.id.tv_help);
        fankui = (TextView)view. findViewById(R.id.tv_fankui);
        about = (TextView)view. findViewById(R.id.tv_about);
        call = (TextView) view.findViewById(R.id.tv_call);

        history.setOnClickListener(this);
        help.setOnClickListener(this);
        fankui.setOnClickListener(this);
        about.setOnClickListener(this);
        call.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_history:
                Intent intent1 = new Intent(mcontext,HistoryAcitivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_help:
                Intent intent2 = new Intent(mcontext,HelpActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_fankui:
                Intent intent3 = new Intent(mcontext,FankuiActivity.class);
                startActivity(intent3);
                break;
            case R.id.tv_about:
                Intent intent4 = new Intent(mcontext,AboutActivity.class);
                startActivity(intent4);
                break;
            case R.id.tv_call:
                callshoper();
        }
    }

    private void callshoper() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + "40080088888"));
        startActivity(intent);

    }

}
