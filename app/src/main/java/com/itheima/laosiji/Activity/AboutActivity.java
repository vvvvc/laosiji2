package com.itheima.laosiji.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.itheima.laosiji.R;


/**
 * Created by Administrator on 2016/9/23.
 */
public class AboutActivity extends Activity implements View.OnClickListener{
    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        intiview();
    }
    private void intiview() {
        back = (ImageButton) findViewById(R.id.ib_back);
        back.setOnClickListener(this);
        }

@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
            onBackPressed();
             break;
        }
    }
}
