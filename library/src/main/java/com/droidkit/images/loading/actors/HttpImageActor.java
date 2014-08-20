package com.droidkit.images.loading.actors;

import android.graphics.Bitmap;
import com.droidkit.actors.ActorCreator;
import com.droidkit.actors.ActorSelection;
import com.droidkit.actors.Props;
import com.droidkit.actors.tasks.AskCallback;
import com.droidkit.actors.tasks.TaskActor;
import com.droidkit.images.common.ImageLoadException;
import com.droidkit.images.loading.log.Log;
import com.droidkit.images.ops.ImageLoading;
import com.droidkit.images.util.HashUtil;

/**
 * Created by ex3ndr on 20.08.14.
 */
public class HttpImageActor extends TaskActor<Bitmap> {

    public static ActorSelection load(String url) {
        return new ActorSelection(props(url), "/img_" + HashUtil.md5(url));
    }

    public static Props<HttpImageActor> props(final String url) {
        return Props.create(HttpImageActor.class, new ActorCreator<HttpImageActor>() {
            @Override
            public HttpImageActor create() {
                return new HttpImageActor(url);
            }
        });
    }

    private String url;

    public HttpImageActor(String url) {
        this.url = url;
    }

    @Override
    public void startTask() {
        Log.d("startTask");
        ask(DownloaderActor.download(url), new AskCallback<String>() {
            @Override
            public void onResult(String result) {
                Log.d("downloaded");
                onDownloaded(result);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d("error");
                error(throwable);
            }
        });
    }

    public void onDownloaded(String fileName) {
        try {
            complete(ImageLoading.loadBitmapOptimized(fileName));
            Log.d("error");
        } catch (ImageLoadException e) {
            error(e);
        }
    }
}