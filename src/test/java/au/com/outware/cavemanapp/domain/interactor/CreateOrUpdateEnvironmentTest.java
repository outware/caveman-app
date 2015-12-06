package au.com.outware.cavemanapp.domain.interactor;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import au.com.outware.caveman.data.model.Environment;
import au.com.outware.cavemanapp.domain.repository.EnvironmentRepository;

import static junit.framework.Assert.assertEquals;
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
public class CreateOrUpdateEnvironmentTest {
    CreateOrUpdateEnvironment createOrUpdateEnvironment;

    @Before
    public void setup() {
        createOrUpdateEnvironment = new CreateOrUpdateEnvironment();
        createOrUpdateEnvironment.environmentRepository = mock(EnvironmentRepository.class);
    }

    @Test
    public void whenEnvironmentDoesntExist_createEnvironment() {
        // Arrange
        List<Environment> environments = new ArrayList<>();
        Environment environment = new Environment();
        environment.setName("test");
        when(createOrUpdateEnvironment.environmentRepository.getEnvironments()).thenReturn(environments);

        // Act
        createOrUpdateEnvironment.setEnvironment(environment).execute();

        // Assert
        verify(createOrUpdateEnvironment.environmentRepository, times(1)).saveEnvironments(anyListOf(Environment.class));
        assertThat(environments.size(), is(1));
        assertEquals(environments.get(0), environment);
    }

    @Test
    public void whenEnvironmentDoesExist_updateEnvironment() {
        // Arrange
        List<Environment> environments = new ArrayList<>();
        Environment environment = new Environment();
        environment.setName("test");
        environments.add(environment);
        when(createOrUpdateEnvironment.environmentRepository.getEnvironments()).thenReturn(environments);

        // Act
        createOrUpdateEnvironment.setEnvironment(environment).execute();

        // Assert
        verify(createOrUpdateEnvironment.environmentRepository, times(1)).saveEnvironments(anyListOf(Environment.class));
        assertThat(environments.size(), is(1));
        assertEquals(environments.get(0), environment);
    }
}
