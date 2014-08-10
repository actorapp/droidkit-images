package com.droidkit.images.sources;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Build;
import com.droidkit.images.common.ImageLoadException;
import com.droidkit.images.common.ImageMetadata;

import java.io.File;
import java.io.IOException;

/**
 * Created by ex3ndr on 08.08.14.
 */
public class FileSource extends ImageSource {

    private String fileName;

    public FileSource(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    protected ImageMetadata loadMetadata() throws ImageLoadException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        o.inTempStorage = BITMAP_TMP.get();
        BitmapFactory.decodeFile(fileName, o);
        if (o.outWidth == 0 || o.outHeight == 0) {
            throw new ImageLoadException("BitmapFactory.decodeFile: unable to load file");
        }

        int w = o.outWidth;
        int h = o.outHeight;

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(fileName);
            String exifOrientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            if (exifOrientation != null &&
                    (exifOrientation.equals("0") ||
                            exifOrientation.equals("1") ||
                            exifOrientation.equals("2") ||
                            exifOrientation.equals("3") ||
                            exifOrientation.equals("4"))) {
                w = o.outHeight;
                h = o.outWidth;
            }
        } catch (IOException e) {
            // e.printStackTrace();
        }

        return new ImageMetadata(w, h);
    }

    @Override
    public Bitmap loadBitmap() throws ImageLoadException {
        return loadBitmap(1);
    }

    @Override
    public Bitmap loadBitmap(int scale) throws ImageLoadException {
        BitmapFactory.Options o = new BitmapFactory.Options();

        o.inScaled = false;
        o.inTempStorage = BITMAP_TMP.get();
        o.inSampleSize = scale;

        if (Build.VERSION.SDK_INT >= 10) {
            o.inPreferQualityOverSpeed = true;
        }

        if (Build.VERSION.SDK_INT >= 11) {
            o.inMutable = true;
        }

        if (!new File(fileName).exists()) {
            throw new ImageLoadException("File not exists");
        }

        Bitmap res = BitmapFactory.decodeFile(fileName, o);
        if (res == null) {
            throw new ImageLoadException("BitmapFactory.decodeFile return null");
        }
        return res;
    }
}
