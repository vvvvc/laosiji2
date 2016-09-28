package com.itheima.laosiji.View;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.itheima.laosiji.Activity.HomeActivity;

/**
 * Created by Marlboro丶 on 2016/9/24.
 */
public class WelcomeViewPager extends ViewGroup {

    private int mStartX;
    private Scroller mScroller;
    private onFinishActivity mOnFinishActivity;
    boolean isNeedFinish = false;

    public WelcomeViewPager(Context context) {
        this(context,null);
    }

    public WelcomeViewPager(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WelcomeViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) event.getX();
                int offset = mStartX - endX;
                int desX = offset + getScrollX();
                if(desX < 0){
                    scrollTo(0,0);
                }else if (desX > getWidth() * 2){
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    getContext().startActivity(intent);
                    isNeedFinish = true;
                    if (mOnFinishActivity != null) {
                        mOnFinishActivity.finishActity(true);
                    }
                }else{
                    scrollBy(offset,0);
                }
                mStartX = endX;
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                int position = (scrollX + getWidth() / 2) / getWidth();
                setCurrentItem(position);
                break;
        }
        return true;
    }

    public void setCurrentItem(int position) {
        int startX = getScrollX();
        int dx = position * getWidth() - startX;
        int duration = Math.abs(dx) * 2;
        mScroller.startScroll(startX,0,dx,0,duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int currX = mScroller.getCurrX();
            scrollTo(currX, 0);
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.measure(widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.layout(i * getWidth(),0,(i+1)*getWidth(),getHeight());
        }
    }

    public interface onFinishActivity {
        public void finishActity(boolean finishActity);
    }

    public void setOnFinishActivity(onFinishActivity onFinishActivity){
        this.mOnFinishActivity = onFinishActivity;
    }
}
