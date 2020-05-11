package net.dev.bedwars.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.dev.bedwars.BedWars;
import net.dev.bedwars.game.GameManager;

public class PlayerMoveListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		GameManager gameManager = BedWars.getInstance().getGameManager();
		
		Player p = e.getPlayer();
		
		if(e.getTo().getBlockY() <= 0) {
			p.setHealth(0);
			p.spigot().respawn();
		} else if(gameManager.getIsGliding().contains(p)) {
			p.setVelocity(p.getLocation().getDirection().multiply(0.4).setY(-0.1));
			p.setFallDistance(0);
			
			if(p.isOnGround())
				gameManager.getIsGliding().remove(p);
		}
	}
	
}
