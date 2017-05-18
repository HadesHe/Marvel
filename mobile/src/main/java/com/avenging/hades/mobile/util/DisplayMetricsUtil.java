package com.avenging.hades.mobile.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by Hades on 2017/5/18.
 */
public class DisplayMetricsUtil {
    public static boolean isScreenW(int widthDp) {
        DisplayMetrics displayMetrics= Resources.getSystem().getDisplayMetrics();
        float screenWidth=displayMetrics.widthPixels/displayMetrics.density;
        return screenWidth>=widthDp;
    }
}
