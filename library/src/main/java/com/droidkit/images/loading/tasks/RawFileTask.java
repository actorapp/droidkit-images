package com.droidkit.images.loading.tasks;

import com.droidkit.images.util.HashUtil;

/**
 * Created by ex3ndr on 26.08.14.
 */
public class RawFileTask extends AbsTask {
    private final String fileName;

    public RawFileTask(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String getKey() {
        return "file:" + HashUtil.md5(fileName);
    }
}
