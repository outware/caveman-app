package au.com.outware.cavemanapp.dagger.component;

import javax.inject.Singleton;

import au.com.outware.cavemanapp.application.CavemanApplication;
import au.com.outware.cavemanapp.dagger.module.ApplicationModule;
import au.com.outware.cavemanapp.domain.repository.ConfigurationRepository;
import au.com.outware.cavemanapp.domain.repository.EnvironmentRepository;
import dagger.Component;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(CavemanApplication application);

    ConfigurationRepository getConfigurationRepository();
    EnvironmentRepository getEnvironmentRepository();
}
