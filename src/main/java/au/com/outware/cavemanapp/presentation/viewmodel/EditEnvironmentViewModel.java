package au.com.outware.cavemanapp.presentation.viewmodel;

import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;

import java.util.List;

import javax.inject.Inject;

import au.com.outware.caveman.data.model.Environment;
import au.com.outware.cavemanapp.data.model.ConfigurationProperty;
import au.com.outware.cavemanapp.domain.interactor.CreateOrUpdateEnvironment;
import au.com.outware.cavemanapp.domain.interactor.GetConfigurationProperties;
import au.com.outware.cavemanapp.domain.interactor.GetCurrentEnvironment;
import au.com.outware.cavemanapp.domain.interactor.SetCurrentEnvironment;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class EditEnvironmentViewModel {
    @VisibleForTesting
    @Inject
    GetCurrentEnvironment getCurrentEnvironment;
    @VisibleForTesting
    @Inject
    SetCurrentEnvironment setCurrentEnvironment;
    @VisibleForTesting
    @Inject
    GetConfigurationProperties getConfigurationProperties;
    @VisibleForTesting
    @Inject
    CreateOrUpdateEnvironment createOrUpdateEnvironment;

    @VisibleForTesting
    Environment currentEnvironment;

    private boolean isNewEnvironment = false;

    @Inject
    public EditEnvironmentViewModel() { }

    public Environment getEnvironment() {
        if(currentEnvironment == null) {
            if(isNewEnvironment) {
                currentEnvironment = new Environment();
            } else {
                currentEnvironment = getCurrentEnvironment.execute();
            }
        }

        return currentEnvironment;
    }

    public List<ConfigurationProperty> getProperties() {
        return getConfigurationProperties.execute();
    }

    public void saveEnvironment() {
        createOrUpdateEnvironment.setEnvironment(currentEnvironment).execute();
        setCurrentEnvironment.setEnvironment(currentEnvironment).execute();
    }

    public void setIsNewEnvironment(boolean isNewEnvironment) {
        this.isNewEnvironment = isNewEnvironment;
    }

    public boolean isNewEnvironment() {
        return isNewEnvironment;
    }

    public boolean isNameValid() {
        return !(isNewEnvironment && TextUtils.isEmpty(currentEnvironment.getName()));
    }
}
