package me.desertdweller.orchestrionslite;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class InstrumentUseEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	Player p;

	public InstrumentUseEvent(Player p) {
		this.p = p;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public Player getPlayer() {
		return p;
	}

}
