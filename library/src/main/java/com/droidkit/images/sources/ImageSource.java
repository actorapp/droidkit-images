package com.droidkit.images.sources;

import android.graphics.Bitmap;
import com.droidkit.images.common.ImageLoadException;
import com.droidkit.images.common.ImageMetadata;
import com.droidkit.images.common.ReuseResult;

/**
 * Created by ex3ndr on 08.08.14.
 */
public abstract class ImageSource {

    protected static ThreadLocal<byte[]> BITMAP_TMP = new ThreadLocal<byte[]>() {
        @Override
        protected byte[] initialValue() {
            return new byte[16 * 1024];
        }
    };

    private ImageMetadata imageMetadata;

    protected abstract ImageMetadata loadMetadata() throws ImageLoadException;

    public ImageMetadata getImageMetadata() throws ImageLoadException {
        if (imageMetadata == null) {
            imageMetadata = loadMetadata();
        }
        return imageMetadata;
    }

    public abstract Bitmap loadBitmap() throws ImageLoadException;

    public abstract Bitmap loadBitmap(int scale) throws ImageLoadException;

    public abstract ReuseResult loadBitmap(Bitmap reuse) throws ImageLoadException;
}