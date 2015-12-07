package au.com.outware.cavemanapp.domain.interactor;

import android.support.annotation.VisibleForTesting;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import au.com.outware.cavemanapp.data.model.ConfigurationProperty;
import au.com.outware.cavemanapp.domain.repository.ConfigurationRepository;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class GetConfigurationProperties implements UseCase<Collection<ConfigurationProperty>> {
    @VisibleForTesting
    @Inject
    ConfigurationRepository configurationRepository;

    @Inject
    public GetConfigurationProperties() { }

    @Override
    public List<ConfigurationProperty> execute() {
        return configurationRepository.getProperties();
    }
}
