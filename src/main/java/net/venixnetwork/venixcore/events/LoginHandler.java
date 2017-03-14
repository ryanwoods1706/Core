package net.venixnetwork.venixcore.events;

import javafx.scene.layout.Priority;
import net.venixnetwork.venixcore.Core;
import net.venixnetwork.venixcore.player.VenixPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * Created by Ryan on 13/03/2017.
 */
public class LoginHandler implements Listener {

    private Core core;

    public LoginHandler(Core core) {
        this.core = core;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onASyncLogin(AsyncPlayerPreLoginEvent e) {
        UUID uuid = e.getUniqueId();
        InetAddress IP = e.getAddress();
        if (!this.core.getPlayerManager().getPlayerData().containsKey(uuid)) {
            VenixPlayer venixPlayer = new VenixPlayer(uuid, IP, this.core);
            venixPlayer.getInformation();
            this.core.getPlayerManager().getPlayerData().put(uuid, venixPlayer);
            if (!this.core.getGroupManager().getPlayerGroup().containsKey(uuid)) {
                this.core.getGroupManager().getPlayerGroup().put(uuid, venixPlayer.getGroup());
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player pl = e.getPlayer();
        VenixPlayer venixPlayer = this.core.getPlayerManager().getPlayerData().get(pl.getUniqueId());
        if (this.core.getGroupManager().doesGroupExist(venixPlayer.getGroup())){
            pl.setDisplayName(this.core.getGroupManager().getPlayerGroupCla(venixPlayer.getGroup()).prefix() + pl.getName());
        }
    }
}
