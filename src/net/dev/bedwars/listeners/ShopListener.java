package net.dev.bedwars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import net.dev.bedwars.game.BedWarsTeam;
import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.main.Main;
import net.dev.bedwars.utils.ShopManager;
import net.dev.bedwars.utils.ShopManager.ItemType;

public class ShopListener implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Villager) {
			Villager v = (Villager) e.getEntity();
			
			if((v.getCustomName() != null) && v.getCustomName().equals("§6§lShop"))
				e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerInteractAtEntity(PlayerInteractEntityEvent e) {
		if(e.getRightClicked() instanceof Villager) {
			Villager v = (Villager) e.getRightClicked();
			
			if((v.getCustomName() != null) && v.getCustomName().equals("§6§lShop")) {
				e.setCancelled(true);
				
				ShopManager.openShopPage(e.getPlayer(), 0);
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			
			if(e.getInventory().getName().startsWith("§7» §3Shop§8: §8")) {
				e.setCancelled(true);
				
				if((e.getCurrentItem() != null) && (e.getCurrentItem().getType() != Material.AIR) && (e.getCurrentItem().getItemMeta() != null) && (e.getCurrentItem().getItemMeta().getDisplayName() != null)) {
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Blöcke")) {
						ShopManager.openShopPage(p, 0);
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Rüstungen")) {
						ShopManager.openShopPage(p, 1);
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Spitzhacken")) {
						ShopManager.openShopPage(p, 2);
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Schwerter")) {
						ShopManager.openShopPage(p, 3);
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Bögen")) {
						ShopManager.openShopPage(p, 4);
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Nahrung")) {
						ShopManager.openShopPage(p, 5);
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Kisten")) {
						ShopManager.openShopPage(p, 6);
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Tränke")) {
						ShopManager.openShopPage(p, 7);
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Spezial")) {
						ShopManager.openShopPage(p, 8);
					} else {
						if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Sandstein"))
							ShopManager.buyItem(p, ItemType.BRONZE, 1, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Endstein"))
							ShopManager.buyItem(p, ItemType.BRONZE, 6, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Eisenblock"))
							ShopManager.buyItem(p, ItemType.IRON, 3, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Glas"))
							ShopManager.buyItem(p, ItemType.BRONZE, 3, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Glowstone"))
							ShopManager.buyItem(p, ItemType.BRONZE, 14, e.getCurrentItem(), e.isShiftClick());
						
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9Lederhelm"))
							ShopManager.buyItem(p, ItemType.BRONZE, 1, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9Lederhose"))
							ShopManager.buyItem(p, ItemType.BRONZE, 1, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9Lederschuhe"))
							ShopManager.buyItem(p, ItemType.BRONZE, 1, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§1Brustplatte"))
							ShopManager.buyItem(p, ItemType.IRON, 1, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§1Brustplatte I"))
							ShopManager.buyItem(p, ItemType.IRON, 3, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§1Brustplatte II"))
							ShopManager.buyItem(p, ItemType.IRON, 7, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§1Brustplatte III"))
							ShopManager.buyItem(p, ItemType.IRON, 11, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§1Sprengweste"))
							ShopManager.buyItem(p, ItemType.GOLD, 6, e.getCurrentItem(), e.isShiftClick());
						
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eSpitzhacke I"))
							ShopManager.buyItem(p, ItemType.BRONZE, 7, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eSpitzhacke II"))
							ShopManager.buyItem(p, ItemType.IRON, 2, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eSpitzhacke III"))
							ShopManager.buyItem(p, ItemType.GOLD, 1, e.getCurrentItem(), e.isShiftClick());
						
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cKnüppel"))
							ShopManager.buyItem(p, ItemType.BRONZE, 10, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cHolzschwert"))
							ShopManager.buyItem(p, ItemType.IRON, 1, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cHolzschwert I"))
							ShopManager.buyItem(p, ItemType.IRON, 4, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cHolzschwert II"))
							ShopManager.buyItem(p, ItemType.IRON, 6, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4Eisenschwert"))
							ShopManager.buyItem(p, ItemType.GOLD, 6, e.getCurrentItem(), e.isShiftClick());

						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§5Bogen I"))
							ShopManager.buyItem(p, ItemType.GOLD, 3, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§5Bogen II"))
							ShopManager.buyItem(p, ItemType.GOLD, 7, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§5Bogen III"))
							ShopManager.buyItem(p, ItemType.GOLD, 11, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§dPfeil"))
							ShopManager.buyItem(p, ItemType.GOLD, 1, e.getCurrentItem(), e.isShiftClick());
						
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Apfel"))
							ShopManager.buyItem(p, ItemType.BRONZE, 2, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Fleisch"))
							ShopManager.buyItem(p, ItemType.BRONZE, 4, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Kuchen"))
							ShopManager.buyItem(p, ItemType.IRON, 1, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Goldapfel"))
							ShopManager.buyItem(p, ItemType.GOLD, 2, e.getCurrentItem(), e.isShiftClick());
						
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aKiste"))
							ShopManager.buyItem(p, ItemType.IRON, 2, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aTeamkiste"))
							ShopManager.buyItem(p, ItemType.GOLD, 2, e.getCurrentItem(), e.isShiftClick());
						
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Heilung I"))
							ShopManager.buyItem(p, ItemType.IRON, 5, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Heilung II"))
							ShopManager.buyItem(p, ItemType.IRON, 8, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Stärke"))
							ShopManager.buyItem(p, ItemType.GOLD, 10, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Haste"))
							ShopManager.buyItem(p, ItemType.GOLD, 5, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Sprungkraft"))
							ShopManager.buyItem(p, ItemType.GOLD, 3, e.getCurrentItem(), e.isShiftClick());
						
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Leiter"))
							ShopManager.buyItem(p, ItemType.BRONZE, 2, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Teleporter"))
							ShopManager.buyItem(p, ItemType.IRON, 10, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Mobiler Shop"))
							ShopManager.buyItem(p, ItemType.IRON, 12, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6TNT"))
							ShopManager.buyItem(p, ItemType.GOLD, 3, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Fallschirm"))
							ShopManager.buyItem(p, ItemType.GOLD, 2, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Rettungsplattform"))
							ShopManager.buyItem(p, ItemType.GOLD, 3, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Enderperle"))
							ShopManager.buyItem(p, ItemType.GOLD, 14, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§§6Spinnenweben"))
							ShopManager.buyItem(p, ItemType.BRONZE, 20, e.getCurrentItem(), e.isShiftClick());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			if((e.getItem() != null) && (e.getItem().getType() != Material.AIR) && (e.getItem().getItemMeta() != null) && (e.getItem().getItemMeta().getDisplayName() != null)) {
				if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Teleporter")) {
					e.setCancelled(true);
					
					ItemStack item = e.getItem();

					if(item.getAmount() > 1)
						item.setAmount(item.getAmount() - 1);
					else
						item = new ItemStack(Material.AIR);
						
					p.getInventory().setItemInHand(item);
					
					Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable() {
						
						int time = 5;
						
						@Override
						public void run() {
							if(time != 0) {
								Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), this, 20);
								
								for (Player all : Bukkit.getOnlinePlayers()) {
									all.playSound(p.getLocation(), Sound.NOTE_PLING, 5, 5);
									
									for (int i = 0; i < 100; i++)
										all.playEffect(p.getLocation(), Effect.COLOURED_DUST, 100);
								}
							} else
								for (Player all : Bukkit.getOnlinePlayers())
									for (int i = 0; i < 100; i++)
										all.playEffect(p.getLocation(), Effect.CLOUD, 100);

							time--;
						}
					}, 20);
				} else if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Mobiler Shop")) {
					e.setCancelled(true);
					
					ShopManager.openShopPage(p, 0);
				} else if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Fallschirm")) {
					e.setCancelled(true);
					
					if(!(GameManager.isGliding.contains(p))) {
						ItemStack item = e.getItem();

						if(item.getAmount() > 1)
							item.setAmount(item.getAmount() - 1);
						else
							item = new ItemStack(Material.AIR);
							
						p.getInventory().setItemInHand(item);
						
						GameManager.isGliding.add(p);
					}
				} else if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Rettungsplattform")) {
					e.setCancelled(true);
					
					ItemStack item = e.getItem();

					if(item.getAmount() > 1)
						item.setAmount(item.getAmount() - 1);
					else
						item = new ItemStack(Material.AIR);
						
					p.getInventory().setItemInHand(item);
					
					BedWarsTeam team = GameManager.getTeam(p);
					
					setBlock(p.getLocation(), 0, -3, 0, Material.STAINED_GLASS, team.getWoolColor());
					setBlock(p.getLocation(), 1, -3, 0, Material.STAINED_GLASS, team.getWoolColor());
					setBlock(p.getLocation(), 0, -3, 1, Material.STAINED_GLASS, team.getWoolColor());
					setBlock(p.getLocation(), 1, -3, 1, Material.STAINED_GLASS, team.getWoolColor());
					setBlock(p.getLocation(), -1, -3, 0, Material.STAINED_GLASS, team.getWoolColor());
					setBlock(p.getLocation(), 0, -3, -1, Material.STAINED_GLASS, team.getWoolColor());
					setBlock(p.getLocation(), -1, -3, -1, Material.STAINED_GLASS, team.getWoolColor());
					setBlock(p.getLocation(), 1, -3, -1, Material.STAINED_GLASS, team.getWoolColor());
					setBlock(p.getLocation(), -1, -3, 1, Material.STAINED_GLASS, team.getWoolColor());
					setBlock(p.getLocation(), 2, -3, 0, Material.STAINED_GLASS, team.getWoolColor());
					setBlock(p.getLocation(), 0, -3, 2, Material.STAINED_GLASS, team.getWoolColor());
					setBlock(p.getLocation(), -2, -3, 0, Material.STAINED_GLASS, team.getWoolColor());
					setBlock(p.getLocation(), 0, -3, -2, Material.STAINED_GLASS, team.getWoolColor());
				}
			}
			
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(e.getClickedBlock().getType() == Material.CHEST) {
					Location loc = e.getClickedBlock().getLocation();
					
					if(!(GameManager.chests.containsKey(loc)))
						GameManager.chests.put(loc, Bukkit.createInventory(null, 27, "§7Kiste"));
					
					Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable() {
						
						@Override
						public void run() {
							p.openInventory(GameManager.chests.get(loc));
						}
					}, 2);
				} else if(e.getClickedBlock().getType() == Material.ENDER_CHEST) {
					String teamName = GameManager.getTeam(p).getTeamName();
					
					if(!(GameManager.teamChests.containsKey(teamName)))
						GameManager.teamChests.put(teamName, Bukkit.createInventory(null, 27, "§7Teamkiste §8[§e" + teamName + "§8]"));
					
					Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable() {
						
						@Override
						public void run() {
							p.openInventory(GameManager.teamChests.get(teamName));
						}
					}, 2);
				}
			}
		}
	}
	
	private void setBlock(Location loc, int addX, int addY, int addZ, Material mat, int metaData) {
		Location location = loc.clone().add(addX, addY, addZ);
		GameManager.structureBuildedBlocks.put(location, location.getWorld().getBlockAt(location).getType());
		
		location.getWorld().getBlockAt(location).setType(mat);
		location.getWorld().getBlockAt(location).setData((byte) metaData);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		if(GameManager.isGliding.contains(p)) {
			p.setVelocity(p.getLocation().getDirection().multiply(0.4).setY(-0.1));
			p.setFallDistance(0);
			
			if(p.isOnGround())
				GameManager.isGliding.remove(p);
		}
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e) {
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
				if(GameManager.buildedBlocks.contains(b.getLocation())) {
					b.setType(Material.AIR);
					
					GameManager.buildedBlocks.remove(b.getLocation());
				}
			}
		}
	}
	
}
