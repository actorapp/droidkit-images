package com.droidkit.images.loading.tasks;

import android.content.Context;
import com.droidkit.images.util.HashUtil;

/**
 * Created by ex3ndr on 26.08.14.
 */
public class RawContentTask extends AbsTask {
    private final Context context;
    private final String uri;

    public RawContentTask(Context context, String uri) {
        this.context = context;
        this.uri = uri;
    }

    public Context getContext() {
        return context;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public String getKey() {
        return "uri:" + HashUtil.md5(uri);
    }
}
