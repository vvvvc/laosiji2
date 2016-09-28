package com.itheima.laosiji.Activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.itheima.laosiji.R;
import com.itheima.laosiji.View.WelcomeViewPager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private WelcomeViewPager mWelcome_viewpager;
    private int[] imgSrcId = {R.mipmap.guide_1,R.mipmap.guide_2,R.mipmap.guide_3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        try {
            copyAddressDb();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyAddressDb() throws IOException {
        File file = new File(getFilesDir(), "city.s3db");
        AssetManager assets = getAssets();
        InputStream is = assets.open("city.s3db");
        FileOutputStream fos = new FileOutputStream(file);
        int len = -1;
        byte[] buffer = new byte[1024];
        while ((len = is.read(buffer)) != -1) {
            fos.write(buffer,0,len);
        }
        is.read();
        fos.close();
    }

    private void initView() {
        mWelcome_viewpager = (WelcomeViewPager) findViewById(R.id.welcome_viewpager);
        for (int i = 0; i < imgSrcId.length; i++) {
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setBackgroundResource(imgSrcId[i]);
            mWelcome_viewpager.addView(imageView);
        }

        mWelcome_viewpager.setOnFinishActivity(new WelcomeViewPager.onFinishActivity() {
            @Override
            public void finishActity(boolean finishActity) {
                if (finishActity) {
                    finish();
                }
            }
        });
    }


}
