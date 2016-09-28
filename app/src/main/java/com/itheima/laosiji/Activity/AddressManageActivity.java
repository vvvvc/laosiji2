package com.itheima.laosiji.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.itheima.laosiji.Adapter.AreaAdapter;
import com.itheima.laosiji.Bean.AreaBean;
import com.itheima.laosiji.R;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AddressManageActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int RESULTCODE = 9;
    private PopupWindow mPw;
    private ListView mLv_province;
    private ListView mLv_city;
    private ListView mLv_district;
    private EditText mEditText;
    private Button mButton;
    private List<AreaBean> mProvinceLists = new ArrayList<>();//省份的数据集合
    private List<AreaBean> mCityLists = new ArrayList<>();//城市的数据集合
    private List<AreaBean> mDistrictLists = new ArrayList<>();//地区的数据集合

    private AreaAdapter mCityAdapter;
    private AreaAdapter mDistrictAdapter;
    private AreaAdapter mProvinceAdapter;
    private File mDbFile;

    private int prevousProvincePosition = -1;//记录当前省份的前一个选中位置
    private int prevousCityPosition = -1;//记录当前省份的前一个选中位置
    private int prevousDistrictPosition = -1;//记录当前省份的前一个选中位置

    private String province = "";
    private String city = "";
    private String district = "";
    private Button mAddress_commit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manage);
        initView();
        initPopUpWindow();
        initProviceListView();
        initListViewListener();

    }

    private void initListViewListener() {
        mLv_province.setOnItemClickListener(this);
        mLv_city.setOnItemClickListener(this);
        mLv_district.setOnItemClickListener(this);
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.et_number);
        mButton = (Button) findViewById(R.id.bt);
        mAddress_commit = (Button) findViewById(R.id.address_commit);
        mAddress_commit.setOnClickListener(this);
        mButton.setOnClickListener(this);
    }

    private void initPopUpWindow() {

        if (mPw == null) {
            View contentView = View.inflate(AddressManageActivity.this,R.layout.item_popupwindow,null);
            mPw = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
                    .WRAP_CONTENT, true);
            mPw.setBackgroundDrawable(new ColorDrawable());
            mLv_province = (ListView) contentView.findViewById(R.id.lv_province);
            mLv_city = (ListView) contentView.findViewById(R.id.lv_city);
            mLv_district = (ListView) contentView.findViewById(R.id.lv_distant);
        }
    }

    private void initProviceListView() {
        readProvinceLists();
        if (mProvinceAdapter == null) {
            mProvinceAdapter = new AreaAdapter(AddressManageActivity.this, mProvinceLists);
            mLv_province.setAdapter(mProvinceAdapter);
        } else {
            mProvinceAdapter.notifyDataSetChanged();
        }
    }

    private void readProvinceLists() {
        mDbFile = new File(getFilesDir(),"city.s3db");
        SQLiteDatabase database = SQLiteDatabase.openDatabase(mDbFile.getAbsolutePath(), null, SQLiteDatabase
                .OPEN_READWRITE);
        Cursor cursor = database.rawQuery("select code , name from province", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String code = cursor.getString(0);
                byte[] blob = cursor.getBlob(1);
                String name = null;
                try {
                    name = new String(blob,"gbk");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                AreaBean areaBean = new AreaBean();
                areaBean.code = code;
                areaBean.name = name;
                mProvinceLists.add(areaBean);
            }
        }
        cursor.close();
        database.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                mPw.showAsDropDown(mButton);
                break;
            case R.id.address_commit:
                String address = mEditText.getText().toString().trim();
                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(this, "地址不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(AddressManageActivity.this, AddAddressActivity.class);
                intent.putExtra("province",province);
                intent.putExtra("city",city);
                intent.putExtra("district",district);
                setResult(RESULT_OK,intent);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_province:
                //先将上一个选中位置设置为未选中
                if (prevousProvincePosition != -1&&prevousProvincePosition != position) {
                    mProvinceLists.get(prevousProvincePosition).isSelected = false;
                }
                //记录当前位置为下一次移动做准备
                prevousProvincePosition = position;
//                view.setBackgroundResource(R.drawable.choose_item_selected);
                //获取条目数据，设置为选中状态，刷新界面
                AreaBean provinceBean = (AreaBean) parent.getItemAtPosition(position);
                if (!provinceBean.isSelected) {
                    provinceBean.isSelected = true;
                    mProvinceAdapter.notifyDataSetChanged();
                }

                //根据点击的省份显示对应的城市
                initCityListViewByProvice(provinceBean);
                //点击省份，必须将城市的前一个选中的位置初始化掉
                prevousCityPosition = -1;

                //让地区不显示
                if (mDistrictAdapter != null) {
                    mDistrictLists.clear();
                    mDistrictAdapter.notifyDataSetChanged();
                }

                //记录当前点击的省份的名称
                province = provinceBean.name;
                mEditText.setText(province);
                break;
            case R.id.lv_city:
                //先将上一个选中位置设置为未选中
                if (prevousCityPosition != -1&&prevousCityPosition != position) {
                    mCityLists.get(prevousCityPosition).isSelected = false;
                }
                //记录当前位置为下一次移动做准备
                prevousCityPosition = position;
//                view.setBackgroundResource(R.drawable.choose_item_selected);
                //获取条目数据，设置为选中状态，刷新界面
                AreaBean cityBean = (AreaBean) parent.getItemAtPosition(position);
                if (!cityBean.isSelected) {
                    cityBean.isSelected = true;
                    mCityAdapter.notifyDataSetChanged();
                }

                //点击城市，根据城市展示地区界面
                initDistrictListViewByCity(cityBean);

                //点击城市时，初始化地区的前一个选中位置
                prevousDistrictPosition = -1;

                //记录当前点击的省份的名称
                city = cityBean.name;
                mEditText.setText(province+" "+city);
                break;
            case R.id.lv_distant:
                //先将上一个选中位置设置为未选中
                if (prevousDistrictPosition != -1&&prevousDistrictPosition != position) {
                    mDistrictLists.get(prevousDistrictPosition).isSelected = false;
                }
                //记录当前位置为下一次移动做准备
                prevousDistrictPosition = position;
//                view.setBackgroundResource(R.drawable.choose_item_selected);
                //获取条目数据，设置为选中状态，刷新界面
                AreaBean districtBean = (AreaBean) parent.getItemAtPosition(position);
                if (!districtBean.isSelected) {
                    districtBean.isSelected = true;
                    mDistrictAdapter.notifyDataSetChanged();
                }

                //记录当前点击的省份的名称
                district = districtBean.name;
                mEditText.setText(province+" "+city+" "+district);
                break;
        }
    }

    private void initDistrictListViewByCity(AreaBean cityBean) {
        //读取地区的数据
        readDistrictListsByCity(cityBean);


        if (mDistrictAdapter == null) {
            mDistrictAdapter = new AreaAdapter(AddressManageActivity.this, mDistrictLists);
            mLv_district.setAdapter(mDistrictAdapter);
        }else{
            mDistrictAdapter.notifyDataSetChanged();
        }
    }

    private void readDistrictListsByCity(AreaBean cityBean) {
        readAreaByPcode(cityBean,mDistrictLists,"district");
    }

    //根据点击的省份显示对应的城市
    private void initCityListViewByProvice(AreaBean provinceBean) {
        //读取城市数据
        readCityListsByProvince(provinceBean);

        if (mCityAdapter == null) {
            mCityAdapter = new AreaAdapter(AddressManageActivity.this, mCityLists);
            mLv_city.setAdapter(mCityAdapter);
        } else {
            mCityAdapter.notifyDataSetChanged();
        }
    }

    //读取城市数据
    private void readCityListsByProvince(AreaBean provinceBean) {
        readAreaByPcode(provinceBean, mCityLists, "city");
    }

    //根据传递进来的集合与表名，获取对应的数据
    private void readAreaByPcode(AreaBean provinceBean, List<AreaBean> lists, String table_name) {
        lists.clear();

        //select code , name from city where pcode = '310000';
        SQLiteDatabase database = SQLiteDatabase.openDatabase(mDbFile.getAbsolutePath(), null, SQLiteDatabase
                .OPEN_READWRITE);
        Cursor cursor = database.rawQuery("select code , name from "+table_name+" where pcode = ?", new String[]{provinceBean
                .code});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String code = cursor.getString(0);
                String name = "";
                try {
                    name  = new String(cursor.getBlob(1),"gbk");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                AreaBean areaBean = new AreaBean();
                areaBean.code = code;
                areaBean.name = name;
                lists.add(areaBean);
            }
        }
        cursor.close();
        database.close();
    }


}
