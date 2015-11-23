package au.com.outware.cavemanapp.dagger.component;

import javax.inject.Singleton;

import au.com.outware.cavemanapp.contentprovider.CavemanProvider;
import au.com.outware.cavemanapp.dagger.module.ContentProviderModule;
import au.com.outware.cavemanapp.domain.repository.ConfigurationRepository;
import au.com.outware.cavemanapp.domain.repository.EnvironmentRepository;
import dagger.Component;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
@Singleton
@Component(modules = {ContentProviderModule.class})
public interface ContentProviderComponent {
    void inject(CavemanProvider cavemanProvider);

    ConfigurationRepository getConfigurationRepository();
    EnvironmentRepository getEnvironmentRepository();
}
