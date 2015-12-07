package au.com.outware.cavemanapp.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import au.com.outware.caveman.data.model.Environment;
import au.com.outware.cavemanapp.BuildConfig;
import au.com.outware.cavemanapp.domain.repository.ConfigurationRepository;
import au.com.outware.cavemanapp.domain.repository.EnvironmentRepository;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Because Environment relies on TextUtils.isEmpty()
 * SDK 21 because roboelectric doesnt support apis for a while
 *
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class GetCurrentEnvironmentTest {
    GetCurrentEnvironment getCurrentEnvironment;

    @Before
    public void setup() {
        getCurrentEnvironment = new GetCurrentEnvironment();
        getCurrentEnvironment.configurationRepository = mock(ConfigurationRepository.class);
        getCurrentEnvironment.environmentRepository = mock(EnvironmentRepository.class);
    }

    @Test
    public void whenNoCurrentEnvironment_returnProductionEnvironment() {
        // Arrange
        List<Environment> environments = new ArrayList<>(2);
        Environment debug = new Environment();
        debug.setName("Debug");
        environments.add(debug);
        Environment production = new Environment();
        production.setName(Environment.ENVIRONMENT_PRODUCTION);
        environments.add(production);
        when(getCurrentEnvironment.environmentRepository.getEnvironments()).thenReturn(environments);

        // Act
        Environment currentEnvironment = getCurrentEnvironment.execute();

        // Assert
        verify(getCurrentEnvironment.environmentRepository, times(1)).getCurrentEnvironment();
        assertTrue(currentEnvironment.isProduction());
        assertEquals(currentEnvironment, production);
    }

    @Test
    public void whenCurrentEnvironmentSet_returnCurrentEnvironment() {
        // Arrange
        final String debugName = "Debug";
        List<Environment> environments = new ArrayList<>(2);
        Environment debug = new Environment();
        debug.setName(debugName);
        environments.add(debug);
        Environment production = new Environment();
        production.setName(Environment.ENVIRONMENT_PRODUCTION);
        environments.add(production);
        when(getCurrentEnvironment.environmentRepository.getEnvironments()).thenReturn(environments);
        when(getCurrentEnvironment.environmentRepository.getCurrentEnvironment()).thenReturn(debugName);

        // Act
        Environment currentEnvironment = getCurrentEnvironment.execute();

        // Assert
        verify(getCurrentEnvironment.environmentRepository, times(1)).getCurrentEnvironment();
        assertEquals(currentEnvironment, debug);
    }
}
