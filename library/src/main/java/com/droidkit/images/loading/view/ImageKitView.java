package com.droidkit.images.loading.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.droidkit.images.cache.BitmapReference;
import com.droidkit.images.loading.ImageLoader;
import com.droidkit.images.loading.ImageReceiver;
import com.droidkit.images.loading.ReceiverCallback;
import com.droidkit.images.loading.tasks.RawUrlTask;

/**
 * Created by ex3ndr on 20.08.14.
 */
public class ImageKitView extends ImageView implements ReceiverCallback {
    private ImageReceiver receiver;

    public ImageKitView(Context context) {
        super(context);
    }

    public ImageKitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageKitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initLoader(ImageLoader loader) {
        receiver = loader.createReceiver(this);
    }

    public void requestUrl(String url) {
        receiver.request(new RawUrlTask(url));
    }

    @Override
    public void onImageLoaded(BitmapReference bitmap) {
        setImageBitmap(bitmap.getBitmap());
    }

    @Override
    public void onImageCleared() {
        setImageBitmap(null);
    }

    @Override
    public void onImageError() {
        setImageBitmap(null);
    }
}
