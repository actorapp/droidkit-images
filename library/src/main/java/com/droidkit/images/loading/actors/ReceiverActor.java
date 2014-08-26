package com.droidkit.images.loading.actors;

import com.droidkit.actors.ActorCreator;
import com.droidkit.actors.ActorRef;
import com.droidkit.actors.Props;
import com.droidkit.actors.ReflectedActor;
import com.droidkit.actors.tasks.AskCallback;
import com.droidkit.actors.tasks.AskFuture;
import com.droidkit.images.cache.BitmapReference;
import com.droidkit.images.loading.ImageLoader;
import com.droidkit.images.loading.ImageReceiver;
import com.droidkit.images.loading.actors.messages.*;
import com.droidkit.images.loading.tasks.RawUrlTask;

/**
 * Created by ex3ndr on 20.08.14.
 */
public final class ReceiverActor extends ReflectedActor {

    public static Props<ReceiverActor> prop(final ImageReceiver receiver, final ImageLoader imageLoader) {
        return Props.create(ReceiverActor.class, new ActorCreator<ReceiverActor>() {
            @Override
            public ReceiverActor create() {
                return new ReceiverActor(receiver, imageLoader);
            }
        }).changeDispatcher("ui");
    }

    private int taskId = -1;
    private AskFuture future;
    private ImageLoader imageLoader;
    private ImageReceiver receiver;

    public ReceiverActor(ImageReceiver receiver, ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        this.receiver = receiver;
    }

    private void cancel() {
        if (future != null) {
            future.cancel();
            future = null;
        }
    }

    public void onReceive(TaskRequest taskRequest) {
        taskId = taskRequest.getRequestId();

        if (taskRequest.getRequest() instanceof RawUrlTask) {
            RawUrlTask url = (RawUrlTask) taskRequest.getRequest();
            final int id = taskId;
            cancel();
            future = ask(RawUrlActor.load(url), new AskCallback<BitmapReference>() {

                @Override
                public void onResult(BitmapReference result) {
                    receiver.onImageLoaded(new ImageLoaded(id, result.fork()));
                }

                @Override
                public void onError(Throwable throwable) {
                    receiver.onImageError(new ImageError(id, throwable));
                }
            });
        }
    }

    public void onReceive(TaskCancel taskCancel) {
        if (taskId == taskCancel.getRequestId()) {
            cancel();
        }
    }
}