package com.shervin.questioner;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import timber.log.Timber;

public class Application extends android.app.Application {
    private static Context sContext;
    private RefWatcher refWatcher;

    @Override public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();

        // this is to find the memory leak on the app.
        refWatcher = LeakCanary.install(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    public static AssetManager assets() {
        return sContext.getAssets();
    }

    public static RefWatcher getRefWatcher(Context context) {
        Application application = (Application) context.getApplicationContext();
        return application.refWatcher;
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private static class CrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }
        }
    }
}
