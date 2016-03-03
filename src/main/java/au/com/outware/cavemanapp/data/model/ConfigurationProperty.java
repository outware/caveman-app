package au.com.outware.cavemanapp.data.model;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public final class ConfigurationProperty <T> {
    String key;
    String name;
    T defaultValue;

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public Class getType() {
        return defaultValue.getClass();
    }

    public T getDefaultValue() {
        return defaultValue;
    }
}
