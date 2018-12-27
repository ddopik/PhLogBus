package com.example.ddopik.phlogbusiness.base.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.ddopik.phlogbusiness.R;


/**
 * Created by abdalla_maged on 10/18/2018.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    public CustomTextView(Context context)
    { super(context); setFont(context); }

    public CustomTextView(Context context,AttributeSet set)
    { super(context,set); setFont(context); }

    public CustomTextView(Context context,AttributeSet set,int defaultStyle)
    { super(context,set,defaultStyle); setFont(context); }

    private void setFont(Context context) {

//        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(),"font/arial_rounded_font.ttf");
        Typeface typeface= ResourcesCompat.getFont(context, R.font.arial_rounded_font);
        setTypeface(typeface); //function used to set font

    }
}

