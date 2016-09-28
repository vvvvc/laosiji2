package com.itheima.laosiji.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by Administrator
 * Time  9/26/2016 3:25 AM
 */
public class Grid extends GridView {
//	private OnTouchInvalidPositionListener onTouchInvalidPositionListener;
	public Grid(Context context) {
		super(context);
	}

	public Grid(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Grid(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
//	public boolean onTouchEvent(MotionEvent ev) {
//		//先创建一个监听接口，一旦点击了无效区域，便实现onTouchInvalidPosition方法，返回true or false来确认是否消费了这个事件
//		if(onTouchInvalidPositionListener!=null){
//			if(!isEnabled()){
//				return isClickable()||isLongClickable();
//			}
//			int motionPosition = pointToPosition((int)ev.getX(), (int)ev.getY());
//			if(ev.getAction()==MotionEvent.ACTION_UP&&motionPosition == INVALID_POSITION){
//				super.onTouchEvent(ev);
//				return onTouchInvalidPositionListener.onTouchInvalidPosition(motionPosition);
//			}
//		}
//		return super.onTouchEvent(ev);
//	}
//
//	public void setOnTouchInvalidPositionListener(OnTouchInvalidPositionListener onTouchInvalidPositionListener) {
//		this.onTouchInvalidPositionListener = onTouchInvalidPositionListener;
//	}
//
//	public interface OnTouchInvalidPositionListener{
//		public boolean onTouchInvalidPosition(int motionEvent);
//	}
}
