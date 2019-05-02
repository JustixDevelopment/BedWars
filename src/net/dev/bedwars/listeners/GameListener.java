package net.dev.bedwars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import net.dev.bedwars.game.BedWarsTeam;
import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.utils.GameFileUtils;
import net.dev.bedwars.utils.ItemUtils;
import net.dev.bedwars.utils.Utils;

public class GameListener implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		if(p.getGameMode().equals(GameMode.CREATIVE))
			return;
		
		if(!(GameManager.isRunning) || GameManager.isEnding) {
			e.setCancelled(true);
			return;
		}
		
		if(e.getBlock().getType().equals(Material.BED_BLOCK) && GameManager.isRunning && GameManager.inGame.contains(p.getUniqueId())) {
			BedWarsTeam team = null;
			
			for (BedWarsTeam bedWarsTeam : GameManager.teams) {
				if(bedWarsTeam.getBedTop().equals(e.getBlock().getLocation()) || bedWarsTeam.getBedBottom().equals(e.getBlock().getLocation()))
					team = bedWarsTeam;
			}
			
			if(team != null) {
				if(!(team.getTeamName().equalsIgnoreCase(GameManager.getTeam(p).getTeamName()))) {
					Block top = team.getBedTop().getBlock();
					Block bottom = team.getBedBottom().getBlock();
					
					top.getDrops().clear();
					top.setType(Material.AIR, false);
					
					bottom.getDrops().clear();
					bottom.setType(Material.AIR, false);
					
					Bukkit.getOnlinePlayers().forEach(all -> all.playSound(all.getLocation(), Sound.ENDERDRAGON_GROWL, 5, 5));
					Utils.broadcastMessage(Utils.prefix + "§7Das Bett von Team " + team.getColor() + team.getTeamName() + " §7wurde von dem Spieler §e" + p.getDisplayName() + " §7zerstört");
				} else {
					e.setCancelled(true);
					
					p.sendMessage(Utils.prefix + "§7Du kannst dein eigenes Bett §cnicht §7zerstören");
				}
			} else {
				e.setCancelled(true);
			}
		} else if(!(GameManager.buildedBlocks.contains(e.getBlock().getLocation()))) {
			e.setCancelled(true);
		} else {
			if(e.getBlock().getType() == Material.RED_SANDSTONE) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR, false);
				
				if((p.getItemInHand().getType() == Material.WOOD_PICKAXE) || (p.getItemInHand().getType() == Material.STONE_PICKAXE) || (p.getItemInHand().getType() == Material.IRON_PICKAXE))
					e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), ItemUtils.createItem(Material.RED_SANDSTONE, "§7Sandstein"));
			} else if(e.getBlock().getType() == Material.ENDER_STONE) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR, false);
				
				if((p.getItemInHand().getType() == Material.WOOD_PICKAXE) || (p.getItemInHand().getType() == Material.STONE_PICKAXE) || (p.getItemInHand().getType() == Material.IRON_PICKAXE))
					e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), ItemUtils.createItem(Material.ENDER_STONE, "§7Endstein"));
			} else if(e.getBlock().getType() == Material.IRON_BLOCK) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR, false);
				
				if((p.getItemInHand().getType() == Material.STONE_PICKAXE) || (p.getItemInHand().getType() == Material.IRON_PICKAXE))
					e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), ItemUtils.createItem(Material.IRON_BLOCK, "§7Eisenblock"));
			} else if(e.getBlock().getType() == Material.STAINED_GLASS) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR, false);
			} else if(e.getBlock().getType() == Material.GLOWSTONE) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR, false);
			} else if(e.getBlock().getType() == Material.CHEST) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR, false);
				e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), ItemUtils.createItem(Material.CHEST, "§aKiste"));
			} else if(e.getBlock().getType() == Material.ENDER_CHEST) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR, false);
				e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), ItemUtils.createItem(Material.ENDER_CHEST, "§aTeamkiste"));
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			if(!(GameManager.isRunning) || !(GameManager.inGame.contains(p.getUniqueId())) || GameManager.isEnding)
				e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			if(!(GameManager.isRunning) || !(GameManager.inGame.contains(p.getUniqueId())) || GameManager.isEnding) {
				e.setCancelled(true);
				
				p.setFoodLevel(20);
			}
		}
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		Player p = (Player) e.getPlayer();
		
		if(!(GameManager.isRunning) || !(GameManager.inGame.contains(p.getUniqueId())) || GameManager.isEnding)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		
		if(p.getGameMode().equals(GameMode.CREATIVE))
			return;
		
		if(!(GameManager.isRunning) || GameManager.isEnding) {
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
		
		if(!(GameManager.buildedBlocks.contains(e.getBlock().getLocation())))
			GameManager.buildedBlocks.add(e.getBlock().getLocation());
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		if(e.getTo().getBlockY() <= 0) {
			p.setHealth(0);
			p.spigot().respawn();
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		
		if(GameManager.inGame.contains(p.getUniqueId())) {
			BedWarsTeam team = GameManager.getTeam(p);
			
			if(team.getBedSymbol().equalsIgnoreCase("§c✖")) {
				GameManager.kickPlayerFromGame(p);
			}
			
			if(p.getKiller() != null) {
				e.setDeathMessage(Utils.prefix + "§7Der Spieler " + p.getDisplayName() + " §7wurde von dem Spieler " + p.getKiller().getDisplayName() + " §7getötet");
			} else {
				e.setDeathMessage(Utils.prefix + "§7Der Spieler " + p.getDisplayName() + " §7ist gestorben");
			}
		}
		
		if(GameManager.isGliding.contains(p))
			GameManager.isGliding.remove(p);
		
		if((p.getInventory().getChestplate() != null) && (p.getInventory().getChestplate().getType() != Material.AIR) && (p.getInventory().getChestplate().getItemMeta() != null) && (p.getInventory().getChestplate().getItemMeta().getDisplayName() != null)) {
			if(p.getInventory().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("§1Sprengweste")) {
				for (Player all : Bukkit.getOnlinePlayers()) {
					if(all != p) {
						all.playSound(p.getLocation(), Sound.EXPLODE, 5, 5);
						all.playEffect(p.getLocation(), Effect.EXPLOSION_LARGE, 100);
						
						if(((int) all.getLocation().distanceSquared(p.getLocation())) <= 10) {
							all.setVelocity(all.getLocation().getDirection().multiply(-1.4).setY(0.5));
							all.damage((10 - all.getLocation().distanceSquared(p.getLocation())) * 1.5);
						}
					}
				}
			}
		}
		
		e.getDrops().clear();
		e.setDroppedExp(0);
		p.spigot().respawn();
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		
		if(GameManager.inGame.contains(p.getUniqueId()))
			e.setRespawnLocation(GameManager.getTeam(p).getSpawnLocation());
		else if(!(GameManager.isRunning))
			e.setRespawnLocation(GameFileUtils.getLocation("Lobby"));
		else
			e.setRespawnLocation(GameManager.teams.get(0).getSpawnLocation());
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if(GameManager.isRunning && !(GameManager.isEnding)) {
			e.setCancelled(true);
			
			if(GameManager.inGame.contains(p.getUniqueId())) {
				if(e.getMessage().toLowerCase().startsWith("@a ")) {
					Utils.broadcastMessage("§8[§7Global§8] " + p.getDisplayName() + "§8: §r" + e.getMessage().substring(3));
				} else
					GameManager.getTeam(p).getMembers().forEach(uuid -> Bukkit.getPlayer(uuid).sendMessage(p.getDisplayName() + "§8: §r" + e.getMessage()));
			} else
				Bukkit.getOnlinePlayers().stream().filter(all -> !GameManager.inGame.contains(all.getUniqueId())).forEach(all -> all.sendMessage("§8[§7Spectator-Chat§8] §7" + p.getName() + "§8: §r" + e.getMessage()));
		} else
			e.setFormat(p.getDisplayName() + "§8: §r" + e.getMessage());
	}
	
}
