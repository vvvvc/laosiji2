package com.itheima.laosiji.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Marlboro丶 on 2016/9/24.
 */
public class SlideMenu extends ViewGroup {

    private View mMenuView;
    private View mHomeView;
    private Scroller mScroller;
    private int mStartX;
    private View mMColumnCatagory;

    public SlideMenu(Context context) {
        this(context,null);
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
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
                System.out.println(event.getX());
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMenuView = getChildAt(0);
        mMenuView.measure(mMenuView.getLayoutParams().width, heightMeasureSpec);
        mHomeView = getChildAt(1);
        mHomeView.measure(widthMeasureSpec,heightMeasureSpec);
        mMColumnCatagory = getChildAt(2);
        mMColumnCatagory.measure(widthMeasureSpec,mMColumnCatagory.getLayoutParams().height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mHomeView.layout(l,t,r,b);
        mMenuView.layout(-mMenuView.getMeasuredWidth(),t,0,b);
        mMColumnCatagory.layout(l,getHeight() - mMColumnCatagory.getMeasuredHeight(),r,b);
    }



    public void showView() {
        //scrollTo(-mMenuView.getMeasuredWidth(),0);
        int startX = getScrollX();
        int dx = -mMenuView.getMeasuredWidth() - startX;
        int duration = Math.abs(dx) * 2;
        mScroller.startScroll(startX,0,dx,0,duration);
        invalidate();
    }

    public void hideView() {
        int startX = getScrollX();
        int dx = 0 - startX;
        int duration = Math.abs(dx) * 3;
        mScroller.startScroll(startX,0,dx,0,duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int currX = mScroller.getCurrX();
            scrollTo(currX,0);
            invalidate();
        }
    }
}
