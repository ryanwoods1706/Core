package net.venixnetwork.venixcore.permissions.groups;

import net.venixnetwork.venixcore.permissions.Group;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 13/03/2017.
 */
public class Owner implements Group{

    public String prefix() {
        return ChatColor.RED + "[Owner] " + ChatColor.RESET;
    }

    public List<String> perms() {
        List<String> perms = new ArrayList<String>();
        perms.add("perms.owner");
        perms.add("perms.admin");
        return perms;
    }

    public String name() {
        return "owner";
    }
}
