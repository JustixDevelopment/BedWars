package net.dev.bedwars.listeners;

import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import net.dev.bedwars.BedWars;

public class PlayerInteractEntityListener implements Listener {

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
		if(e.getRightClicked() instanceof Villager) {
			Villager v = (Villager) e.getRightClicked();
			
			if((v.getCustomName() != null) && v.getCustomName().equals("§6§lShop")) {
				e.setCancelled(true);
				BedWars.getInstance().getShopManager().openShopPage(e.getPlayer(), 0);
			}
		}
	}
	
}
