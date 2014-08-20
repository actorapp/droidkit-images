package com.droidkit.images.loading;

import android.graphics.Bitmap;

/**
 * Created by ex3ndr on 20.08.14.
 */
public interface ReceiverCallback {
    public void onImageLoaded(Bitmap bitmap);

    public void onImageCleared();

    public void onImageError();
}
