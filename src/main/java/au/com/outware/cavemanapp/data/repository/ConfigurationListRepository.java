package au.com.outware.cavemanapp.data.repository;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import au.com.outware.cavemanapp.R;
import au.com.outware.cavemanapp.data.model.ConfigurationProperty;
import au.com.outware.cavemanapp.domain.repository.ConfigurationRepository;
import au.com.outware.cavemanapp.util.RawReader;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class ConfigurationListRepository implements ConfigurationRepository {
    private final List<ConfigurationProperty> properties;

    public ConfigurationListRepository(Context context) {
        String json = RawReader.readStringFromRaw(R.raw.configuration, context);
        Type categoryListType = new TypeToken<List<ConfigurationProperty>>() {}.getType();
        properties = new Gson().fromJson(json, categoryListType);
    }

    @Override
    public List<ConfigurationProperty> getProperties() {
        return properties;
    }
}
