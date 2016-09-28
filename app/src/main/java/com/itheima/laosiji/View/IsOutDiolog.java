package com.itheima.laosiji.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.itheima.laosiji.R;

/**
 * Created by Administrator on 2016/9/28.
 */
public class IsOutDiolog extends Dialog implements View.OnClickListener {
    public IsOutDiolog(Context context) {
        super(context, R.style.isout_dialog_style);
        Window window = getWindow();
        WindowManager.LayoutParams layoutparams = window.getAttributes();
        layoutparams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutparams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.isout_dialog);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        intiview();
    }

    private void intiview() {
        RelativeLayout rl_out = (RelativeLayout) findViewById(R.id.rl_out);
        RelativeLayout rl_cancle = (RelativeLayout) findViewById(R.id.rl_cancle);
        rl_cancle.setOnClickListener(this);
        rl_out.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_cancle:
                dismiss();
                break;
        }
    }
}
