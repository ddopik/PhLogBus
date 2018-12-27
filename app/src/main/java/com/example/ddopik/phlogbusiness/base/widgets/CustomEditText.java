package com.example.ddopik.phlogbusiness.base.widgets;//package com.spade.nrc.ui.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;

import com.example.ddopik.phlogbusiness.R;


/**
 * Created by abdalla_maged on 10/18/2018.
 */

public class CustomEditText extends android.support.v7.widget.AppCompatEditText {

    public CustomEditText(Context context) {
        super(context);
        this.setTextColor(ContextCompat.getColor(context, R.color.white));
    }
//
//    public CustomEditText(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        this.setTextColor(ContextCompat.getColor(context, R.color.white));
//        this.setHintTextColor(ContextCompat.getColor(context, R.color.white));
//        this.setGravity(Gravity.START);
//        this.setPadding(context.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin),
//                context.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin), context.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin), context.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin));
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText);
//        int fontStyle = typedArray.getInteger(R.styleable.CustomEditText_editTextTypeFace, 0);
//        int fontNameStringID = 0;
//        switch (fontStyle) {
//            case 0:
//                fontNameStringID = R.string.;
//                break;
//            case 1:
//                fontNameStringID = R.string.gotham_semi_bold;
//                break;
//            case 2:
//                fontNameStringID = R.string.gotham_bold;
//                break;
//        }
//
//        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + getResources().getString(fontNameStringID));
//        setTypeface(typeface);
//        typedArray.recycle();
//    }
}
