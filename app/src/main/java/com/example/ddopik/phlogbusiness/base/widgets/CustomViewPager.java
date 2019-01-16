package com.example.ddopik.phlogbusiness.base.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {

    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private SwipeDirection direction = SwipeDirection.ALL;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (this.isSwipeAllowed(event)) {
//            return super.onTouchEvent(event);
//        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
//        if (this.isSwipeAllowed(event)) {
//            return super.onInterceptTouchEvent(event);
//        }
        return false;
    }

    private boolean isSwipeAllowed(MotionEvent event) {
        if (this.direction == SwipeDirection.ALL) return true;

        if (direction == SwipeDirection.NONE)//disable any swipe
            return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            initialX = event.getX();
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            try {
                float diffX = event.getX() - initialX;
                if (diffX > 0 && direction == SwipeDirection.RIGHT) {
                    // swipe from LEFT to RIGHT detected
                    return true;
                } else if (diffX < 0 && direction == SwipeDirection.LEFT) {
                    // swipe from RIGHT to LEFT detected
//                    if (onSwipeLeftListener != null && Math.abs(diffX) > 50)
//                        onSwipeLeftListener.onSwipeLeft();
                    return true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return false;
    }

    public void setAllowedSwipeDirection(SwipeDirection direction) {
        this.direction = direction;
    }

    public enum SwipeDirection {
        ALL, LEFT, RIGHT, NONE
    }

    private float initialX;

    private OnSwipeLeftListener onSwipeLeftListener;

    public void setOnSwipeLeftListener(OnSwipeLeftListener onSwipeLeftListener) {
        this.onSwipeLeftListener = onSwipeLeftListener;
    }

    public interface OnSwipeLeftListener {
        void onSwipeLeft();
    }
}
