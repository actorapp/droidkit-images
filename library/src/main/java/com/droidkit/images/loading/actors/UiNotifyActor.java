package com.droidkit.images.loading.actors;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.droidkit.actors.*;
import com.droidkit.images.loading.actors.messages.NotifyError;
import com.droidkit.images.loading.actors.messages.NotifyImage;

/**
 * Created by ex3ndr on 20.08.14.
 */
public class UiNotifyActor extends ReflectedActor {

    private static Handler handler;

    public static void init(Handler h) {
        handler = h;
    }

    public void onReceive(Runnable message) {
        handler.post(message);
    }

    public void onReceive(NotifyImage notifyImage) {
        Message message = handler.obtainMessage(NotifyImage.NOTIFY_ID, notifyImage.getId(), notifyImage.getTaskId());
        message.obj = notifyImage.getBitmap();
        handler.sendMessage(message);
    }

    public void onReceive(NotifyError notifyError) {
        Message message = handler.obtainMessage(NotifyError.NOTIFY_ID, notifyError.getId(), notifyError.getTaskId());
        handler.sendMessage(message);
    }
}
