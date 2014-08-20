package com.droidkit.images.loading;

import android.graphics.Bitmap;
import com.droidkit.actors.ActorRef;
import com.droidkit.actors.ActorSystem;
import com.droidkit.images.loading.actors.ReceiverActor;
import com.droidkit.images.loading.actors.messages.RequestTask;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ex3ndr on 16.08.14.
 */
public class ImageReceiver {

    private static final AtomicInteger NEXT_ID = new AtomicInteger(1);
    private static final AtomicInteger TASK_ID = new AtomicInteger(1);

    private final int id;
    private final ActorSystem actorSystem;
    private final ReceiverCallback receiverCallback;
    private final ActorRef receiver;
    private int currentTask = -1;

    ImageReceiver(ImageLoader loader, ReceiverCallback receiverCallback) {
        this.id = NEXT_ID.getAndIncrement();
        this.actorSystem = loader.getActorSystem();
        this.receiverCallback = receiverCallback;
        this.receiver = actorSystem.actorOf(ReceiverActor.prop(this), "/receiver_" + id);
    }

    void onImageLoaded(int taskId, Bitmap bitmap) {
        if (taskId != currentTask) {
            return;
        }
        receiverCallback.onImageLoaded(bitmap);
    }

    void onImageError(int taskId) {
        if (taskId != currentTask) {
            return;
        }
        receiverCallback.onImageError();
    }

    public int getId() {
        return id;
    }

    public void requestUrl(String url) {
        clear();
        receiver.send(new RequestTask(currentTask, url));
    }

    public void clear() {
        currentTask = TASK_ID.getAndIncrement();
        receiverCallback.onImageCleared();
    }
}