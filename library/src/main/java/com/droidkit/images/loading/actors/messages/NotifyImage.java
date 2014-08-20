package com.droidkit.images.loading.actors.messages;

import android.graphics.Bitmap;

/**
 * Created by ex3ndr on 20.08.14.
 */
public class NotifyImage {
    public static final int NOTIFY_ID = 0;

    private final int id;
    private final int taskId;
    private final Bitmap bitmap;

    public NotifyImage(int id, int taskId, Bitmap bitmap) {
        this.id = id;
        this.taskId = taskId;
        this.bitmap = bitmap;
    }

    public int getId() {
        return id;
    }

    public int getTaskId() {
        return taskId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
