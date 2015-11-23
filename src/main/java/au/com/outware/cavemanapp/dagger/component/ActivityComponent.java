package au.com.outware.cavemanapp.dagger.component;

import au.com.outware.cavemanapp.dagger.scope.ActivityScope;
import au.com.outware.cavemanapp.presentation.view.EditEnvironmentActivity;
import au.com.outware.cavemanapp.presentation.view.MainActivity;
import dagger.Component;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
@ActivityScope
@Component(dependencies = {ApplicationComponent.class})
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(EditEnvironmentActivity mainActivity);
}
