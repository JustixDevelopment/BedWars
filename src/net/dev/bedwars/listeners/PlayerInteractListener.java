package net.dev.bedwars.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.dev.bedwars.BedWars;
import net.dev.bedwars.game.BedWarsTeam;
import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.utils.GameFileUtils;
import net.dev.bedwars.utils.ItemUtils;

public class PlayerInteractListener implements Listener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		BedWars bedWars = BedWars.getInstance();
		GameManager gameManager = bedWars.getGameManager();
		ItemUtils itemUtils = bedWars.getItemUtils();
		GameFileUtils gameFileUtils = bedWars.getGameFileUtils();
		
		Player p = e.getPlayer();
		
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if((e.getItem() != null) && (e.getItem().getType() != Material.AIR) && (e.getItem().getItemMeta() != null) && (e.getItem().getItemMeta().getDisplayName() != null)) {
				if(!(gameManager.isRunning()) || gameManager.isEnding() && !(p.getGameMode().equals(GameMode.CREATIVE)))
					e.setCancelled(true);
				
				String displayName = e.getItem().getItemMeta().getDisplayName();
				
				if(displayName.equalsIgnoreCase("§aWähle dein Team")) {
					e.setCancelled(true);
					
					Inventory inv = Bukkit.createInventory(null, 9, "§aWähle dein Team§8:");
					
					int i = 0;
					boolean b = false;
					
					for (BedWarsTeam team : gameManager.getTeams()) {
						if((i < (gameManager.getTeams().size() / 2)) && !(b)) {
							i++;
							
							String lore = "";
							
							for (UUID member : team.getMembers()) {
								Player t = Bukkit.getPlayer(member);
								
								lore += "§8» §7" + t.getName() + "\n";
							}
							
							inv.setItem(i - 1, itemUtils.createItem(Material.WOOL, 1, team.getWoolColor(), team.getColor() + team.getTeamName(), lore.split("\n")));
						} else {
							b = true;
							i--;
							
							String lore = "";
							
							for (UUID member : team.getMembers()) {
								Player t = Bukkit.getPlayer(member);
								
								lore += "§8» §7" + t.getName() + "\n";
							}
							
							inv.setItem(8 - i, itemUtils.createItem(Material.WOOL, 1, team.getWoolColor(), team.getColor() + team.getTeamName(), lore.split("\n")));
						}
					}

					p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 5, 5);
					p.openInventory(inv);
				} else if(displayName.equalsIgnoreCase("§6Gold-Voting")) {
					e.setCancelled(true);
					
					Inventory inv = Bukkit.createInventory(null, 9, "§6Gold-Voting§8:");
					
					inv.setItem(2, itemUtils.createItem(Material.WOOL, 1, 5, "§aAn", "§e" + gameManager.getGoldVotesYes() + " §7Votes"));
					inv.setItem(6, itemUtils.createItem(Material.WOOL, 1, 14, "§cAus", "§e" + gameManager.getGoldVotesNo() + " §7Votes"));
					
					p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 5, 5);
					p.openInventory(inv);
				} else if(displayName.equalsIgnoreCase("§7Map-Voting")) {
					e.setCancelled(true);
					
					Inventory inv = Bukkit.createInventory(null, ((gameFileUtils.getConfig().getStringList("Maps").size() / 9) + 1) * 9, "§7Map-Voting§8:");
					
					int i = 0;
					
					for (String mapName : gameFileUtils.getConfig().getStringList("Maps")) {
						if(!(gameManager.getMaps().containsKey(mapName)))
							gameManager.getMaps().put(mapName, 0);

						inv.setItem(i, itemUtils.createItem(Material.PAPER, "§7" + mapName, "§e" + gameManager.getMaps().get(mapName) + " §7Votes"));
						
						i++;
					}
					
					p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 5, 5);
					p.openInventory(inv);
				} else if(displayName.equalsIgnoreCase("§cVerlassen")) {
					e.setCancelled(true);
					
					p.kickPlayer("§cDu hast das Spiel verlassen");
				} else if(displayName.equalsIgnoreCase("§6Teleporter")) {
					e.setCancelled(true);
					
					ItemStack item = e.getItem();

					if(item.getAmount() > 1)
						item.setAmount(item.getAmount() - 1);
					else
						item = new ItemStack(Material.AIR);
						
					p.getInventory().setItemInHand(item);
					
					Bukkit.getScheduler().runTaskLater(BedWars.getPlugin(BedWars.class), new Runnable() {
						
						int time = 5;
						
						@Override
						public void run() {
							if(time != 0) {
								Bukkit.getScheduler().runTaskLater(BedWars.getPlugin(BedWars.class), this, 20);
								
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
				} else if(displayName.equalsIgnoreCase("§6Mobiler Shop")) {
					e.setCancelled(true);
					
					bedWars.getShopManager().openShopPage(p, 0);
				} else if(displayName.equalsIgnoreCase("§6Fallschirm")) {
					e.setCancelled(true);
					
					if(!(gameManager.getIsGliding().contains(p))) {
						ItemStack item = e.getItem();

						if(item.getAmount() > 1)
							item.setAmount(item.getAmount() - 1);
						else
							item = new ItemStack(Material.AIR);
							
						p.getInventory().setItemInHand(item);
						
						gameManager.getIsGliding().add(p);
					}
				} else if(displayName.equalsIgnoreCase("§6Rettungsplattform")) {
					e.setCancelled(true);
					
					ItemStack item = e.getItem();

					if(item.getAmount() > 1)
						item.setAmount(item.getAmount() - 1);
					else
						item = new ItemStack(Material.AIR);
						
					p.getInventory().setItemInHand(item);
					
					BedWarsTeam team = gameManager.getTeam(p);
					
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
					
					p.setFallDistance(0);
				}
			}
			
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(e.getClickedBlock().getType() == Material.CHEST) {
					Location loc = e.getClickedBlock().getLocation();
					
					if(!(gameManager.getChests().containsKey(loc)))
						gameManager.getChests().put(loc, Bukkit.createInventory(null, 27, "§7Kiste"));
					
					Bukkit.getScheduler().runTaskLater(BedWars.getPlugin(BedWars.class), new Runnable() {
						
						@Override
						public void run() {
							p.openInventory(gameManager.getChests().get(loc));
						}
					}, 2);
				} else if(e.getClickedBlock().getType() == Material.ENDER_CHEST) {
					String teamName = gameManager.getTeam(p).getTeamName();
					
					if(!(gameManager.getTeamChests().containsKey(teamName)))
						gameManager.getTeamChests().put(teamName, Bukkit.createInventory(null, 27, "§7Teamkiste §8[§e" + teamName + "§8]"));
					
					Bukkit.getScheduler().runTaskLater(BedWars.getPlugin(BedWars.class), new Runnable() {
						
						@Override
						public void run() {
							p.openInventory(gameManager.getTeamChests().get(teamName));
						}
					}, 2);
				}
			}
		}
	}
	
	private void setBlock(Location loc, int addX, int addY, int addZ, Material mat, int metaData) {
		Location location = loc.clone().add(addX, addY, addZ);
		BedWars.getInstance().getGameManager().getStructureBuildedBlocks().put(location, location.getWorld().getBlockAt(location).getType());
		
		location.getWorld().getBlockAt(location).setType(mat);
		location.getWorld().getBlockAt(location).setData((byte) metaData);
	}
	
}