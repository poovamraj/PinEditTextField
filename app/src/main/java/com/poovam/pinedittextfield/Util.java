package com.poovam.pinedittextfield;

import android.content.res.Resources;

/**
 * Created by poovam-5255 on 3/3/2018.
 */

public class Util {
    public static float dpToPx(int dp) {
        return  (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static float pxToDp(int px) {
        return  (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
