package com.droidkit.images.loading.actors;

import android.graphics.Bitmap;
import com.droidkit.actors.ActorCreator;
import com.droidkit.actors.ActorRef;
import com.droidkit.actors.Props;
import com.droidkit.actors.ReflectedActor;
import com.droidkit.actors.tasks.AskCallback;
import com.droidkit.actors.tasks.AskFuture;
import com.droidkit.images.loading.ImageReceiver;
import com.droidkit.images.loading.actors.messages.CancelTask;
import com.droidkit.images.loading.actors.messages.NotifyError;
import com.droidkit.images.loading.actors.messages.NotifyImage;
import com.droidkit.images.loading.actors.messages.RequestTask;
import com.droidkit.images.loading.log.Log;

/**
 * Created by ex3ndr on 20.08.14.
 */
public class ReceiverActor extends ReflectedActor {

    public static Props<ReceiverActor> prop(final ImageReceiver receiver) {
        return Props.create(ReceiverActor.class, new ActorCreator<ReceiverActor>() {
            @Override
            public ReceiverActor create() {
                return new ReceiverActor(receiver);
            }
        });
    }

    private ImageReceiver receiver;
    private ActorRef notifier;
    private int taskId = -1;
    private AskFuture future;

    public ReceiverActor(ImageReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void preStartImpl() {
        notifier = system().actorOf(UiNotifyActor.class, "ui");
    }

    public void onReceive(RequestTask requestTask) {
        taskId = requestTask.getRequestId();

        if (requestTask.getRequest() instanceof String) {
            String url = (String) requestTask.getRequest();
            final int id = taskId;
            if (future != null) {
                future.cancel();
            }
            future = ask(HttpImageActor.load(url), new AskCallback<Bitmap>() {

                @Override
                public void onResult(Bitmap result) {
                    if (id == taskId) {
                        notifier.send(new NotifyImage(receiver.getId(), id, result));
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    if (id == taskId) {
                        notifier.send(new NotifyError(receiver.getId(), id));
                    }
                }
            });
        }
    }

    public void onReceive(CancelTask cancelTask) {
        if (future != null) {
            future.cancel();
            future = null;
        }
    }
}