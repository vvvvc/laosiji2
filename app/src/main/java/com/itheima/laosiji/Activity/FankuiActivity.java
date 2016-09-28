package com.itheima.laosiji.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.itheima.laosiji.R;

/**
 * Created by Administrator on 2016/9/23.
 */
public class FankuiActivity extends Activity implements View.OnClickListener {
    private ImageButton back;
    private Button submit;
    private EditText comment;
    private EditText contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fankui);
        intiview();
    }
    private void intiview() {
        back = (ImageButton) findViewById(R.id.ib_back);
        submit = (Button) findViewById(R.id.bt_submit);
        comment = (EditText) findViewById(R.id.et_comment);
        contact = (EditText) findViewById(R.id.et_contact);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                onBackPressed();
                break;
            case R.id.bt_submit:
                String s1 = comment.getText().toString().trim();
                String s2 = contact.getText().toString().trim();
                if (TextUtils.isEmpty(s1)) {
                    Toast.makeText(getApplicationContext(),"评论内容不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(s2)) {
                    Toast.makeText(getApplicationContext(),"联系方式不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                comment.setText("");
                contact.setText("");
                Toast.makeText(getApplicationContext(),"感谢您的反馈",Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
