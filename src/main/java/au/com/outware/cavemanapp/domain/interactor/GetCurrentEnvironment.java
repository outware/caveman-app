package au.com.outware.cavemanapp.domain.interactor;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import javax.inject.Inject;

import au.com.outware.caveman.data.model.Environment;
import au.com.outware.cavemanapp.domain.repository.ConfigurationRepository;
import au.com.outware.cavemanapp.domain.repository.EnvironmentRepository;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class GetCurrentEnvironment implements UseCase<Environment> {
    @VisibleForTesting
    @Inject
    ConfigurationRepository configurationRepository;
    @VisibleForTesting
    @Inject
    EnvironmentRepository environmentRepository;

    @Inject
    public GetCurrentEnvironment() { }

    @Nullable
    public Environment execute() {
        String currentEnvironment = environmentRepository.getCurrentEnvironment();
        Environment environment = null;

        if (currentEnvironment == null) {
            // iterate and get the PRODUCTION environment if null/empty
            for (Environment e : environmentRepository.getEnvironments()) {
                if (e.isProduction()) {
                    environment = e;
                    break;
                }
            }
        } else {
            for(Environment env : environmentRepository.getEnvironments()) {
                if(env.getName().equalsIgnoreCase(currentEnvironment)) {
                    environment = env;
                    break;
                }
            }
        }

        return environment;
    }
}
