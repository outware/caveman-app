package au.com.outware.cavemanapp.domain.repository;

import java.util.List;

import au.com.outware.caveman.data.model.Environment;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public interface EnvironmentRepository {
    void saveEnvironments(List<Environment> environments);
    List<Environment> getEnvironments();
    void saveCurrentEnvironment(String environment);
    String getCurrentEnvironment();
}
