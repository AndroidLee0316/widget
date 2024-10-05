package com.pasc.lib.demo;

import android.app.Application;

//import com.pasc.business.workspace.WorkspaceManager;
import com.pasc.lib.base.AppProxy;
import com.pasc.lib.imageloader.PascImageLoader;
import com.pasc.lib.router.RouterManager;
import com.pasc.lib.widget.theme.SkinCompatManager;
import com.squareup.leakcanary.LeakCanary;

public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppProxy.getInstance().init(this, false);

        LeakCanary.install(this);

        SkinCompatManager.withoutActivity(this)
                .setSkinAllActivityEnable(true)
                .setSkinStatusBarColorEnable(true)
                .setSkinWindowBackgroundEnable(true)
                .loadSkin();

        initImageLoader();

        initRouter();

        initWorkspace();
    }

    private void initImageLoader() {
        PascImageLoader.getInstance().init(this, PascImageLoader.GLIDE_CORE, R.drawable.ic_common_error);
    }

    private void initRouter() {
        RouterManager.initARouter(this, true);
    }

    private void initWorkspace() {
//        WorkspaceManager workspaceManager = WorkspaceManager.getInstance().init(this);
    }
}
