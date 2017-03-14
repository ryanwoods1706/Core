package net.venixnetwork.venixcore.permissions;

import net.venixnetwork.venixcore.Core;
import net.venixnetwork.venixcore.permissions.groups.Default;
import net.venixnetwork.venixcore.permissions.groups.Owner;
import net.venixnetwork.venixcore.player.VenixPlayer;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by Ryan on 13/03/2017.
 */
public class GroupManager {

    private Core core;
    private final Map<UUID, String> playerGroup = new HashMap<UUID, String>();


    public GroupManager(Core core){
        this.core = core;
    }


    public Map<UUID, String> getPlayerGroup(){ return this.playerGroup;}


    public void addPerms(Player pl){
        VenixPlayer venixPlayer = this.core.getPlayerManager().getPlayerData().get(pl.getUniqueId());
        if (pl != null){
            for (String string : getPlayerGroupCla(venixPlayer.getGroup()).perms()){
                pl.addAttachment(this.core, string, true);
            }
        }
    }


    public Group getPlayerGroupCla(String groupName){
        for (Group group : getGroupList()){
            if (group.name().equalsIgnoreCase(groupName)){
                return group;
            }
        }
        return null;
    }

    public List<Group> getGroupList(){
        List<Group> groups = new ArrayList<Group>();
        groups.add(new Owner());
        groups.add(new Default());
        return groups;
    }

    public boolean doesGroupExist(String input){
        for (Group group : this.core.getGroupManager().getGroupList()){
            if (input.equalsIgnoreCase(group.name())){
                return true;
            }
        }
        return false;
    }

    public void removePermissions(Player pl){
        if (pl.isOnline()) {
            VenixPlayer venixPlayer = this.core.getPlayerManager().getPlayerData().get(pl.getUniqueId());
            for (String node : getPlayerGroupCla(venixPlayer.getGroup()).perms()) {
                pl.addAttachment(this.core, node, false);
            }
        }
    }
}
