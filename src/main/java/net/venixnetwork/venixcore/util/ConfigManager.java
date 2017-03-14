package net.venixnetwork.venixcore.util;

import net.venixnetwork.venixcore.Core;

/**
 * Created by Ryan on 13/03/2017.
 */
public class ConfigManager {

    private Core core;

    public ConfigManager(Core core){
        this.core = core;
    }
    
    
    
    public void initiateConfig()
    {
        String path = "Messages.";
        this.core.getConfig().addDefault(path + "prefix", "[VenixNetwork]");;
        this.core.getConfig().addDefault("SQL.ip", "localhost");
        this.core.getConfig().addDefault("SQL.database", "venix");
        this.core.getConfig().addDefault("SQL.user", "username");
        this.core.getConfig().addDefault("SQL.password", "password");
        this.core.getConfig().options().copyDefaults(true);
        this.core.saveConfig();

    }
}
