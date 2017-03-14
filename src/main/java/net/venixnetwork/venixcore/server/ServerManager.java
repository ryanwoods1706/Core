package net.venixnetwork.venixcore.server;

import net.venixnetwork.venixcore.Core;

/**
 * Created by Ryan on 13/03/2017.
 */
public class ServerManager {

    private Core core;
    private boolean chatMuted = false;

    public ServerManager(Core core){
        this.core = core;
    }


    public boolean isChatMuted(){ return this.chatMuted;}
    public void setChatMuted(boolean bool){ this.chatMuted = bool;}
}
