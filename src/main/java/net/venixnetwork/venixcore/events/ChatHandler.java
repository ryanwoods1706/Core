package net.venixnetwork.venixcore.events;

import net.venixnetwork.venixcore.Core;
import net.venixnetwork.venixcore.permissions.Group;
import net.venixnetwork.venixcore.player.VenixPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Ryan on 13/03/2017.
 */
public class ChatHandler implements Listener{


    private Core core;

    public ChatHandler(Core core){
        this.core = core;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        if (this.core.getServerManager().isChatMuted()){
            if (!player.hasPermission("perms.staff")){
                e.setCancelled(true);
                player.sendMessage("§cThe chat is currently disabled!");
            }
        }
        String msg = e.getMessage().replace("%", "%%");
        e.setFormat(player.getDisplayName() + ": " + ChatColor.RESET + msg);
    }




}
