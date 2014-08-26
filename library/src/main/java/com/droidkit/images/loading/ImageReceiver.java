package com.droidkit.images.loading;

import com.droidkit.actors.ActorRef;
import com.droidkit.actors.ActorSystem;
import com.droidkit.actors.android.UiActor;
import com.droidkit.actors.messages.PoisonPill;
import com.droidkit.images.loading.actors.ReceiverActor;
import com.droidkit.images.loading.actors.messages.TaskCancel;
import com.droidkit.images.loading.actors.messages.ImageError;
import com.droidkit.images.loading.actors.messages.ImageLoaded;
import com.droidkit.images.loading.actors.messages.TaskRequest;
import com.droidkit.images.cache.BitmapReference;
import com.droidkit.images.loading.tasks.AbsTask;
import com.droidkit.images.util.UiUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ex3ndr on 16.08.14.
 */
public class ImageReceiver {

    private static final AtomicInteger RECEIVER_ID = new AtomicInteger(1);
    private static final AtomicInteger TASK_ID = new AtomicInteger(1);

    private final int id;

    private final ActorSystem actorSystem;
    private final ReceiverCallback receiverCallback;

    private final ActorRef receiver;

    private int currentTask = -1;
    private BitmapReference reference;

    ImageReceiver(ImageLoader loader, ReceiverCallback receiverCallback) {
        this.id = RECEIVER_ID.incrementAndGet();
        this.actorSystem = loader.getActorSystem();
        this.receiver = actorSystem.actorOf(ReceiverActor.prop(this, loader), "receiver_" + id);
        this.receiverCallback = receiverCallback;
    }

    public int getId() {
        return id;
    }

    public BitmapReference getReference() {
        return reference;
    }

    public void request(AbsTask task) {
        if (!UiUtil.isMainThread()) {
            throw new RuntimeException("Operations allowed only on UI thread");
        }
        clear();
        receiver.send(new TaskRequest(currentTask, task));
    }

    public void clear() {
        if (!UiUtil.isMainThread()) {
            throw new RuntimeException("Operations allowed only on UI thread");
        }
        receiver.send(new TaskCancel(currentTask));
        currentTask = TASK_ID.getAndIncrement();
        receiverCallback.onImageCleared();
        if (reference != null) {
            reference.release();
            reference = null;
        }
    }

    public void close() {
        if (!UiUtil.isMainThread()) {
            throw new RuntimeException("Operations allowed only on UI thread");
        }
        clear();
        receiver.send(PoisonPill.INSTANCE);
    }

    // TODO: hide
    public void onImageLoaded(ImageLoaded loaded) {
        if (!UiUtil.isMainThread()) {
            throw new RuntimeException("Operations allowed only on UI thread");
        }
        if (loaded.getTaskId() != currentTask) {
            return;
        }
        reference = loaded.getReference();
        receiverCallback.onImageLoaded(reference);
    }

    // TODO: hide
    public void onImageError(ImageError error) {
        if (!UiUtil.isMainThread()) {
            throw new RuntimeException("Operations allowed only on UI thread");
        }
        if (error.getTaskId() != currentTask) {
            return;
        }
        receiverCallback.onImageError();
    }
}