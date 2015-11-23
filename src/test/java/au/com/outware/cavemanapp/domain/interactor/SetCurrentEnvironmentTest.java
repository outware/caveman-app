package au.com.outware.cavemanapp.domain.interactor;

import org.junit.Before;
import org.junit.Test;

import au.com.outware.caveman.data.model.Environment;
import au.com.outware.cavemanapp.domain.repository.EnvironmentRepository;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class SetCurrentEnvironmentTest {
    SetCurrentEnvironment setCurrentEnvironment;

    @Before
    public void setup() {
        setCurrentEnvironment = new SetCurrentEnvironment();
        setCurrentEnvironment.environmentRepository = mock(EnvironmentRepository.class);
    }

    @Test
    public void currentEnvironmentIsSaced() {
        // Arrange
        Environment environment = new Environment();

        // Act
        setCurrentEnvironment.setEnvironment(environment).execute();

        // Assert
        verify(setCurrentEnvironment.environmentRepository, times(1)).saveCurrentEnvironment(anyString());
    }
}
