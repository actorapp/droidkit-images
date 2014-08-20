package com.droidkit.images.loading;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.droidkit.actors.ActorSystem;
import com.droidkit.actors.mailbox.MailboxesDispatcher;
import com.droidkit.images.loading.actors.UiNotifyActor;
import com.droidkit.images.loading.actors.messages.NotifyError;
import com.droidkit.images.loading.actors.messages.NotifyImage;
import com.droidkit.images.loading.config.Config;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * Created by ex3ndr on 16.08.14.
 */
public class ImageLoader {
    private ActorSystem actorSystem;
    private Context context;

    private Handler handler;
    private WeakHashMap<Integer, ImageReceiver> receivers = new WeakHashMap<Integer, ImageReceiver>();

    public ImageLoader(Context context) {
        Config.setContext(context);
        this.context = context;
        this.actorSystem = new ActorSystem();
        this.handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                ImageLoader.this.notify(msg);
            }
        };
        UiNotifyActor.init(handler);
    }

    ActorSystem getActorSystem() {
        return actorSystem;
    }

    public ImageReceiver createReceiver(ReceiverCallback callback) {
        ImageReceiver res = new ImageReceiver(this, callback);
        receivers.put(res.getId(), res);
        return res;
    }

    private void notify(Message message) {
        if (message.what == NotifyImage.NOTIFY_ID) {
            ImageReceiver receiver = receivers.get(message.arg1);
            receiver.onImageLoaded(message.arg2, (android.graphics.Bitmap) message.obj);
        } else if (message.what == NotifyError.NOTIFY_ID) {
            ImageReceiver receiver = receivers.get(message.arg1);
            receiver.onImageError(message.arg2);
        }
    }
}