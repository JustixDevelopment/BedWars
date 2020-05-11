package net.dev.bedwars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import net.dev.bedwars.BedWars;
import net.dev.bedwars.game.BedWarsTeam;
import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.utils.ItemUtils;
import net.dev.bedwars.utils.Utils;

public class BlockBreakListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		BedWars bedWars = BedWars.getInstance();
		GameManager gameManager = bedWars.getGameManager();
		Utils utils = bedWars.getUtils();
		ItemUtils itemUtils = bedWars.getItemUtils();
		
		Player p = e.getPlayer();
		String prefix = utils.getPrefix();
		
		if(p.getGameMode().equals(GameMode.CREATIVE))
			return;
		
		if(!(gameManager.isRunning()) || gameManager.isEnding()) {
			e.setCancelled(true);
			return;
		}
		
		if(e.getBlock().getType().equals(Material.BED_BLOCK) && gameManager.isRunning() && gameManager.getInGame().contains(p.getUniqueId())) {
			BedWarsTeam team = null;
			
			for (BedWarsTeam bedWarsTeam : gameManager.getTeams()) {
				if(bedWarsTeam.getBedTop().equals(e.getBlock().getLocation()) || bedWarsTeam.getBedBottom().equals(e.getBlock().getLocation()))
					team = bedWarsTeam;
			}
			
			if(team != null) {
				if(!(team.getTeamName().equalsIgnoreCase(gameManager.getTeam(p).getTeamName()))) {
					Block top = team.getBedTop().getBlock();
					Block bottom = team.getBedBottom().getBlock();
					
					top.getDrops().clear();
					top.setType(Material.AIR, false);
					
					bottom.getDrops().clear();
					bottom.setType(Material.AIR, false);
					
					Bukkit.getOnlinePlayers().forEach(all -> all.playSound(all.getLocation(), Sound.ENDERDRAGON_GROWL, 5, 5));
					utils.broadcastMessage(prefix + "§7Das Bett von Team " + team.getColor() + team.getTeamName() + " §7wurde von dem Spieler §e" + p.getDisplayName() + " §7zerstört");
				} else {
					e.setCancelled(true);
					
					p.sendMessage(prefix + "§7Du kannst dein eigenes Bett §cnicht §7zerstören");
				}
			} else {
				e.setCancelled(true);
			}
		} else if(!(gameManager.getBuildedBlocks().contains(e.getBlock().getLocation()))) {
			e.setCancelled(true);
		} else {
			if(e.getBlock().getType() == Material.RED_SANDSTONE) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR, false);
				
				if((p.getItemInHand().getType() == Material.WOOD_PICKAXE) || (p.getItemInHand().getType() == Material.STONE_PICKAXE) || (p.getItemInHand().getType() == Material.IRON_PICKAXE))
					e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), itemUtils.createItem(Material.RED_SANDSTONE, "§7Sandstein"));
			} else if(e.getBlock().getType() == Material.ENDER_STONE) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR, false);
				
				if((p.getItemInHand().getType() == Material.WOOD_PICKAXE) || (p.getItemInHand().getType() == Material.STONE_PICKAXE) || (p.getItemInHand().getType() == Material.IRON_PICKAXE))
					e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), itemUtils.createItem(Material.ENDER_STONE, "§7Endstein"));
			} else if(e.getBlock().getType() == Material.IRON_BLOCK) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR, false);
				
				if((p.getItemInHand().getType() == Material.STONE_PICKAXE) || (p.getItemInHand().getType() == Material.IRON_PICKAXE))
					e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), itemUtils.createItem(Material.IRON_BLOCK, "§7Eisenblock"));
			} else if(e.getBlock().getType() == Material.STAINED_GLASS) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR, false);
			} else if(e.getBlock().getType() == Material.GLOWSTONE) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR, false);
			} else if(e.getBlock().getType() == Material.CHEST) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR, false);
				e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), itemUtils.createItem(Material.CHEST, "§aKiste"));
			} else if(e.getBlock().getType() == Material.ENDER_CHEST) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR, false);
				e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), itemUtils.createItem(Material.ENDER_CHEST, "§aTeamkiste"));
			}
		}
	}
	
}
