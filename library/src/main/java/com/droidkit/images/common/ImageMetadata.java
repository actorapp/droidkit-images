package com.droidkit.images.common;

/**
 * Image metadata
 */
public class ImageMetadata {
    private int w;
    private int h;
    private ImageFormat format;

    /**
     * Creating of ImageMetadata
     * @param w width of image
     * @param h height of image
     * @param format format of image
     */
    public ImageMetadata(int w, int h, ImageFormat format) {
        this.w = w;
        this.h = h;
        this.format = format;
    }

    /**
     * Width of image
     *
     * @return width
     */
    public int getW() {
        return w;
    }

    /**
     * Height of image
     *
     * @return height
     */
    public int getH() {
        return h;
    }

    /**
     * Format of image
     *
     * @return format
     */
    public ImageFormat getFormat() {
        return format;
    }
}
