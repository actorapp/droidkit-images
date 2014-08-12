package com.droidkit.images.common;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by ex3ndr on 12.08.14.
 */
public class WorkCache {
    public static ThreadLocal<byte[]> BITMAP_TMP = new ThreadLocal<byte[]>() {
        @Override
        protected byte[] initialValue() {
            return new byte[16 * 1024];
        }
    };

    public static ThreadLocal<Paint> PAINT = new ThreadLocal<Paint>() {
        @Override
        protected Paint initialValue() {
            return new Paint();
        }
    };

    public static ThreadLocal<Rect> RECT1 = new ThreadLocal<Rect>() {
        @Override
        protected Rect initialValue() {
            return new Rect();
        }
    };

    public static ThreadLocal<Rect> RECT2 = new ThreadLocal<Rect>() {
        @Override
        protected Rect initialValue() {
            return new Rect();
        }
    };

    public static ThreadLocal<RectF> RECTF1 = new ThreadLocal<RectF>() {
        @Override
        protected RectF initialValue() {
            return new RectF();
        }
    };

    public static ThreadLocal<RectF> RECTF2 = new ThreadLocal<RectF>() {
        @Override
        protected RectF initialValue() {
            return new RectF();
        }
    };
}
