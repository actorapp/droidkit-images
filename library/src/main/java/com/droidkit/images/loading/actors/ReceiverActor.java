package com.droidkit.images.loading.actors;

import com.droidkit.actors.*;
import com.droidkit.actors.tasks.AskCallback;
import com.droidkit.actors.tasks.AskFuture;
import com.droidkit.images.cache.BitmapReference;
import com.droidkit.images.loading.ImageLoader;
import com.droidkit.images.loading.ImageReceiver;
import com.droidkit.images.loading.actors.messages.*;
import com.droidkit.images.loading.config.TaskResolver;
import com.droidkit.images.loading.log.Log;
import com.droidkit.images.loading.tasks.RawUrlTask;

/**
 * Created by ex3ndr on 20.08.14.
 */
public final class ReceiverActor extends Actor {

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
    private TaskResolver resolver;
    private ImageReceiver receiver;

    public ReceiverActor(ImageReceiver receiver, ImageLoader imageLoader) {
        this.resolver = imageLoader.getTaskResolver();
        this.receiver = receiver;
    }

    @Override
    public void onReceive(Object message) {
        super.onReceive(message);

        if (message instanceof TaskRequest) {
            final TaskRequest taskRequest = (TaskRequest) message;
            Log.d("RequestTask #" + taskRequest.getRequest().getKey());

            taskId = taskRequest.getRequestId();
            final int currentId = taskId;
            // Cancel current work
            cancel();
            try {
                future = ask(resolver.resolveSelection(taskRequest.getRequest()), new AskCallback<BitmapReference>() {
                    @Override
                    public void onResult(BitmapReference result) {
                        Log.d("TaskResult #" + taskRequest.getRequest().getKey());
                        receiver.onImageLoaded(new ImageLoaded(currentId, result.fork()));
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("TaskError #" + taskRequest.getRequest().getKey());
                        receiver.onImageError(new ImageError(currentId, throwable));
                    }
                });
            } catch (Exception e) {
                Log.d("RequestTaskError #" + ((TaskRequest) message).getRequest().getKey());
                receiver.onImageError(new ImageError(currentId, e));
            }
        } else if (message instanceof TaskCancel) {
            TaskCancel taskCancel = (TaskCancel) message;
            if (taskId == taskCancel.getRequestId()) {
                cancel();
            }
        }
    }

    private void cancel() {
        if (future != null) {
            future.cancel();
            future = null;
        }
    }
}