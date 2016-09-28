package com.itheima.laosiji.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.laosiji.Fragment.CatagoryFragment;
import com.itheima.laosiji.Fragment.HomeFragment;
import com.itheima.laosiji.Fragment.MoreFragment;
import com.itheima.laosiji.Fragment.MyFragment;
import com.itheima.laosiji.Fragment.ShoppingFragment;
import com.itheima.laosiji.R;
import com.itheima.laosiji.View.SlideMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;



public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout mFl_Home;
    private LinearLayout mLinearLayout;
    private List<Fragment> mFragmentLists;
    private ImageView mMove_to_menu;
    private SlideMenu mSlideMenu;
    SlidingMenu slidingMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setBehindWidth(350);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.toggle();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setMenu(R.layout.menu_left);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        initView();
    }




    private void initView() {
        mFl_Home = (FrameLayout) findViewById(R.id.fl_home);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_bottom);
        TextView menu_left_address = (TextView) findViewById(R.id.menu_left_address);
        menu_left_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddressActivity.class);
                intent.setAction("HomeActivity");
                startActivity(intent);
            }
        });
        //给底部的所有图标设置点击事件，点击调到对应的fragment
        ImageView catagory_home = (ImageView) findViewById(R.id.catagory_home);
        ImageView catagory_column = (ImageView) findViewById(R.id.catagory_column);
        ImageView catagory_shopcart = (ImageView) findViewById(R.id.catagory_shopcart);
        ImageView catagory_my = (ImageView) findViewById(R.id.catagory_my);
        ImageView catagory_setting = (ImageView) findViewById(R.id.catagory_setting);
        catagory_home.setOnClickListener(this);
        catagory_column.setOnClickListener(this);
        catagory_shopcart.setOnClickListener(this);
        catagory_my.setOnClickListener(this);
        catagory_setting.setOnClickListener(this);


        mFragmentLists = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        mFragmentLists.add(homeFragment);
        mFragmentLists.add(new CatagoryFragment());
        mFragmentLists.add(new ShoppingFragment());
        mFragmentLists.add(new MyFragment());
        mFragmentLists.add(new MoreFragment());
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_home, mFragmentLists.get(0)).commit();
        homeFragment.setOnChangeFragment(new HomeFragment.onChangeFragment() {
            @Override
            public void ChangeFragment(boolean isSkip) {
                if (isSkip) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_home, mFragmentLists.get(2)).commit();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.catagory_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_home, mFragmentLists.get(0)).commit();
                break;
            case R.id.catagory_column:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_home, mFragmentLists.get(1)).commit();
                break;
            case R.id.catagory_shopcart:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_home, mFragmentLists.get(2)).commit();
                break;
            case R.id.catagory_my:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_home, mFragmentLists.get(3)).commit();
                break;
            case R.id.catagory_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_home, mFragmentLists.get(4)).commit();
                break;
        }
    }

}
