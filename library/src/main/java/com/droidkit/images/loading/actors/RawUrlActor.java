package com.droidkit.images.loading.actors;

import android.graphics.Bitmap;
import com.droidkit.actors.tasks.AskCallback;
import com.droidkit.actors.tasks.AskFuture;
import com.droidkit.images.common.ImageLoadException;
import com.droidkit.images.loading.ImageLoader;
import com.droidkit.images.loading.actors.base.BasicTaskActor;
import com.droidkit.images.loading.log.Log;
import com.droidkit.images.loading.tasks.RawUrlTask;
import com.droidkit.images.ops.ImageLoading;

/**
 * Created by ex3ndr on 26.08.14.
 */
public class RawUrlActor extends BasicTaskActor<RawUrlTask> {

    private AskFuture future;

    public RawUrlActor(RawUrlTask task, ImageLoader loader) {
        super(task, loader);
    }

    @Override
    public void startTask() {
        Log.d("StartDownload");
        future = ask(DownloaderActor.download(getTask().getUrl(), getLoader()), new AskCallback<String>() {
            @Override
            public void onResult(String result) {
                Log.d("Download Success");
                onDownloaded(result);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d("Download Error");
                error(throwable);
            }
        });
    }

    public void onDownloaded(String fileName) {
        Log.d("StartDecode");
        future = ask(BitmapDecoderActor.decode(fileName, getLoader()), new AskCallback<Bitmap>() {
            @Override
            public void onResult(Bitmap bitmap) {
                Log.d("DecodeSuccess");
                completeTask(bitmap);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d("DecodeError");
                error(throwable);
            }
        });
    }

    @Override
    public void onTaskObsolete() {
        future.cancel();
    }
}