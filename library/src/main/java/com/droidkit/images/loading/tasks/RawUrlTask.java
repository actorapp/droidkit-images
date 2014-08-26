package com.droidkit.images.loading.tasks;

import com.droidkit.images.util.HashUtil;

/**
 * Created by ex3ndr on 26.08.14.
 */
public class RawUrlTask extends AbsTask {
    private final String url;

    public RawUrlTask(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String getKey() {
        return "url:" + HashUtil.md5(url);
    }
}
