package au.com.outware.cavemanapp.application;

import android.app.Application;

import au.com.outware.cavemanapp.dagger.component.ApplicationComponent;
import au.com.outware.cavemanapp.dagger.component.DaggerApplicationComponent;
import au.com.outware.cavemanapp.dagger.module.ApplicationModule;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class CavemanApplication extends Application {
    private static ApplicationComponent applicationComponent;

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }
}
