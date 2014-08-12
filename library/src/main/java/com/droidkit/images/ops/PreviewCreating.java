package com.droidkit.images.ops;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.droidkit.images.common.ImageLoadException;
import com.droidkit.images.common.ImageSaveException;

/**
 * Created by ex3ndr on 12.08.14.
 */
public class PreviewCreating {
    public void optimizeAvatarBeforeUpload(String fileName, String destFileName) throws ImageLoadException, ImageSaveException {
        Bitmap bitmap = ImageLoading.loadBitmapOptimizedHQ(fileName);
        ImageLoading.saveHq(bitmap, destFileName);
    }

    public void createRoundAvatar(Bitmap source, int round, Bitmap dest) {

    }

    public void createRoundedAvatar(Bitmap source, int round, Bitmap dest) {

    }

    public void createMaskedAvatar(Bitmap source, Drawable mask, Bitmap dest) {

    }
}