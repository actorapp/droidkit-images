package com.droidkit.images.ops;

import android.graphics.*;

/**
 * Created by ex3ndr on 12.08.14.
 */
public class ImageEffects {
    public static Bitmap grayscale(Bitmap source) {
        Bitmap res = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(res);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        canvas.drawBitmap(source, 0, 0, paint);
        return res;
    }
}