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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class GetEnvironmentsTest {
    GetEnvironments getEnvironments;

    @Before
    public void setup() {
        getEnvironments = new GetEnvironments();
        getEnvironments.environmentRepository = mock(EnvironmentRepository.class);
    }

    @Test
    public void returnValidEnvironments() {
        // Arrange
        List<Environment> environments = new ArrayList<>(2);
        Environment debug = new Environment();
        debug.setName("Debug");
        environments.add(debug);
        Environment production = new Environment();
        production.setName(Environment.ENVIRONMENT_PRODUCTION);
        environments.add(production);
        when(getEnvironments.environmentRepository.getEnvironments()).thenReturn(environments);

        // Act
        List<Environment> returnedEnvironments = getEnvironments.execute();

        // Assert
        verify(getEnvironments.environmentRepository, times(1)).getEnvironments();
        assertEquals(returnedEnvironments, environments);
        assertThat(returnedEnvironments.size(), is(2));
    }
}
