package au.com.outware.cavemanapp.dagger.module;

import android.app.Application;

import javax.inject.Singleton;

import au.com.outware.cavemanapp.data.repository.ConfigurationListRepository;
import au.com.outware.cavemanapp.data.repository.EnvironmentSharedPrefsRepository;
import au.com.outware.cavemanapp.domain.repository.ConfigurationRepository;
import au.com.outware.cavemanapp.domain.repository.EnvironmentRepository;
import dagger.Module;
import dagger.Provides;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public ConfigurationRepository provideConfigurationRepository() {
        return new ConfigurationListRepository(application);
    }

    @Provides
    @Singleton
    public EnvironmentRepository provideEnvironmentRepository() {
        return new EnvironmentSharedPrefsRepository(application);
    }
}
