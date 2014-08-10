package com.droidkit.images.common;

import java.io.IOException;

/**
 * Created by ex3ndr on 08.08.14.
 */
public class ImageLoadException extends IOException {
    public ImageLoadException() {
    }

    public ImageLoadException(String detailMessage) {
        super(detailMessage);
    }

    public ImageLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageLoadException(Throwable cause) {
        super(cause);
    }
}
