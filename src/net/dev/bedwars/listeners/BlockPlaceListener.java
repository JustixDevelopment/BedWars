package net.dev.bedwars.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import net.dev.bedwars.BedWars;
import net.dev.bedwars.game.GameManager;

public class BlockPlaceListener implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		GameManager gameManager = BedWars.getInstance().getGameManager();
		
		Player p = e.getPlayer();
		
		if(p.getGameMode().equals(GameMode.CREATIVE))
			return;
		
		if(!(gameManager.isRunning()) || gameManager.isEnding()) {
			e.setCancelled(true);
			return;
		}
		
		if(e.getBlock().getType() == Material.TNT) {
			ItemStack item = p.getItemInHand();

			if(item.getAmount() > 1)
				item.setAmount(item.getAmount() - 1);
			else
				item = new ItemStack(Material.AIR);
				
			p.getInventory().setItemInHand(item);
			
			e.setCancelled(true);
			e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.PRIMED_TNT);
			return;
		}
		
		if(!(gameManager.getBuildedBlocks().contains(e.getBlock().getLocation())))
			gameManager.getBuildedBlocks().add(e.getBlock().getLocation());
	}
	
}
