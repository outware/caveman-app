package au.com.outware.cavemanapp.presentation.viewmodel;

import android.support.annotation.VisibleForTesting;

import java.util.List;

import javax.inject.Inject;

import au.com.outware.caveman.data.model.Environment;
import au.com.outware.cavemanapp.domain.interactor.GetCurrentEnvironment;
import au.com.outware.cavemanapp.domain.interactor.GetEnvironments;
import au.com.outware.cavemanapp.domain.interactor.RemoveEnvironment;
import au.com.outware.cavemanapp.domain.interactor.SetCurrentEnvironment;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class MainActivityViewModel {
    @VisibleForTesting
    @Inject
    GetEnvironments getEnvironments;
    @VisibleForTesting
    @Inject
    GetCurrentEnvironment getCurrentEnvironment;
    @VisibleForTesting
    @Inject
    SetCurrentEnvironment setCurrentEnvironment;
    @VisibleForTesting
    @Inject
    RemoveEnvironment removeEnvironment;

    @Inject
    public MainActivityViewModel() { }

    public int getSelectedItemPosition() {
        int pos = getEnvironments().indexOf(getCurrentEnvironment());
        return (pos != -1) ? pos : 0;
    }

    public Environment getCurrentEnvironment() {
        return getCurrentEnvironment.execute();
    }

    public List<Environment> getEnvironments() {
        return getEnvironments.execute();
    }

    public void setCurrentEnvironment(Environment environment) {
        setCurrentEnvironment.setEnvironment(environment).execute();
    }

    public void removeCurrentEnvironment() {
        removeEnvironment.setEnvironment(getCurrentEnvironment()).execute();
        for (Environment environment : getEnvironments.execute()) {
            if (environment.isProduction()) {
                setCurrentEnvironment.setEnvironment(environment).execute();
                break;
            }
        }
    }

    public boolean shouldEnableButtons() {
        return !getCurrentEnvironment().isProduction();
    }
}
