package com.droidkit.images.loading;

import android.content.Context;
import com.droidkit.actors.ActorSystem;
import com.droidkit.actors.android.UiActorDispatcher;
import com.droidkit.images.cache.DiskCache;
import com.droidkit.images.cache.MemoryCache;
import com.droidkit.images.loading.actors.RawUrlActor;
import com.droidkit.images.loading.config.TaskResolver;
import com.droidkit.images.loading.tasks.RawUrlTask;

import java.util.HashSet;

/**
 * Created by ex3ndr on 16.08.14.
 */
public class ImageLoader {
    private ActorSystem actorSystem;

    private HashSet<ImageReceiver> receivers = new HashSet<ImageReceiver>();
    private MemoryCache memoryCache;
    private TaskResolver taskResolver;
    private DiskCache internalDiskCache;

    public ImageLoader(Context context) {
        this.actorSystem = new ActorSystem();
        this.actorSystem.addDispatcher("ui", new UiActorDispatcher(actorSystem));
        this.taskResolver = new TaskResolver(this);
        this.memoryCache = new MemoryCache();
        this.internalDiskCache = new DiskCache(context.getFilesDir().getAbsolutePath() + "dcache/");
        initDefaultResolver();
    }

    private void initDefaultResolver() {
        this.taskResolver.register(RawUrlTask.class, RawUrlActor.class);
    }

    public MemoryCache getMemoryCache() {
        return memoryCache;
    }

    public DiskCache getInternalDiskCache() {
        return internalDiskCache;
    }

    public TaskResolver getTaskResolver() {
        return taskResolver;
    }

    public ActorSystem getActorSystem() {
        return actorSystem;
    }

    public ImageReceiver createReceiver(ReceiverCallback callback) {
        ImageReceiver res = new ImageReceiver(this, callback);
        receivers.add(res);
        return res;
    }
}