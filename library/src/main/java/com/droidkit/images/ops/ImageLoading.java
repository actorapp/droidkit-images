package com.droidkit.images.ops;

import android.graphics.Bitmap;
import com.droidkit.images.common.ImageLoadException;
import com.droidkit.images.common.ImageMetadata;
import com.droidkit.images.common.ImageSaveException;
import com.droidkit.images.sources.FileSource;
import com.droidkit.images.sources.ImageSource;
import com.droidkit.images.util.BitmapUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ex3ndr on 09.08.14.
 */
public class ImageLoading {

    private static final int MAX_PIXELS = 1200 * 1200;
    private static final int MAX_PIXELS_HQ = 1500 * 1500;

    public static final int JPEG_QUALITY = 80;
    public static final int JPEG_QUALITY_HQ = 90;

    // Public methods

    public static Bitmap loadBitmap(String fileName) throws ImageLoadException {
        return loadBitmap(new FileSource(fileName));
    }

    public static Bitmap loadBitmap(String fileName, int scale) throws ImageLoadException {
        return loadBitmap(new FileSource(fileName), scale);
    }

    public static Bitmap loadBitmapOptimized(String fileName) throws ImageLoadException {
        return loadBitmapOptimized(fileName, MAX_PIXELS);
    }

    public static Bitmap loadBitmapOptimizedHQ(String fileName) throws ImageLoadException {
        return loadBitmapOptimized(fileName, MAX_PIXELS_HQ);
    }

    public static Bitmap loadBitmapOptimized(String fileName, int limit) throws ImageLoadException {
        return loadBitmapOptimized(new FileSource(fileName), limit);
    }

    public static byte[] save(Bitmap src) throws ImageSaveException {
        return save(src, Bitmap.CompressFormat.JPEG, JPEG_QUALITY);
    }

    public static byte[] saveHq(Bitmap src) throws ImageSaveException {
        return save(src, Bitmap.CompressFormat.JPEG, JPEG_QUALITY_HQ);
    }

    public static byte[] saveJpeg(Bitmap src, int quality) throws ImageSaveException {
        return save(src, Bitmap.CompressFormat.JPEG, quality);
    }

    public static void save(Bitmap src, String fileName) throws ImageSaveException {
        saveJpeg(src, fileName, JPEG_QUALITY);
    }

    public static void saveHq(Bitmap src, String fileName) throws ImageSaveException {
        saveJpeg(src, fileName, JPEG_QUALITY_HQ);
    }

    public static void saveJpeg(Bitmap src, String fileName, int quality) throws ImageSaveException {
        save(src, fileName, Bitmap.CompressFormat.JPEG, quality);
    }

    public static void savePng(Bitmap src, String fileName) throws ImageSaveException {
        save(src, fileName, Bitmap.CompressFormat.PNG, 100);
    }

    public static void saveBmp(Bitmap src, String fileName) throws ImageSaveException {
        try {
            BitmapUtil.save(src, fileName);
        } catch (IOException e) {
            throw new ImageSaveException(e);
        }
    }

    // Private  methods
    private static Bitmap loadBitmap(ImageSource source) throws ImageLoadException {
        return source.loadBitmap();
    }

    private static Bitmap loadBitmap(ImageSource source, int scale) throws ImageLoadException {
        return source.loadBitmap(scale);
    }

    private static Bitmap loadBitmapOptimized(ImageSource source, int limit) throws ImageLoadException {
        int scale = getScaleFactor(source.getImageMetadata(), limit);
        return loadBitmap(source, scale);
    }

    private static void save(Bitmap src, String fileName, Bitmap.CompressFormat format, int quality) throws ImageSaveException {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileName);
            src.compress(format, quality, outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new ImageSaveException(e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static byte[] save(Bitmap src, Bitmap.CompressFormat format, int quality) {
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            src.compress(format, quality, outputStream);
            return outputStream.toByteArray();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static int getScaleFactor(ImageMetadata metadata, int maxPixels) {
        int scale = 1;
        int scaledW = metadata.getW();
        int scaledH = metadata.getH();
        while (scaledW * scaledH > maxPixels) {
            scale *= 2;
            scaledH /= 2;
            scaledW /= 2;
        }
        return scale;
    }
}
