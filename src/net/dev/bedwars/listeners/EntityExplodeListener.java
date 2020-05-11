package net.dev.bedwars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import net.dev.bedwars.BedWars;
import net.dev.bedwars.game.GameManager;

public class EntityExplodeListener implements Listener {

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e) {
		GameManager gameManager = BedWars.getInstance().getGameManager();
		
		if(e.getEntity() instanceof TNTPrimed) {
			e.setCancelled(true);
			e.setYield(0F);
			
			for (Player all : Bukkit.getOnlinePlayers()) {
				all.playSound(e.getLocation(), Sound.EXPLODE, 5, 5);
				all.playEffect(e.getLocation(), Effect.EXPLOSION_LARGE, 100);
				
				if(((int) all.getLocation().distanceSquared(e.getLocation())) < 10) {
					all.setVelocity(all.getLocation().getDirection().multiply(-1.4).setY(0.5));
					all.damage((10 - all.getLocation().distanceSquared(e.getLocation())) * 1.5);
				}
			}
			
			for (Block b : e.blockList()) {
				if(gameManager.getBuildedBlocks().contains(b.getLocation())) {
					b.setType(Material.AIR);
					
					gameManager.getBuildedBlocks().remove(b.getLocation());
				}
			}
		}
	}
	
}
