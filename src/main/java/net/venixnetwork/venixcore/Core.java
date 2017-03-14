package net.venixnetwork.venixcore;

import net.venixnetwork.venixcore.events.ChatHandler;
import net.venixnetwork.venixcore.events.LoginHandler;
import net.venixnetwork.venixcore.permissions.GroupManager;
import net.venixnetwork.venixcore.player.PlayerManager;
import net.venixnetwork.venixcore.player.VenixPlayer;
import net.venixnetwork.venixcore.server.ServerManager;
import net.venixnetwork.venixcore.sql.SQL;
import net.venixnetwork.venixcore.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Ryan on 13/03/2017.
 */
public class Core extends JavaPlugin {

    private ConfigManager configManager;
    private GroupManager groupManager;
    private VenixPlayer venixPlayer;
    private PlayerManager playerManager;
    private SQL sql;
    private ServerManager serverManager;

    @Override
    public void onEnable(){
        reloadConfig();
        this.configManager = new ConfigManager(this);
        this.configManager.initiateConfig();
        this.groupManager = new GroupManager(this);
        this.venixPlayer = new VenixPlayer(this);
        this.playerManager = new PlayerManager(this);
        this.sql = new SQL(this);
        this.serverManager = new ServerManager(this);
        registerCom();
        registerEve();
    }

    @Override
    public void onDisable(){

    }


    private void registerEve(){
        PluginManager plm = Bukkit.getPluginManager();
        plm.registerEvents(new LoginHandler(this), this);
        plm.registerEvents(new ChatHandler(this), this);

    }
    private void registerCom(){

    }


    public ConfigManager getConfigManager(){ return this.configManager;}
    public GroupManager getGroupManager(){ return this.groupManager;}
    public VenixPlayer getVenixPlayer(){ return this.venixPlayer;}
    public PlayerManager getPlayerManager(){ return this.playerManager;}
    public ServerManager getServerManager(){ return this.serverManager;}
    public SQL getSql(){ return this.sql;}
}
