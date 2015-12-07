package au.com.outware.cavemanapp.domain.interactor;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import au.com.outware.cavemanapp.data.model.ConfigurationProperty;
import au.com.outware.cavemanapp.domain.repository.ConfigurationRepository;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class GetConfigurationPropertiesTest {
    GetConfigurationProperties getConfigurationProperties;

    @Before
    public void setup() {
        getConfigurationProperties = new GetConfigurationProperties();
        getConfigurationProperties.configurationRepository = mock(ConfigurationRepository.class);
    }

    @Test
    public void returnValidProperties() {
        // Arrange
        List<ConfigurationProperty> properties = new ArrayList<>();
        when(getConfigurationProperties.configurationRepository.getProperties()).thenReturn(properties);

        // Act
        Collection<ConfigurationProperty> returnedProperties = getConfigurationProperties.execute();

        // Assert
        verify(getConfigurationProperties.configurationRepository, times(1)).getProperties();
        assertEquals(properties, returnedProperties);
    }
}
