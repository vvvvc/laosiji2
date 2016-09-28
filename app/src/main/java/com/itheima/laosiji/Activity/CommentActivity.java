package com.itheima.laosiji.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.laosiji.Adapter.CommentAdapter;
import com.itheima.laosiji.Bean.MyCommentBean;
import com.itheima.laosiji.Constant.Constant;
import com.itheima.laosiji.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {



    //private String baseUrl = Constant.baseUrl;
    //private String baseUrl = "http://192.168.39.62:8080/market/product/";

    private String baseUrl = Constant.baseUrl;
    private TextView mCommentView;
    private ArrayList<MyCommentBean> al = new ArrayList<MyCommentBean>();
    private ImageView mBack;

    private ListView mLv;
    private List<MyCommentBean.CommentBean> mComment= new ArrayList<MyCommentBean.CommentBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        getProductBean();


    }

    private void initview() {


        mLv = (ListView) findViewById(R.id.lv_comment);

        //返回键
        mBack = (ImageView) findViewById(R.id.iv_back);


        //内容
        try{
            mComment = al.get(0).getComment();
        }catch(NullPointerException e){
            if(mComment==null||mComment.size()==0){

                MyCommentBean.CommentBean commentBean = new MyCommentBean.CommentBean();
                commentBean.setContent("亲,暂时没有评论哦");


                mComment.add(commentBean);
        }



        }




        CommentAdapter adapter = new CommentAdapter(this,mComment);
        mLv.setAdapter(adapter);

        mBack.setOnClickListener(this);


    }

    //该方法通过Retrofit获取服务器上的信息 将他保存为bean对象 ,并放入集合  通过不同的Pid得到不同服装的信息
    public void getProductBean() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();


        Result beanResult = retrofit.create(Result.class);


        //这里写死了 不写死 也可以  第一个代表的Pid  商品id 第二个 是第几页 第三个是每页显示几个
        Call<MyCommentBean> comment = beanResult.getComment(1, 1, 10);


        comment.enqueue(new Callback<MyCommentBean>() {
            @Override


            public void onResponse(Response<MyCommentBean> response, Retrofit retrofit) {
                MyCommentBean commentBean = response.body();

                //将获取到的bean放到al集合里面

                al.add(commentBean);
                initview();


            }

            @Override
            public void onFailure(Throwable throwable) {

                Toast.makeText(CommentActivity.this, "暂时没有数据哦", Toast.LENGTH_SHORT).show();

            }


        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();


        switch(id){
            case R.id.iv_back:
                onBackPressed();
                break;


        };

    }

    //http://localhost:8080/market/product/comment?pId=1&page=1&pageNum=10
    interface Result {

        @GET("comment")
        Call<MyCommentBean> getComment(@Query("pId") int pId, @Query("page") int page, @Query("pageNum") int pageNum);
    }
}
