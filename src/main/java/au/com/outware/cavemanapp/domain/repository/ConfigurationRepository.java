package au.com.outware.cavemanapp.domain.repository;

import java.util.List;

import au.com.outware.cavemanapp.data.model.ConfigurationProperty;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public interface ConfigurationRepository {
    List<ConfigurationProperty> getProperties();
}
