package au.com.outware.cavemanapp.presentation.viewmodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import au.com.outware.caveman.data.model.Environment;
import au.com.outware.cavemanapp.BuildConfig;
import au.com.outware.cavemanapp.data.model.ConfigurationProperty;
import au.com.outware.cavemanapp.domain.interactor.CreateOrUpdateEnvironment;
import au.com.outware.cavemanapp.domain.interactor.GetConfigurationProperties;
import au.com.outware.cavemanapp.domain.interactor.GetCurrentEnvironment;
import au.com.outware.cavemanapp.domain.interactor.SetCurrentEnvironment;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Because EditEnvironmentViewModel relies on TextUtils.isEmpty()
 * SDK 21 because roboelectric doesnt support apis for a while
 *
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class EditEnvironmentViewModelTest {
    EditEnvironmentViewModel editEnvironmentViewModel;

    @Before
    public void setup() {
        editEnvironmentViewModel = new EditEnvironmentViewModel();
        editEnvironmentViewModel.createOrUpdateEnvironment = mock(CreateOrUpdateEnvironment.class);
        editEnvironmentViewModel.getConfigurationProperties = mock(GetConfigurationProperties.class);
        editEnvironmentViewModel.getCurrentEnvironment = mock(GetCurrentEnvironment.class);
        editEnvironmentViewModel.setCurrentEnvironment = mock(SetCurrentEnvironment.class);
    }

    @Test
    public void whenNewEnvironment_currentEnvironmentIsBlank() {
        // Assemble
        editEnvironmentViewModel.setIsNewEnvironment(true);

        // Act
        Environment environment = editEnvironmentViewModel.getEnvironment();

        // Assert
        verify(editEnvironmentViewModel.getCurrentEnvironment, times(0)).execute();
        assertNull(environment.getName());
    }

    @Test
    public void whenExistingEnvironment_returnsCurrentEnvironment() {
        // Assemble
        editEnvironmentViewModel.setIsNewEnvironment(false);

        // Arrange
        Environment debug = new Environment();
        debug.setName("Debug");
        when(editEnvironmentViewModel.getCurrentEnvironment.execute()).thenReturn(debug);

        // Act
        Environment environment = editEnvironmentViewModel.getEnvironment();

        // Assert
        verify(editEnvironmentViewModel.getCurrentEnvironment, times(1)).execute();
        assertEquals(environment, debug);
    }

    @Test
    public void returnsCurrentProperties() {
        // Arrange
        List<ConfigurationProperty> properties = new ArrayList<>();
        properties.add(new ConfigurationProperty());
        when(editEnvironmentViewModel.getConfigurationProperties.execute()).thenReturn(properties);

        // Act
        Collection<ConfigurationProperty> returnedProperties = editEnvironmentViewModel.getProperties();

        // Assert
        verify(editEnvironmentViewModel.getConfigurationProperties, times(1)).execute();
        assertThat(returnedProperties.size(), is(1));
        assertEquals(properties, returnedProperties);
    }

    @Test
    public void savesEnvironment() {
        // Arrange
        Environment debug = new Environment();
        debug.setName("Debug");
        editEnvironmentViewModel.currentEnvironment = debug;
        when(editEnvironmentViewModel.createOrUpdateEnvironment.setEnvironment(any(Environment.class))).thenReturn(mock(CreateOrUpdateEnvironment.class));
        when(editEnvironmentViewModel.setCurrentEnvironment.setEnvironment(any(Environment.class))).thenReturn(mock(SetCurrentEnvironment.class));

        // Act
        editEnvironmentViewModel.saveEnvironment();

        // Verify
        verify(editEnvironmentViewModel.createOrUpdateEnvironment.setEnvironment(any(Environment.class)), times(1)).execute();
        verify(editEnvironmentViewModel.setCurrentEnvironment.setEnvironment(any(Environment.class)), times(1)).execute();
    }

    @Test
    public void whenExistingEnvironment_nameIsValid() {
        // Act
        boolean nameValid = editEnvironmentViewModel.isNameValid();

        // Assert
        assertTrue(nameValid);
    }

    @Test
    public void whenNewEnvironmentHasName_nameIsValid() {
        // Arrange
        Environment debug = new Environment();
        debug.setName("Debug");
        editEnvironmentViewModel.currentEnvironment = debug;
        editEnvironmentViewModel.setIsNewEnvironment(true);

        // Act
        boolean nameValid = editEnvironmentViewModel.isNameValid();

        // Assert
        assertTrue(nameValid);
    }

    @Test
    public void whenNewEnvironmentNameIsEmpty_nameInvalid() {
        // Arrange
        editEnvironmentViewModel.currentEnvironment = new Environment();
        editEnvironmentViewModel.setIsNewEnvironment(true);

        // Act
        boolean nameValid = editEnvironmentViewModel.isNameValid();

        // Assert
        assertFalse(nameValid);
    }
}
