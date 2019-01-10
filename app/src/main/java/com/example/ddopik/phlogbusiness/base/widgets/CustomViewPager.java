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

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                initialX = ev.getX();
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                boolean right = initialX - ev.getX() < 0;
//                if (right)
//                    return super.onInterceptTouchEvent(ev);
//                else if (initialX - ev.getX() > 100 && !hit) {
//                    hit = true;
//                    return supplier.shouldSwipe();
//                } else
//                    return false;
//            case MotionEvent.ACTION_UP:
//                hit = false;
//                return true;
//            default:
//                return false;
//        }
//    }

    private boolean canSwipeLeft;

    public void setCanSwipeLeft(boolean canSwipeLeft) {
        this.canSwipeLeft = canSwipeLeft;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = ev.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                boolean right = initialX - ev.getX() < 0;
                return right ? super.onTouchEvent(ev) : canSwipeLeft && super.onTouchEvent(ev);
            default:
                return super.onTouchEvent(ev);
        }
    }

    private float initialX;

    private Supplier supplier;

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public interface Supplier {
        boolean shouldSwipe();
    }
}
