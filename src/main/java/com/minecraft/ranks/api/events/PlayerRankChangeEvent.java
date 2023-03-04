package com.minecraft.ranks.api.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.minecraft.ranks.api.ranks.Rank;

public class PlayerRankChangeEvent extends Event{

    private OfflinePlayer player;
    private Rank rank;

    public PlayerRankChangeEvent(OfflinePlayer who, Rank rank) {
        super();
        this.player = who;
        this.rank = rank;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public Rank getRank() {
        return rank;
    }

    private static HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
}
