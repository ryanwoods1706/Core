package net.venixnetwork.venixcore.permissions;

import java.util.List;

/**
 * Created by Ryan on 13/03/2017.
 */
public abstract interface Group {

    public abstract String prefix();

    public abstract List<String> perms();

    public abstract String name();
}
