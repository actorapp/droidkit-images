package com.droidkit.images.loading.actors;

import android.graphics.Bitmap;
import com.droidkit.actors.ActorCreator;
import com.droidkit.actors.ActorSelection;
import com.droidkit.actors.Props;
import com.droidkit.actors.tasks.AskCallback;
import com.droidkit.actors.tasks.AskFuture;
import com.droidkit.actors.tasks.TaskActor;
import com.droidkit.images.cache.BitmapReference;
import com.droidkit.images.cache.MemoryCache;
import com.droidkit.images.common.ImageLoadException;
import com.droidkit.images.loading.tasks.RawUrlTask;
import com.droidkit.images.ops.ImageLoading;

/**
 * Created by ex3ndr on 26.08.14.
 */
public class RawUrlActor extends TaskActor<BitmapReference> {

    public static ActorSelection load(RawUrlTask task) {
        return new ActorSelection(props(task), task.getKey());
    }

    public static ActorSelection load(String url) {
        return load(new RawUrlTask(url));
    }

    public static Props<RawUrlActor> props(final RawUrlTask task) {
        return Props.create(RawUrlActor.class, new ActorCreator<RawUrlActor>() {
            @Override
            public RawUrlActor create() {
                return new RawUrlActor(task);
            }
        });
    }

    private static MemoryCache memoryCache = new MemoryCache();

    private RawUrlTask task;
    private AskFuture future;

    public RawUrlActor(RawUrlTask task) {
        this.task = task;
    }

    @Override
    public void startTask() {
        future = ask(DownloaderActor.download(task.getUrl()), new AskCallback<String>() {
            @Override
            public void onResult(String result) {
                onDownloaded(result);
            }

            @Override
            public void onError(Throwable throwable) {
                error(throwable);
            }
        });
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof BitmapReference) {
            system().actorOf(ReferenceKillerActor.killer()).send(message);
        }
        super.onReceive(message);
    }

    public void onDownloaded(String fileName) {
        Bitmap bitmap = null;
        try {
            bitmap = ImageLoading.loadBitmapOptimized(fileName);
        } catch (ImageLoadException e) {
            error(e);
            return;
        }

        BitmapReference reference = memoryCache.referenceBitmap(task.getKey(), bitmap);
        complete(reference);
        // All results supposed to be delivered to actors that works in UI thread
        // for now this is only ReceiverActor.
        // So, sending reference killer to ui actor will perform releasing references after
        // all notificaitons in UI
        self().send(reference);
    }

    @Override
    public void onTaskObsolete() {
        future.cancel();
    }
}