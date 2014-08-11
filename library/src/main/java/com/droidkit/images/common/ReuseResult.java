package com.droidkit.images.common;

import android.graphics.Bitmap;

/**
 * Created by ex3ndr on 11.08.14.
 */
public class ReuseResult {
    private Bitmap res;
    private boolean isReused;

    public ReuseResult(Bitmap res, boolean isReused) {
        this.res = res;
        this.isReused = isReused;
    }

    public Bitmap getRes() {
        return res;
    }

    public boolean isReused() {
        return isReused;
    }
}
