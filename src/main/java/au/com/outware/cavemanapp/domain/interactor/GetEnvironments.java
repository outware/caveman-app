package au.com.outware.cavemanapp.domain.interactor;

import android.support.annotation.VisibleForTesting;

import java.util.List;

import javax.inject.Inject;

import au.com.outware.cavemanapp.domain.repository.EnvironmentRepository;
import au.com.outware.caveman.data.model.Environment;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class GetEnvironments implements UseCase<List<Environment>> {
    @VisibleForTesting
    @Inject
    EnvironmentRepository environmentRepository;

    @Inject
    public GetEnvironments() { }

    @Override
    public List<Environment> execute() {
        return environmentRepository.getEnvironments();
    }
}
