package net.dev.bedwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class PlayerKickListener implements Listener {

	@EventHandler
	public void onPlayerKick(PlayerKickEvent e) {
		e.setLeaveMessage(null);
	}
	
}
