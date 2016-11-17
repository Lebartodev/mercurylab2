package com.lebartodev.mercuryapp2;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by Александр on 14.11.2016.
 */

public class MercApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();

    }
}
