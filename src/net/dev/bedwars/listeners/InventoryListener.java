package net.dev.bedwars.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import net.dev.bedwars.game.BedWarsTeam;
import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.utils.GameFileUtils;
import net.dev.bedwars.utils.ItemUtils;
import net.dev.bedwars.utils.Utils;

public class InventoryListener implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if((e.getItem() != null) && (e.getItem().getType() != Material.AIR) && (e.getItem().getItemMeta() != null) && (e.getItem().getItemMeta().getDisplayName() != null)) {
				if(!(GameManager.isRunning) || GameManager.isEnding)
					e.setCancelled(true);
				
				if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aWähle dein Team")) {
					e.setCancelled(true);
					
					Inventory inv = Bukkit.createInventory(null, 9, "§aWähle dein Team§8:");
					
					int i = 0;
					boolean b = false;
					
					for (BedWarsTeam team : GameManager.teams) {
						if((i < (GameManager.teams.size() / 2)) && !(b)) {
							i++;
							
							String lore = "";
							
							for (UUID member : team.getMembers()) {
								Player t = Bukkit.getPlayer(member);
								
								lore += "§8» §7" + t.getName() + "\n";
							}
							
							inv.setItem(i - 1, ItemUtils.createItem(Material.WOOL, 1, team.getWoolColor(), team.getColor() + team.getTeamName(), lore.split("\n")));
						} else {
							b = true;
							i--;
							
							String lore = "";
							
							for (UUID member : team.getMembers()) {
								Player t = Bukkit.getPlayer(member);
								
								lore += "§8» §7" + t.getName() + "\n";
							}
							
							inv.setItem(8 - i, ItemUtils.createItem(Material.WOOL, 1, team.getWoolColor(), team.getColor() + team.getTeamName(), lore.split("\n")));
						}
					}

					p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 5, 5);
					p.openInventory(inv);
				} else if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Gold-Voting")) {
					e.setCancelled(true);
					
					Inventory inv = Bukkit.createInventory(null, 9, "§6Gold-Voting§8:");
					
					inv.setItem(2, ItemUtils.createItem(Material.WOOL, 1, 5, "§aAn", "§e" + GameManager.goldVotesYes + " §7Votes"));
					inv.setItem(6, ItemUtils.createItem(Material.WOOL, 1, 14, "§cAus", "§e" + GameManager.goldVotesNo + " §7Votes"));
					
					p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 5, 5);
					p.openInventory(inv);
				} else if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Map-Voting")) {
					e.setCancelled(true);
					
					Inventory inv = Bukkit.createInventory(null, ((GameFileUtils.cfg.getStringList("Maps").size() / 9) + 1) * 9, "§7Map-Voting§8:");
					
					int i = 0;
					
					for (String mapName : GameFileUtils.cfg.getStringList("Maps")) {
						if(!(GameManager.maps.containsKey(mapName)))
							GameManager.maps.put(mapName, 0);

						inv.setItem(i, ItemUtils.createItem(Material.PAPER, "§7" + mapName, "§e" + GameManager.maps.get(mapName) + " §7Votes"));
						
						i++;
					}
					
					p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 5, 5);
					p.openInventory(inv);
				} else if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cVerlassen")) {
					e.setCancelled(true);
					
					p.kickPlayer("§cDu hast das Spiel verlassen");
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if(!(GameManager.isRunning) || GameManager.isEnding)
			e.setCancelled(true);
		
		if((e.getCurrentItem() != null) && (e.getCurrentItem().getType() != Material.AIR) && (e.getCurrentItem().getItemMeta() != null) && (e.getCurrentItem().getItemMeta().getDisplayName() != null)) {
			if(e.getInventory().getName().equalsIgnoreCase("§aWähle dein Team§8:")) {
				e.setCancelled(true);
				
				for (BedWarsTeam team : GameManager.teams) {
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(team.getColor() + team.getTeamName())) {
						if(team.getMembers().size() < team.getTeamSize()) {
							team.addMember(p);
							
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
							p.sendMessage(Utils.prefix + "§7Du bist nun in Team " + team.getColor() + team.getTeamName());
							p.closeInventory();
						}
					}
				}
			} else if(e.getInventory().getName().equalsIgnoreCase("§6Gold-Voting§8:")) {
				e.setCancelled(true);
				
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aAn")) {
					if(GameManager.goldVoted.containsKey(p.getUniqueId())) {
						if(GameManager.goldVoted.get(p.getUniqueId()))
							GameManager.goldVotesYes--;
						else
							GameManager.goldVotesNo--;
						
						GameManager.goldVoted.remove(p.getUniqueId());
					}
						
					GameManager.goldVotesYes++;
					GameManager.goldVoted.put(p.getUniqueId(), true);
					
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
					p.sendMessage(Utils.prefix + "§7Du hast §afür §6Gold §7abgestimmt");
					p.closeInventory();
				} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cAus")) {
					if(GameManager.goldVoted.containsKey(p.getUniqueId())) {
						if(GameManager.goldVoted.get(p.getUniqueId()))
							GameManager.goldVotesYes--;
						else
							GameManager.goldVotesNo--;
						
						GameManager.goldVoted.remove(p.getUniqueId());
					}
						
					GameManager.goldVotesNo++;
					GameManager.goldVoted.put(p.getUniqueId(), false);
					
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
					p.sendMessage(Utils.prefix + "§7Du hast §cgegen §6Gold §7abgestimmt");
					p.closeInventory();
				}
			} else if(e.getInventory().getName().equalsIgnoreCase("§7Map-Voting§8:")) {
				e.setCancelled(true);
				
				for (String mapName : GameFileUtils.cfg.getStringList("Maps")) {
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7" + mapName)) {
						if(GameManager.mapsVoted.containsKey(p.getUniqueId())) {
							String oldVotedMap = GameManager.mapsVoted.get(p.getUniqueId());
							
							GameManager.maps.put(oldVotedMap, GameManager.maps.get(oldVotedMap) - 1);
							GameManager.mapsVoted.remove(p.getUniqueId());
						}
						
						GameManager.maps.put(mapName, GameManager.maps.get(mapName) + 1);
						GameManager.mapsVoted.put(p.getUniqueId(), mapName);
						
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.sendMessage(Utils.prefix + "§7Du hast für die Map §a" + mapName + " §7abgestimmt");
						p.closeInventory();
					}
				}
			}
		}
	}
	
}
