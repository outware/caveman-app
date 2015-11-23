package au.com.outware.cavemanapp.domain.interactor;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import au.com.outware.caveman.data.model.Environment;
import au.com.outware.cavemanapp.domain.repository.EnvironmentRepository;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class RemoveEnvironmentTest {
    RemoveEnvironment removeEnvironment;
    
    @Before
    public void setup() {
        removeEnvironment = new RemoveEnvironment();
        removeEnvironment.environmentRepository = mock(EnvironmentRepository.class);
    }

    @Test
    public void whenEnvironmentNotSaved_environmentsUnchanged() {
        // Arrange
        Environment production = new Environment();
        production.setName(Environment.ENVIRONMENT_PRODUCTION);
        Environment debug = new Environment();
        debug.setName("Debug");
        List<Environment> environments = new ArrayList<>(1);
        environments.add(production);
        when(removeEnvironment.environmentRepository.getEnvironments()).thenReturn(environments);

        // Act
        removeEnvironment.setEnvironment(debug).execute();

        // Assert
        verify(removeEnvironment.environmentRepository, times(1)).getEnvironments();
        verify(removeEnvironment.environmentRepository, times(1)).saveEnvironments(anyListOf(Environment.class));
        assertThat(environments.size(), is(1));
    }

    @Test
    public void whenEnvironmentSaved_environmentRemoved() {
        // Arrange
        Environment production = new Environment();
        production.setName(Environment.ENVIRONMENT_PRODUCTION);
        Environment debug = new Environment();
        debug.setName("Debug");
        List<Environment> environments = new ArrayList<>(1);
        environments.add(production);
        environments.add(debug);
        when(removeEnvironment.environmentRepository.getEnvironments()).thenReturn(environments);

        // Act
        removeEnvironment.setEnvironment(debug).execute();

        // Assert
        verify(removeEnvironment.environmentRepository, times(1)).getEnvironments();
        verify(removeEnvironment.environmentRepository, times(1)).saveEnvironments(anyListOf(Environment.class));
        assertThat(environments.size(), is(1));
    }
}
