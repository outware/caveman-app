package au.com.outware.cavemanapp.domain.interactor;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.List;

import javax.inject.Inject;

import au.com.outware.cavemanapp.domain.repository.EnvironmentRepository;
import au.com.outware.caveman.data.model.Environment;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class RemoveEnvironment implements UseCase<Void> {
    @VisibleForTesting
    @Inject
    EnvironmentRepository environmentRepository;

    private Environment environment;

    @Inject
    public RemoveEnvironment() { }

    public RemoveEnvironment setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
        return this;
    }

    @Override
    public Void execute() {
        List<Environment> environments = environmentRepository.getEnvironments();
        environments.remove(environment);
        environmentRepository.saveEnvironments(environments);
        return null;
    }
}
