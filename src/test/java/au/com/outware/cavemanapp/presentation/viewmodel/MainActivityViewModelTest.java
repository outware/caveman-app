package au.com.outware.cavemanapp.presentation.viewmodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import au.com.outware.caveman.data.model.Environment;
import au.com.outware.cavemanapp.BuildConfig;
import au.com.outware.cavemanapp.domain.interactor.GetCurrentEnvironment;
import au.com.outware.cavemanapp.domain.interactor.GetEnvironments;
import au.com.outware.cavemanapp.domain.interactor.RemoveEnvironment;
import au.com.outware.cavemanapp.domain.interactor.SetCurrentEnvironment;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
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
public class MainActivityViewModelTest {
    MainActivityViewModel mainActivityViewModel;

    @Before
    public void setup() {
        mainActivityViewModel = new MainActivityViewModel();
        mainActivityViewModel.getCurrentEnvironment = mock(GetCurrentEnvironment.class);
        mainActivityViewModel.getEnvironments = mock(GetEnvironments.class);
        mainActivityViewModel.removeEnvironment = mock(RemoveEnvironment.class);
        mainActivityViewModel.setCurrentEnvironment = mock(SetCurrentEnvironment.class);
    }

    @Test
    public void whenRemovingCurrentEnvironment_currentEnvironmentSetToProduction() {
        // Assemble
        Environment production = new Environment();
        production.setName(Environment.ENVIRONMENT_PRODUCTION);
        Environment debug = new Environment();
        debug.setName("Debug");
        List<Environment> environments = new ArrayList<>(2);
        environments.add(production);
        environments.add(debug);
        when(mainActivityViewModel.getEnvironments.execute()).thenReturn(environments);
        when(mainActivityViewModel.getCurrentEnvironment.execute()).thenReturn(debug);
        when(mainActivityViewModel.removeEnvironment.setEnvironment(any(Environment.class))).thenReturn(mock(RemoveEnvironment.class));
        when(mainActivityViewModel.setCurrentEnvironment.setEnvironment(any(Environment.class))).thenReturn(mock(SetCurrentEnvironment.class));

        // Act
        mainActivityViewModel.removeCurrentEnvironment();

        // Assert
        verify(mainActivityViewModel.removeEnvironment.setEnvironment(any(Environment.class)), times(1)).execute();
        verify(mainActivityViewModel.setCurrentEnvironment.setEnvironment(any(Environment.class)), times(1)).execute();
    }

    @Test
    public void whenProductionEnvironment_buttonsAreDisabled() {
        // Assemble
        Environment production = new Environment();
        production.setName(Environment.ENVIRONMENT_PRODUCTION);
        when(mainActivityViewModel.getCurrentEnvironment.execute()).thenReturn(production);

        // Act
        boolean enabled = mainActivityViewModel.shouldEnableButtons();

        // Assert
        assertFalse(enabled);
    }

    @Test
    public void whenEnvironmentNotProduction_buttonsAreEnabled() {
        // Assemble
        Environment debug = new Environment();
        debug.setName("Debug");
        when(mainActivityViewModel.getCurrentEnvironment.execute()).thenReturn(debug);

        // Act
        boolean enabled = mainActivityViewModel.shouldEnableButtons();

        // Assert
        assertTrue(enabled);
    }

    @Test
    public void whenCurrentEnvironmentNotInList_selectedItemPositionIsZero() {
        // Assemble
        Environment debug = new Environment();
        debug.setName("Debug");
        Environment production = new Environment();
        production.setName(Environment.ENVIRONMENT_PRODUCTION);
        List<Environment> environments = new ArrayList<>(2);
        environments.add(production);
        when(mainActivityViewModel.getCurrentEnvironment.execute()).thenReturn(debug);
        when(mainActivityViewModel.getEnvironments.execute()).thenReturn(environments);

        // Act
        int position = mainActivityViewModel.getSelectedItemPosition();

        assertEquals(position, 0);
    }

    @Test
    public void whenCurrentEnvironmentIsInList_selectedItemPositionIsIndex() {
        // Assemble
        Environment debug = new Environment();
        debug.setName("Debug");
        Environment production = new Environment();
        production.setName(Environment.ENVIRONMENT_PRODUCTION);
        List<Environment> environments = new ArrayList<>(2);
        environments.add(production);
        environments.add(debug);
        when(mainActivityViewModel.getCurrentEnvironment.execute()).thenReturn(debug);
        when(mainActivityViewModel.getEnvironments.execute()).thenReturn(environments);

        // Act
        int position = mainActivityViewModel.getSelectedItemPosition();

        assertEquals(position, 1);
    }
}
