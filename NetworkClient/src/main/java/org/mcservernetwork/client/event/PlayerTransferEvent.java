package org.mcservernetwork.client.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.mcservernetwork.commons.net.Sector;

/**
 * @author barpec12 on 23.06.2020
 */
public class PlayerTransferEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private boolean isCancelled = false;
    private Location from;
    private Location to;
    private Sector sectorTo;
    private Player player;

    public PlayerTransferEvent(Location from, Location to, Sector sectorTo, Player player){
        this.from = from;
        this.to = to;
        this.sectorTo = sectorTo;
        this.player = player;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
    public Location getFrom(){
        return from;
    }
    public Location getTo(){
        return to;
    }
    public Sector getSectorTo(){
        return sectorTo;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
