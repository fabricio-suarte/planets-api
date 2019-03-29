package com.fabriciosuarte.planets.api.common;

import java.util.Properties;

/**
 * A helper for sharing application properties throughout layers
 */
public final class PropertyBag {
    private static PropertyBag instance;

    private Properties properties = new Properties();

    private PropertyBag() {}

    public static PropertyBag getInstance() {

        if(instance == null) {
            synchronized (PropertyBag.class) {

                //double check!
                if( instance == null) {
                    instance = new PropertyBag();
                }
            }
        }

        return instance;
    }

    public String getString(String key) {
        return this.properties.getProperty(key, "");
    }

    public Integer getInteger(String key) {
        return (Integer) this.properties.getOrDefault(key, null);
    }

    public void setString(String key, String value) {
        this.properties.setProperty(key, value);
    }

    public void setInteger(String key, Integer value) {
        this.properties.setProperty(key, String.valueOf(value));
    }
}
