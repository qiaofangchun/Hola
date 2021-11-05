package com.hola.plugin.core;

public class PluginManager {
    private static final PluginManager INSTANCE = null;

    private PluginManager(){
    }

    public static PluginManager getInstance(){
        return INSTANCE;
    }

}
