package com.droidkit.images.loading.actors.messages;

/**
 * Created by ex3ndr on 20.08.14.
 */
public class RequestTask {
    private final int requestId;
    private final Object request;

    public RequestTask(int requestId, Object request) {
        this.requestId = requestId;
        this.request = request;
    }

    public int getRequestId() {
        return requestId;
    }

    public Object getRequest() {
        return request;
    }
}
