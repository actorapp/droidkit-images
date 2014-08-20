package com.droidkit.images.loading.actors;

import android.os.HandlerThread;
import com.droidkit.actors.*;
import com.droidkit.actors.dispatch.RunnableDispatcher;
import com.droidkit.actors.tasks.TaskActor;
import com.droidkit.images.loading.config.Config;
import com.droidkit.images.loading.log.Log;
import com.droidkit.images.util.HashUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.SyncHttpClient;
import org.apache.http.Header;

import java.io.File;

/**
 * Created by ex3ndr on 16.08.14.
 */
public class DownloaderActor extends TaskActor<String> {

    public static ActorSelection download(String url) {
        return new ActorSelection(prop(url), "/http_" + HashUtil.md5(url));
    }

    private static Props<DownloaderActor> prop(final String url) {
        return Props.create(DownloaderActor.class, new ActorCreator<DownloaderActor>() {
            @Override
            public DownloaderActor create() {
                return new DownloaderActor(url);
            }
        });
    }

    private RunnableDispatcher dispatcher = new RunnableDispatcher(2);

    private String url;
    private String fileName;
    private Runnable runnable;

    public DownloaderActor(String url) {
        this.url = url;
        this.fileName = Config.getContext().getFilesDir().getAbsolutePath() + "/http_" + HashUtil.md5(url) + ".tmp";
    }

    @Override
    public void startTask() {
        Log.d("startTask");
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(self(), "startDownload");
                    SyncHttpClient syncHttpClient = new SyncHttpClient();
                    syncHttpClient.get(url, new FileAsyncHttpResponseHandler(new File(fileName)) {
                        @Override
                        public void onSuccess(int i, Header[] headers, File file) {
                            Log.d(self(), "onSuccess");
                            complete(file.getAbsolutePath());
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, Throwable throwable, File file) {
                            Log.d(self(), "onFailure");
                            error(throwable);
                        }
                    });
                    Log.d(self(), "success");
                } catch (Throwable t) {
                    Log.d(self(), "error");
                    error(t);
                }
            }
        };
        dispatcher.postAction(runnable);
    }

    @Override
    public void onTaskObsolete() {
        Log.d("onTaskObsolete");
        dispatcher.removeAction(runnable);
    }
}