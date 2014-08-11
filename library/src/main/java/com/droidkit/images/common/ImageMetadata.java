package com.droidkit.images.common;

/**
 * Created by ex3ndr on 08.08.14.
 */
public class ImageMetadata {
    private int w;
    private int h;
    private ImageFormat format;

    public ImageMetadata(int w, int h,ImageFormat format) {
        this.w = w;
        this.h = h;
        this.format = format;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public ImageFormat getFormat() {
        return format;
    }
}
