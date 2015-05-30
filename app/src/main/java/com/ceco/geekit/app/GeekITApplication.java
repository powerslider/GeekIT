package com.ceco.geekit.app;

import android.app.Application;

import com.ceco.geekit.appabstract.net.WebFetcher;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 25 May 2015
 */
public class GeekITApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        WebFetcher.init(this);
    }
}
