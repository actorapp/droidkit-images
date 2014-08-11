package com.droidkit.images.ops;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Created by ex3ndr on 11.08.14.
 */
public class ImageDrawing {

    /**
     * Default clear color (Transparent)
     */
    public static final int CLEAR_COLOR = Color.TRANSPARENT;

    /**
     * Clearing bitmap with transparent color (Transparent)
     *
     * @param bitmap bitmap for clearing
     */
    public static void clearBitmap(Bitmap bitmap) {
        clearBitmap(bitmap, CLEAR_COLOR);
    }

    /**
     * Clearing bitmap with transparent color
     *
     * @param bitmap bitmap for clearing
     */
    public static void clearBitmap(Bitmap bitmap, int color) {
        bitmap.eraseColor(color);
    }

    /**
     * Drawing bitmap over dest bitmap with clearing last one before drawing
     *
     * @param src  source bitmap
     * @param dest destination bitmap
     */
    public static void drawTo(Bitmap src, Bitmap dest) {
        drawTo(src, dest, CLEAR_COLOR);
    }

    /**
     * Drawing bitmap over dest bitmap with clearing last one before drawing
     *
     * @param src   source bitmap
     * @param dest  destination bitmap
     * @param color clear color
     */
    public static void drawTo(Bitmap src, Bitmap dest, int color) {
        clearBitmap(src, color);
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(src, 0, 0, null);
        canvas.setBitmap(null);
    }
}