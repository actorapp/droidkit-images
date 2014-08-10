package com.droidkit.images.common;

import java.io.IOException;

/**
 * Created by ex3ndr on 10.08.14.
 */
public class ImageSaveException extends IOException {
    public ImageSaveException() {
    }

    public ImageSaveException(String detailMessage) {
        super(detailMessage);
    }

    public ImageSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageSaveException(Throwable cause) {
        super(cause);
    }
}
