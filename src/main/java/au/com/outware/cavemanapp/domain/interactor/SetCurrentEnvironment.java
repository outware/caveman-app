package au.com.outware.cavemanapp.domain.interactor;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import javax.inject.Inject;

import au.com.outware.cavemanapp.domain.repository.EnvironmentRepository;
import au.com.outware.caveman.data.model.Environment;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class SetCurrentEnvironment implements UseCase<Void> {
    @VisibleForTesting
    @Inject
    EnvironmentRepository environmentRepository;

    private Environment environment;

    @Inject
    public SetCurrentEnvironment() { }

    public SetCurrentEnvironment setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
        return this;
    }

    @Override
    public Void execute() {
        environmentRepository.saveCurrentEnvironment(environment.getName());
        return null;
    }
}
