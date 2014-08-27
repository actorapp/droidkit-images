package com.droidkit.images.loading.actors;

import com.droidkit.actors.*;
import com.droidkit.actors.dispatch.RunnableDispatcher;
import com.droidkit.images.cache.DiskCache;
import com.droidkit.images.loading.ImageLoader;
import com.droidkit.images.loading.actors.base.WorkerActor;
import com.droidkit.images.loading.log.Log;
import com.droidkit.images.util.HashUtil;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import org.apache.http.Header;

import java.io.File;

/**
 * Created by ex3ndr on 16.08.14.
 */
public class DownloaderActor extends WorkerActor<String> {

    public static ActorSelection download(String url, ImageLoader loader) {
        return new ActorSelection(prop(url, loader), "http_" + HashUtil.md5(url));
    }

    private static Props<DownloaderActor> prop(final String url, final ImageLoader loader) {
        return Props.create(DownloaderActor.class, new ActorCreator<DownloaderActor>() {
            @Override
            public DownloaderActor create() {
                return new DownloaderActor(url, loader);
            }
        });
    }

    private static RunnableDispatcher dispatcher = new RunnableDispatcher(2);

    private String url;
    private DiskCache cache;

    public DownloaderActor(String url, ImageLoader loader) {
        super(dispatcher);
        this.url = url;
        this.cache = loader.getInternalDiskCache();
    }

    @Override
    public void startTask() {
        String cachedFileName = cache.lockFile(url);
        if (cachedFileName != null) {
            complete(cachedFileName);
            return;
        }

        doStartTask();
    }

    @Override
    protected String doWork() throws Exception {
        String cachedFileName = cache.lockFile(url);
        if (cachedFileName != null) {
            return cachedFileName;
        }

        String fileName = cache.startWriteFile(url);

        SyncHttpClient syncHttpClient = new SyncHttpClient();
        syncHttpClient.get(url, new FileAsyncHttpResponseHandler(new File(fileName)) {
            @Override
            public void onSuccess(int i, Header[] headers, File file) {
                // Just do nothing
            }

            @Override
            public void onFailure(int i, Header[] headers, Throwable throwable, File file) {
                throw new RuntimeException(throwable);
            }
        });

        String resultFileName = cache.commitFile(url);
        if (resultFileName != null) {
            return resultFileName;
        }

        throw new RuntimeException("Unable to commit file");
    }
}