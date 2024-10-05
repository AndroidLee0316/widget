package com.pasc.lib.demo.widget.dialog;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.dialog.OnDismissListener;
import com.pasc.lib.widget.dialog.loading.LoadingDialogFragment;

@Route(path = "/Demo/Dialogs/LoadingDialogFragment")
public class DemoLoadingDialogActivity extends AppCompatActivity {

    private static final String TAG = "DemoLoadingDialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_loading_dialog);

        Log.d(TAG, "onCreate");

        findViewById(R.id.noWordButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new LoadingDialogFragment.Builder()
                        .setCancelable(true)
                        .build()
                        .show(getSupportFragmentManager(), "LoadingDialogFragment");
            }
        });

        findViewById(R.id.withWordButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadingDialogFragment.Builder()
                        .setMessage("加载中")
                        .setCancelable(true)
                        .build()
                        .show(getSupportFragmentManager(), "LoadingDialogFragment2");
            }
        });

        findViewById(R.id.changeImgButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadingDialogFragment.Builder()
                        .setMessage("Hello world!")
                        .setRotateImageRes(R.drawable.ic_pets_white_48dp)
                        .setCancelable(true)
                        .setOnDismissListener(new OnDismissListener<LoadingDialogFragment>() {
                            @Override
                            public void onDismiss(LoadingDialogFragment loadingDialogFragment) {
                                Toast.makeText(DemoLoadingDialogActivity.this, "对话框消失了", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build()
                        .show(getSupportFragmentManager(), "LoadingDialogFragment");
            }
        });

        findViewById(R.id.updateContentButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final LoadingDialogFragment dialogFragment = new LoadingDialogFragment.Builder()
                        .setMessage("Hello world!")
                        .setRotateImageRes(R.drawable.ic_pets_white_48dp)
                        .setOnDismissListener(new OnDismissListener<LoadingDialogFragment>() {
                            @Override
                            public void onDismiss(LoadingDialogFragment loadingDialogFragment) {
                                Toast.makeText(DemoLoadingDialogActivity.this, "对话框消失了", Toast.LENGTH_SHORT).show();
                                v.removeCallbacks(action);
                            }
                        })
                        .setCancelable(true)
                        .build();
                dialogFragment.show(getSupportFragmentManager(), "LoadingDialogFragment");
                startTimer(v, dialogFragment);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d(TAG, "onSaveInstanceState 2");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState 1");
        outState.putInt("time", time);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState 1");
        super.onRestoreInstanceState(savedInstanceState);

        time = savedInstanceState.getInt("time", 0);

        Fragment loadingDialogFragment4 = getSupportFragmentManager().findFragmentByTag("LoadingDialogFragment4");
        if (loadingDialogFragment4 != null) {
            startTimer(findViewById(R.id.updateContentButton), (LoadingDialogFragment) loadingDialogFragment4);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        Log.d(TAG, "onRestoreInstanceState 2");
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    Runnable action;

    int time;

    void startTimer(final View view, final LoadingDialogFragment dialogFragment) {
        action = new Runnable() {
            @Override
            public void run() {
                time++;
                dialogFragment.updateMessage("Smart City " + time + "!");
                startTimer(view, dialogFragment);
            }
        };
        view.postDelayed(action, 1000);
    }
}
