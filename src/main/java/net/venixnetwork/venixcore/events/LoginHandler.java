package net.venixnetwork.venixcore.events;

import javafx.scene.layout.Priority;
import net.venixnetwork.venixcore.Core;
import net.venixnetwork.venixcore.player.VenixPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;

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
        if (!this.core.getPlayerManager().getPlayerData().containsKey(uuid)) {
            VenixPlayer venixPlayer = new VenixPlayer(uuid);
            venixPlayer.getInformation();
            this.core.getPlayerManager().getPlayerData().put(uuid, venixPlayer);
            if (!this.core.getGroupManager().getPlayerGroup().containsKey(uuid)) {
                this.core.getGroupManager().getPlayerGroup().put(uuid, venixPlayer.getGroup());
            }
        }
    }
}
