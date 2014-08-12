package com.droidkit.images.ops;

import android.graphics.Bitmap;
import com.droidkit.images.common.ImageFormat;
import com.droidkit.images.common.ImageLoadException;
import com.droidkit.images.common.ImageMetadata;
import com.droidkit.images.common.ReuseResult;
import com.droidkit.images.libjpeg.LibjpegNative;
import com.droidkit.images.sources.FileSource;

import java.io.IOException;

import static com.droidkit.images.ops.ImageDrawing.*;

/**
 * Created by ex3ndr on 11.08.14.
 */
public class ImageLoadingEx extends ImageLoading {
    protected ImageLoadingEx() {
    }

    public static Bitmap loadLibJpeg(String fileName) throws ImageLoadException {
        FileSource source = new FileSource(fileName);
        ImageMetadata metadata = source.getImageMetadata();
        if (metadata.getFormat() == ImageFormat.JPEG) {
            Bitmap res = Bitmap.createBitmap(metadata.getW(), metadata.getH(), Bitmap.Config.ARGB_8888);
            try {
                LibjpegNative.decodeReuseBitmap(fileName, res);
            } catch (IOException e) {
                throw new ImageLoadException(e);
            }
            return res;
        } else {
            return loadBitmap(fileName);
        }
    }

    public static ReuseResult loadReuseExact(String fileName, Bitmap dest) throws ImageLoadException {
        FileSource source = new FileSource(fileName);
        ImageMetadata metadata = source.getImageMetadata();
        if (metadata.getFormat() != ImageFormat.JPEG) {
            return ImageLoading.loadReuseExact(fileName, dest);
        }
        if (metadata.getW() != dest.getWidth() || metadata.getH() != dest.getHeight()) {
            return new ReuseResult(ImageLoading.loadBitmap(fileName), false);
        }
        return loadReuseRegion(fileName, dest);
    }

    public static ReuseResult loadReuseRegion(String fileName, Bitmap dest) throws ImageLoadException {
        try {
            clearBitmap(dest);
            LibjpegNative.decodeReuseBitmap(fileName, dest);
            return new ReuseResult(dest, true);
        } catch (IOException e) {
            throw new ImageLoadException();
        }
    }
}