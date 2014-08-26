package com.droidkit.images.loading;

import android.content.Context;
import com.droidkit.actors.ActorSystem;
import com.droidkit.actors.android.UiActorDispatcher;
import com.droidkit.images.loading.config.Config;

import java.util.HashSet;

/**
 * Created by ex3ndr on 16.08.14.
 */
public class ImageLoader {
    private ActorSystem actorSystem;

    private HashSet<ImageReceiver> receivers = new HashSet<ImageReceiver>();

    public ImageLoader(Context context) {
        Config.setContext(context);
        this.actorSystem = new ActorSystem();
        this.actorSystem.addDispatcher("ui", new UiActorDispatcher(actorSystem));
    }

    ActorSystem getActorSystem() {
        return actorSystem;
    }

    public ImageReceiver createReceiver(ReceiverCallback callback) {
        ImageReceiver res = new ImageReceiver(this, callback);
        receivers.add(res);
        return res;
    }
}