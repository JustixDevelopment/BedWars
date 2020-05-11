package net.dev.bedwars.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import net.dev.bedwars.BedWars;
import net.dev.bedwars.game.GameManager;

public class PlayerDropItemListener implements Listener {

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		GameManager gameManager = BedWars.getInstance().getGameManager();
		
		Player p = (Player) e.getPlayer();
		
		if(!(gameManager.isRunning()) || !(gameManager.getInGame().contains(p.getUniqueId())) || gameManager.isEnding())
			e.setCancelled(true);
	}
	
}
