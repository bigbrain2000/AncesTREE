package com.upt.weatherBeacon.ui.utilities;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenUtils {

    public static int getScreenHeightInDp(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        float density = context.getResources().getDisplayMetrics().density;
        int screenHeightInPixels = displayMetrics.heightPixels;
        return (int) (screenHeightInPixels / density);
    }

    public static int getScreenHeightInPixels(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return (int) displayMetrics.heightPixels;
    }
}

