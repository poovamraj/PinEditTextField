package com.poovam.pinedittextfield;

import android.content.res.Resources;

/**
 * Created by poovam-5255 on 3/3/2018.
 *
 * Util class to contain helper methods
 */

public class Util {
    public static float dpToPx(float dp) {
        return  (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
