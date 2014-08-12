package com.droidkit.images.libjpeg;

import android.graphics.Bitmap;

import java.io.IOException;

/**
 * Created by ex3ndr on 11.08.14.
 */
public class LibjpegNative {

    static {
        System.loadLibrary("timg");
    }

    private LibjpegNative() {

    }

    public static void decodeReuseBitmapBlend(String fileName, Bitmap dest, boolean scale) throws IOException {
        nativeDecodeBitmapBlend(fileName, dest, scale);
    }

    public static void decodeReuseBitmap(String fileName, Bitmap dest) throws IOException {
        nativeDecodeBitmap(fileName, dest);
    }

    public static void decodeReuseBitmap(byte[] src, Bitmap dest) throws IOException {
        nativeDecodeArray(src, dest);
    }

    public static void saveBitmap(Bitmap dest, int w, int h, String fileName) throws IOException {
        nativeSaveBitmap(dest, w, h, fileName);
    }

    private static native int nativeDecodeBitmapScaled(String fileName, Bitmap bitmap, int scale) throws IOException;

    private static native void nativeDecodeBitmap(String fileName, Bitmap bitmap) throws IOException;

    private static native void nativeDecodeArray(byte[] array, Bitmap bitmap) throws IOException;

    private static native void nativeDecodeBitmapBlend(String fileName, Bitmap bitmap, boolean scale) throws IOException;

    private static native void nativeSaveBitmap(Bitmap bitmap, int w, int h, String fileName) throws IOException;
}
