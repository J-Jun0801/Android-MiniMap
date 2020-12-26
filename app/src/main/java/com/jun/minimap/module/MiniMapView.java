package com.jun.minimap.module;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MiniMapView extends View {

    private Rect rect;
    private int left = 0;
    private int top = 0;
    private int right = 0;
    private int bottom = 0;

    public MiniMapView(Context context) {
        super(context);
        init();
    }

    public MiniMapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MiniMapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rect.set(left, top, right, bottom);
        // 페인트 객체 생성
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(rect, paint);
    }


    public void setBackgroundBitmap(Bitmap bitmap) {
        Drawable drawable = new BitmapDrawable(bitmap);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        }
    }

    private void init() {
        rect = new Rect();

    }

    public void invalidRect(int left, int top, int right ,int bottom) {
        this.left =left;
        this.right =right;
        this.top = top;
        this.bottom = bottom;

        invalidate();
    }

}
