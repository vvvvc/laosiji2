package com.itheima.laosiji.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.itheima.laosiji.Bean.RecommendBean;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;


public class SerachActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageView mVoice_search;
    private Button button_start;//开始按钮
    private EditText text_input;//语音识别对话框
    private BaiduASRDigitalDialog mDialog = null;
    private DialogRecognitionListener mDialogListener = null;
    private String API_KEY = "I2xMGLGkBXYjdRC2Sj7FHWr3";
    private String SECRET_KEY = "ca1b72510ceb4a7e48344f7aa83dac49";
    private EditText mSerach_et;
    private List<String> mSearchKeywords;
    private GridView mSerach_gridview;
    private SerachAdapter mAdapter;
    private TextView mSerach_cancel;
    private TextView mSerach_confirm;
    private GridView mSerachhistiry_gridview;
    private List<String> historyList = new ArrayList<>();
    private SerachHistoryAdapter mSerachHistoryAdapter;
    private TextView mHistory_clean;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach);
        getSaveData();
        initView();
        initVoiceView();
        getServerData();
    }

    private void getSaveData() {
        File file = new File(this.getFilesDir(), "serachhistory");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String readLine;
            while ((readLine = br.readLine()) != null) {
                historyList.add(readLine.toString().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getServerData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        Search search = retrofit.create(Search.class);
        Call<RecommendBean> call = search.getRecommend();
        call.enqueue(new Callback<RecommendBean>() {
            @Override
            public void onResponse(Response<RecommendBean> response, Retrofit retrofit) {
                RecommendBean recommendBean = response.body();
                mSearchKeywords = recommendBean.getSearchKeywords();
                mSerach_gridview.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }


    private void initVoiceView() {
        if (mDialog == null) {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            Bundle params = new Bundle();

            params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, API_KEY);
            params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY, SECRET_KEY);

            params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME, BaiduASRDigitalDialog.THEME_BLUE_LIGHTBG);

            mDialog = new BaiduASRDigitalDialog(SerachActivity.this, params);

            mDialogListener = new DialogRecognitionListener() {
                @Override
                public void onResults(Bundle mResults) {
                    ArrayList<String> rs = mResults != null ? mResults.getStringArrayList(RESULTS_RECOGNITION) : null;
                    if (rs != null && rs.size() > 0) {
                        mSerach_et.setText(rs.get(0));
                    }
                }
            };
            mDialog.setDialogRecognitionListener(mDialogListener);
        }
    }


    private void initView() {
        mVoice_search = (ImageView) findViewById(R.id.voice_search);
        mVoice_search.setOnClickListener(this);
        mSerach_et = (EditText) findViewById(R.id.serach_et);
        mSerach_gridview = (GridView) findViewById(R.id.serach_gridview);
        mAdapter = new SerachAdapter();
        mSerach_cancel = (TextView) findViewById(R.id.serach_cancel);
        mSerach_cancel.setOnClickListener(this);
        mSerach_confirm = (TextView) findViewById(R.id.serach_confirm);
        mSerach_confirm.setOnClickListener(this);
        mSerachhistiry_gridview = (GridView) findViewById(R.id.serachhistiry_gridview);
        mSerachhistiry_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String content = (String) parent.getItemAtPosition(position);
                new Thread(){
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);
                    }
                }.start();

                Intent intent = new Intent(SerachActivity.this, SearchRseultActivity.class);
                intent.putExtra("serachname",content);
                startActivity(intent);
            }
        });
        mSerachHistoryAdapter = new SerachHistoryAdapter();
        mSerachhistiry_gridview.setAdapter(mSerachHistoryAdapter);
        mSerach_gridview.setOnItemClickListener(this);
        mHistory_clean = (TextView) findViewById(R.id.history_clean);
        mHistory_clean.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.voice_search:
                mDialog.show();
                break;
            case R.id.serach_cancel:
                saveData();
                finish();
                break;
            case R.id.serach_confirm:
                String serachContent = mSerach_et.getText().toString().trim();
                if (TextUtils.isEmpty(serachContent)) {
                    Toast.makeText(this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (String content:historyList) {
                    if(TextUtils.equals(serachContent.trim(),content.trim())){
                        Intent intent = new Intent(SerachActivity.this, SearchRseultActivity.class);
                        intent.putExtra("serachname",serachContent);
                        startActivity(intent);
                        return;
                    }
                }
                historyList.add(serachContent);
                mSerachHistoryAdapter.notifyDataSetChanged();
                new Thread(){
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);
                    }
                }.start();
                Intent intent = new Intent(SerachActivity.this, SearchRseultActivity.class);
                intent.putExtra("serachname",serachContent);
                startActivity(intent);
                break;
            case R.id.history_clean:
                historyList.clear();
                mSerachHistoryAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void saveData() {
        File file = new File(this.getFilesDir(), "serachhistory");
        String saveDate = "";

        for (String content:historyList) {
            saveDate = saveDate + content + "\n";
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(saveDate.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String serachContent = (String) parent.getItemAtPosition(position);
        for (String content:historyList) {
            if(TextUtils.equals(serachContent.trim(),content.trim())){
                new Thread(){
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);
                    }
                }.start();

                Intent intent = new Intent(SerachActivity.this, SearchRseultActivity.class);
                intent.putExtra("serachname",serachContent);
                startActivity(intent);
                return;
            }
        }
        historyList.add(serachContent);
        mSerachHistoryAdapter.notifyDataSetChanged();
        new Thread(){
            @Override
            public void run() {
                SystemClock.sleep(1000);
            }
        }.start();

        Intent intent = new Intent(SerachActivity.this, SearchRseultActivity.class);
        intent.putExtra("serachname",serachContent);
        startActivity(intent);

    }

    //http://localhost:8080/market/search/recommend
    public interface Search {
        @GET("search/recommend")
        Call<RecommendBean> getRecommend();
    }

    class SerachAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mSearchKeywords.size();
        }

        @Override
        public String getItem(int position) {
            return mSearchKeywords.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(SerachActivity.this, R.layout.item_serach_gridview, null);
            }
            TextView recommend_product = (TextView) convertView.findViewById(R.id.recommend_product);
            recommend_product.setText(getItem(position).toString().trim());

            return convertView;
        }
    }

    class SerachHistoryAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return historyList.size();
        }

        @Override
        public String getItem(int position) {
            return historyList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(SerachActivity.this, R.layout.item_serach_gridview, null);
            }
            TextView recommend_product = (TextView) convertView.findViewById(R.id.recommend_product);
            recommend_product.setText(getItem(position).toString().trim());
            return convertView;
        }
    }

    @Override
    public void onBackPressed() {
        saveData();
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        saveData();
        super.onStop();
    }
}
