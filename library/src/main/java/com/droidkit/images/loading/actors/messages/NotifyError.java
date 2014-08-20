package com.droidkit.images.loading.actors.messages;

/**
 * Created by ex3ndr on 20.08.14.
 */
public class NotifyError {
    public static final int NOTIFY_ID = 1;

    private final int id;
    private final int taskId;

    public NotifyError(int id, int taskId) {
        this.id = id;
        this.taskId = taskId;
    }

    public int getId() {
        return id;
    }

    public int getTaskId() {
        return taskId;
    }
}
