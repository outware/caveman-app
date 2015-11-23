package au.com.outware.cavemanapp.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import au.com.outware.cavemanapp.R;
import au.com.outware.cavemanapp.domain.repository.EnvironmentRepository;
import au.com.outware.cavemanapp.util.RawReader;
import au.com.outware.caveman.data.model.Environment;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class EnvironmentSharedPrefsRepository implements EnvironmentRepository {
    public final static String ENVIRONMENT_LIST = "ENVIRONMENT_LIST";
    public final static String CURRENT_ENVIRONMENT = "CURRENT_ENVIRONMENT";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private final Gson gson = new Gson();
    private final Type environmentListType = new TypeToken<List<Environment>>() {}.getType();

    public EnvironmentSharedPrefsRepository(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        editor = sharedPreferences.edit();

        if(!sharedPreferences.contains(ENVIRONMENT_LIST)) {
            String environment = RawReader.readStringFromRaw(R.raw.environment, context);
            editor.putString(ENVIRONMENT_LIST, environment).commit();
        }
    }

    public void saveEnvironments(List<Environment> environments) {
         editor.putString(ENVIRONMENT_LIST, gson.toJson(environments)).commit();
    }

    public List<Environment> getEnvironments() {
        return gson.fromJson(sharedPreferences.getString(ENVIRONMENT_LIST, null), environmentListType);
    }

    public void saveCurrentEnvironment(String environment) {
        editor.putString(CURRENT_ENVIRONMENT, environment).commit();
    }

    public String getCurrentEnvironment() {
        return sharedPreferences.getString(CURRENT_ENVIRONMENT, null);
    }
}
