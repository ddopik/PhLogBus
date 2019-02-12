package com.example.ddopik.phlogbusiness.base.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;
import com.example.ddopik.phlogbusiness.R;

import static android.widget.ImageView.ScaleType.CENTER_CROP;
import static android.widget.ImageView.ScaleType.CENTER_INSIDE;

public class RoundedImageView extends android.support.v7.widget.AppCompatImageView {
    private Paint mPaint;
    private Path mPath;
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private int mRadius = convertDpToPixel(10);
    private int mWidth;
    private int mHeight;
    private Drawable mDrawable;

    public RoundedImageView(Context context) {
        super(context);
        init();
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);

        mPath = new Path();
    }

    public int convertDpToPixel(int dp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        mDrawable = drawable;
        if (drawable == null) {
            return;
        }
        mBitmap = drawableToBitmap(drawable);
        mBitmap = roundCorner(mBitmap,15);
        int bDIWidth = mBitmap.getWidth();
        int bDIHeight = mBitmap.getHeight();
        //Fit to screen.
        float scale;
        if ((mHeight / (float) bDIHeight) >= (mWidth / (float) bDIWidth)) {
            scale = mHeight / (float) bDIHeight;
        } else {
            scale = mWidth / (float) bDIWidth;
        }
        float borderLeft = (mWidth - (bDIWidth * scale)) / 2;
        float borderTop = (mHeight - (bDIHeight * scale)) / 2;
        mMatrix = getImageMatrix();
        RectF drawableRect = new RectF(0, 0, bDIWidth, bDIHeight);
        RectF viewRect = new RectF(borderLeft, borderTop, (bDIWidth * scale) + borderLeft, (bDIHeight * scale) + borderTop);
        mMatrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);
        invalidate();
    }


    private Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    private  Bitmap roundCorner(Bitmap src, float round)
    {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();

        // create bitmap output
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // set canvas for painting
        Canvas canvas = new Canvas(result);
        canvas.drawARGB(0, 0, 0, 0);

        // config paint
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);

        // config rectangle for embedding
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);

        // draw rect to canvas
        canvas.drawRoundRect(rectF, round, round, paint);

        // create Xfer mode
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // draw source image to canvas
        canvas.drawBitmap(src, rect, rect, paint);

        // return final image
        return result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        if ((mDrawable != null) && (mHeight > 0) && (mWidth > 0)) {
            setImageDrawable(mDrawable);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap == null) {
            return;
        }
        canvas.drawColor(Color.TRANSPARENT);
        mPath.reset();
        mPath.moveTo(0, mRadius);
        mPath.lineTo(0, canvas.getHeight());
        mPath.lineTo(canvas.getWidth(), canvas.getHeight());
        mPath.lineTo(canvas.getWidth(), mRadius);
        mPath.quadTo(canvas.getWidth(), 0, canvas.getWidth() - mRadius, 0);
        mPath.lineTo(mRadius, 0);
        mPath.quadTo(0, 0, 0, mRadius);
        canvas.drawPath(mPath, mPaint);
        canvas.clipPath(mPath);
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
    }
}