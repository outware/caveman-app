package au.com.outware.cavemanapp.domain.interactor;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.List;

import javax.inject.Inject;

import au.com.outware.caveman.data.model.Environment;
import au.com.outware.cavemanapp.domain.repository.EnvironmentRepository;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class CreateOrUpdateEnvironment implements UseCase<Void> {
    @VisibleForTesting
    @Inject
    EnvironmentRepository environmentRepository;

    private Environment environment;

    @Inject
    public CreateOrUpdateEnvironment() { }

    public CreateOrUpdateEnvironment setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
        return this;
    }

    @Override
    public Void execute() {
        List<Environment> environments = environmentRepository.getEnvironments();
        int index = environments.indexOf(environment);
        if(index == -1) {
            environments.add(environment);
        } else {
            environments.remove(index);
            environments.add(index, environment);
        }
        environmentRepository.saveEnvironments(environments);

        return null;
    }
}
