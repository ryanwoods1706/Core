package net.venixnetwork.venixcore.player;

import net.venixnetwork.venixcore.Core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Ryan on 13/03/2017.
 */
public class PlayerManager {

    private Core core;
    private Map<UUID, VenixPlayer> playerData = new HashMap<UUID, VenixPlayer>();

    public PlayerManager(Core core){
        this.core = core;
    }

    public Map<UUID, VenixPlayer> getPlayerData(){ return this.playerData;}
}
