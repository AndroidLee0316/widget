package com.pasc.lib.widget.seriesadapter;

/**
 * Created by huanglihou519 on 2018/1/29.
 */

public interface AsyncExecutor {
    void executeOnBackgroundThread(Runnable runnable);

    void executeMainThread(Runnable runnable);
}
