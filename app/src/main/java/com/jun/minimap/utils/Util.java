package com.jun.minimap.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.RequiresApi;

public class Util {

    public static Float dpToPixel(Context context, Float dp) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }

    public static int getScreenWidth(Activity act) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Activity act) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getScreenHeight2(Activity act) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getNavigationBarHeight(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            ((Activity)context).getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }

    public static int getStatusBarHeight(Context context) {
        int	result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
