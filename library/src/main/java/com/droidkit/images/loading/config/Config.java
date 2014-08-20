package com.droidkit.images.loading.config;

import android.content.Context;
import android.os.Handler;

/**
 * Created by ex3ndr on 20.08.14.
 */
public class Config {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Config.context = context;
    }
}
