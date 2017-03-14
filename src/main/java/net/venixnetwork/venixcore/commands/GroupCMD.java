package net.venixnetwork.venixcore.commands;

import net.venixnetwork.venixcore.Core;
import net.venixnetwork.venixcore.permissions.Group;
import net.venixnetwork.venixcore.player.VenixPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Ryan on 14/03/2017.
 */
public class GroupCMD implements CommandExecutor {

    private Core core;

    public GroupCMD(Core core){
        this.core = core;
    }


    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        try{
            if (args[0].equalsIgnoreCase("list")){
                sender.sendMessage("§e" + this.core.getGroupManager().getGroupList().toString());
            }
            else if (args[0].equalsIgnoreCase("add")){
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                if (!this.core.getSql().doesUserExist(player.getUniqueId())){
                    sender.sendMessage("§cThat user is not present in the database");
                }
                if (!doesGroupExist(args[2])){
                    sender.sendMessage("§cThat group does not exist! Use /group list");
                    return true;
                }
                if (!player.isOnline()){
                    this.core.getSql().updateOfflinerPlayerRank(player.getUniqueId(), args[2]);
                    sender.sendMessage("§aSuccessfully updated: §e" + player.getName() + "'s §arank to: §e" + args[2]);
                }
                if (player.isOnline()){
                    VenixPlayer venixPlayer = this.core.getPlayerManager().getPlayerData().get(player.getUniqueId());
                    venixPlayer.setGroup(args[2]);
                    sender.sendMessage("§aSuccessfully updated: §e" + player.getName() + "'s §arank to: §e" + args[2]);
                }

            }
            else if (args[0].equalsIgnoreCase("remove")){

            }
            else if (args[0].equalsIgnoreCase("userinfo")){

            }
            else if (args[0].equalsIgnoreCase("groupinfo")){

            }



        }catch (ArrayIndexOutOfBoundsException e){
            sender.sendMessage("§cUsage: /group list -- Lists all groups");
            sender.sendMessage("§cUsage: /group add <player> <groupname> -- assigns a group to a player");
            sender.sendMessage("§cUsage: /group remove <player> <groupname> -- Removes a group from said player.");
            sender.sendMessage("§cUsage: /group userinfo <player> -- Gets all information corresponding to that user");
            sender.sendMessage("§cUsage: /group groupinfo <group> -- Gets all information corresponding to that group");
        }
        return false;
    }

    private boolean doesGroupExist(String input){
        for (Group group : this.core.getGroupManager().getGroupList()){
            if (input.equalsIgnoreCase(group.name())){
                return true;
            }
        }
        return false;
    }
}
