package net.dev.bedwars.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import net.dev.bedwars.BedWars;
import net.dev.bedwars.game.GameManager;

public class FoodLevelChangeListener implements Listener {

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		GameManager gameManager = BedWars.getInstance().getGameManager();
		
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			if(!(gameManager.isRunning()) || !(gameManager.getInGame().contains(p.getUniqueId())) || gameManager.isEnding()) {
				e.setCancelled(true);
				p.setFoodLevel(20);
			}
		}
	}
	
}
