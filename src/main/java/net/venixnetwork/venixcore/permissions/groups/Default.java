package net.venixnetwork.venixcore.permissions.groups;

import net.venixnetwork.venixcore.permissions.Group;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 14/03/2017.
 */
public class Default implements Group {
    public String prefix() {
        return ChatColor.RESET + "";
    }

    public List<String> perms() {
        List<String> perms = new ArrayList<String>();
        perms.add("perms.default");
        return perms;
    }

    public String name() {
        return "default";
    }
}
