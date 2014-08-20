package com.droidkit.images.loading.actors.messages;

/**
 * Created by ex3ndr on 20.08.14.
 */
public class CancelTask {
    private final int requestId;

    public CancelTask(int requestId) {
        this.requestId = requestId;
    }

    public int getRequestId() {
        return requestId;
    }
}
