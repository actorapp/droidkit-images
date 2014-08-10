package com.droidkit.images.libjpeg;

import android.graphics.Bitmap;
import com.droidkit.images.common.ImageLoadException;
import com.droidkit.images.common.ImageMetadata;
import com.droidkit.images.libjpeg.utils.LibjpegNative;
import com.droidkit.images.ops.ImageLoading;
import com.droidkit.images.sources.FileSource;

import java.io.IOException;

/**
 * Created by ex3ndr on 11.08.14.
 */
public class ImageLoadingEx extends ImageLoading {
    public static Bitmap loadLibJpeg(String fileName) throws ImageLoadException {
        FileSource source = new FileSource(fileName);
        ImageMetadata metadata = source.getImageMetadata();
        Bitmap res = Bitmap.createBitmap(metadata.getW(), metadata.getH(), Bitmap.Config.ARGB_8888);
        try {
            LibjpegNative.decodeReuseBitmap(fileName, res);
        } catch (IOException e) {
            throw new ImageLoadException(e);
        }
        return res;
    }
}
