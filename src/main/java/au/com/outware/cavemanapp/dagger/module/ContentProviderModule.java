package au.com.outware.cavemanapp.dagger.module;

import android.content.Context;

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
public class ContentProviderModule {
    private final Context context;

    public ContentProviderModule(Context context) {
        this.context = context;
    }


    @Provides
    @Singleton
    public ConfigurationRepository provideConfigurationRepository() {
        return new ConfigurationListRepository(context);
    }


    @Provides
    @Singleton
    public EnvironmentRepository provideEnvironmentRepository() {
        return new EnvironmentSharedPrefsRepository(context);
    }
}
