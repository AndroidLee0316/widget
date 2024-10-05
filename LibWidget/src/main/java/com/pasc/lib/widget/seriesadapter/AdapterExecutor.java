package com.pasc.lib.widget.seriesadapter;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huanglihou519 on 2018/1/29.
 */

public class AdapterExecutor implements AsyncExecutor {
    private static volatile AdapterExecutor sInstance;

    public static AdapterExecutor getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (AdapterExecutor.class) {
            if (sInstance == null) {
                sInstance = new AdapterExecutor();
            }
        }
        return sInstance;
    }

    private ExecutorService ioExecutor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void executeOnBackgroundThread(Runnable runnable) {
        ioExecutor.execute(runnable);
    }

    @Override
    public void executeMainThread(Runnable runnable) {
        mainHandler.post(runnable);
    }
}
