package com.pasc.lib.widget.loaderview;

public interface DataLoadListener<MODEL> {
    void loadData();

    void refreshData();

    void retryData();

    void onSuccess(MODEL model);

    void onError();

}
