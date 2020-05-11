package net.dev.bedwars.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.dev.bedwars.BedWars;
import net.dev.bedwars.game.BedWarsTeam;
import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.utils.ShopManager;
import net.dev.bedwars.utils.ShopManager.ItemType;
import net.dev.bedwars.utils.Utils;

public class InventoryClickListener implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		BedWars bedWars = BedWars.getInstance();
		GameManager gameManager = bedWars.getGameManager();
		Utils utils = bedWars.getUtils();
		ShopManager shopManager = bedWars.getShopManager();
		
		Player p = (Player) e.getWhoClicked();
		String prefix = utils.getPrefix();
		
		if(!(gameManager.isRunning()) || gameManager.isEnding())
			e.setCancelled(true);
		
		if((e.getCurrentItem() != null) && (e.getCurrentItem().getType() != Material.AIR) && (e.getCurrentItem().getItemMeta() != null) && (e.getCurrentItem().getItemMeta().getDisplayName() != null)) {
			if(e.getInventory().getName().equalsIgnoreCase("§aWähle dein Team§8:")) {
				e.setCancelled(true);
				
				for (BedWarsTeam team : gameManager.getTeams()) {
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(team.getColor() + team.getTeamName())) {
						if(team.getMembers().size() < team.getTeamSize()) {
							team.addMember(p);
							
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
							p.sendMessage(prefix+ "§7Du bist nun in Team " + team.getColor() + team.getTeamName());
							p.closeInventory();
						}
					}
				}
			} else if(e.getInventory().getName().equalsIgnoreCase("§6Gold-Voting§8:")) {
				e.setCancelled(true);
				
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aAn")) {
					if(gameManager.getGoldVoted().containsKey(p.getUniqueId())) {
						if(gameManager.getGoldVoted().get(p.getUniqueId()))
							gameManager.setGoldVotesYes(gameManager.getGoldVotesYes() - 1);
						else
							gameManager.setGoldVotesNo(gameManager.getGoldVotesNo() - 1);
						
						gameManager.getGoldVoted().remove(p.getUniqueId());
					}
						
					gameManager.setGoldVotesYes(gameManager.getGoldVotesYes() + 1);
					gameManager.getGoldVoted().put(p.getUniqueId(), true);
					
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
					p.sendMessage(prefix+ "§7Du hast §afür §6Gold §7abgestimmt");
					p.closeInventory();
				} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cAus")) {
					if(gameManager.getGoldVoted().containsKey(p.getUniqueId())) {
						if(gameManager.getGoldVoted().get(p.getUniqueId()))
							gameManager.setGoldVotesYes(gameManager.getGoldVotesYes() - 1);
						else
							gameManager.setGoldVotesNo(gameManager.getGoldVotesNo() - 1);
						
						gameManager.getGoldVoted().remove(p.getUniqueId());
					}
						
					gameManager.setGoldVotesNo(gameManager.getGoldVotesNo() + 1);
					gameManager.getGoldVoted().put(p.getUniqueId(), false);
					
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
					p.sendMessage(prefix+ "§7Du hast §cgegen §6Gold §7abgestimmt");
					p.closeInventory();
				}
			} else if(e.getInventory().getName().equalsIgnoreCase("§7Map-Voting§8:")) {
				e.setCancelled(true);
				
				for (String mapName : bedWars.getGameFileUtils().getConfig().getStringList("Maps")) {
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7" + mapName)) {
						if(gameManager.getMapsVoted().containsKey(p.getUniqueId())) {
							String oldVotedMap = gameManager.getMapsVoted().get(p.getUniqueId());
							
							gameManager.getMaps().put(oldVotedMap, gameManager.getMaps().get(oldVotedMap) - 1);
							gameManager.getMapsVoted().remove(p.getUniqueId());
						}
						
						gameManager.getMaps().put(mapName, gameManager.getMaps().get(mapName) + 1);
						gameManager.getMapsVoted().put(p.getUniqueId(), mapName);
						
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 5);
						p.sendMessage(prefix+ "§7Du hast für die Map §a" + mapName + " §7abgestimmt");
						p.closeInventory();
					}
				}
			} else if(e.getInventory().getName().startsWith("§7» §3Shop§8: §8")) {
				e.setCancelled(true);
				
				if((e.getCurrentItem() != null) && (e.getCurrentItem().getType() != Material.AIR) && (e.getCurrentItem().getItemMeta() != null) && (e.getCurrentItem().getItemMeta().getDisplayName() != null)) {
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Blöcke")) {
						shopManager.openShopPage(p, 0);
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Rüstungen")) {
						shopManager.openShopPage(p, 1);
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Spitzhacken")) {
						shopManager.openShopPage(p, 2);
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Schwerter")) {
						shopManager.openShopPage(p, 3);
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Bögen")) {
						shopManager.openShopPage(p, 4);
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Nahrung")) {
						shopManager.openShopPage(p, 5);
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Kisten")) {
						shopManager.openShopPage(p, 6);
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Tränke")) {
						shopManager.openShopPage(p, 7);
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §3Spezial")) {
						shopManager.openShopPage(p, 8);
					} else {
						if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Sandstein"))
							shopManager.buyItem(p, ItemType.BRONZE, 1, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Endstein"))
							shopManager.buyItem(p, ItemType.BRONZE, 6, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Eisenblock"))
							shopManager.buyItem(p, ItemType.IRON, 3, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Glas"))
							shopManager.buyItem(p, ItemType.BRONZE, 3, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Glowstone"))
							shopManager.buyItem(p, ItemType.BRONZE, 14, e.getCurrentItem(), e.isShiftClick());
						
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9Lederhelm"))
							shopManager.buyItem(p, ItemType.BRONZE, 1, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9Lederhose"))
							shopManager.buyItem(p, ItemType.BRONZE, 1, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9Lederschuhe"))
							shopManager.buyItem(p, ItemType.BRONZE, 1, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§1Brustplatte"))
							shopManager.buyItem(p, ItemType.IRON, 1, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§1Brustplatte I"))
							shopManager.buyItem(p, ItemType.IRON, 3, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§1Brustplatte II"))
							shopManager.buyItem(p, ItemType.IRON, 7, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§1Brustplatte III"))
							shopManager.buyItem(p, ItemType.IRON, 11, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§1Sprengweste"))
							shopManager.buyItem(p, ItemType.GOLD, 6, e.getCurrentItem(), e.isShiftClick());
						
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eSpitzhacke I"))
							shopManager.buyItem(p, ItemType.BRONZE, 7, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eSpitzhacke II"))
							shopManager.buyItem(p, ItemType.IRON, 2, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eSpitzhacke III"))
							shopManager.buyItem(p, ItemType.GOLD, 1, e.getCurrentItem(), e.isShiftClick());
						
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cKnüppel"))
							shopManager.buyItem(p, ItemType.BRONZE, 10, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cHolzschwert"))
							shopManager.buyItem(p, ItemType.IRON, 1, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cHolzschwert I"))
							shopManager.buyItem(p, ItemType.IRON, 4, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cHolzschwert II"))
							shopManager.buyItem(p, ItemType.IRON, 6, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4Eisenschwert"))
							shopManager.buyItem(p, ItemType.GOLD, 6, e.getCurrentItem(), e.isShiftClick());

						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§5Bogen I"))
							shopManager.buyItem(p, ItemType.GOLD, 3, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§5Bogen II"))
							shopManager.buyItem(p, ItemType.GOLD, 7, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§5Bogen III"))
							shopManager.buyItem(p, ItemType.GOLD, 11, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§dPfeil"))
							shopManager.buyItem(p, ItemType.GOLD, 1, e.getCurrentItem(), e.isShiftClick());
						
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Apfel"))
							shopManager.buyItem(p, ItemType.BRONZE, 2, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Fleisch"))
							shopManager.buyItem(p, ItemType.BRONZE, 4, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Kuchen"))
							shopManager.buyItem(p, ItemType.IRON, 1, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Goldapfel"))
							shopManager.buyItem(p, ItemType.GOLD, 2, e.getCurrentItem(), e.isShiftClick());
						
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aKiste"))
							shopManager.buyItem(p, ItemType.IRON, 2, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aTeamkiste"))
							shopManager.buyItem(p, ItemType.GOLD, 2, e.getCurrentItem(), e.isShiftClick());
						
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Heilung I"))
							shopManager.buyItem(p, ItemType.IRON, 5, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Heilung II"))
							shopManager.buyItem(p, ItemType.IRON, 8, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Stärke"))
							shopManager.buyItem(p, ItemType.GOLD, 10, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Haste"))
							shopManager.buyItem(p, ItemType.GOLD, 5, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Sprungkraft"))
							shopManager.buyItem(p, ItemType.GOLD, 3, e.getCurrentItem(), e.isShiftClick());
						
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Leiter"))
							shopManager.buyItem(p, ItemType.BRONZE, 2, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Teleporter"))
							shopManager.buyItem(p, ItemType.IRON, 10, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Mobiler Shop"))
							shopManager.buyItem(p, ItemType.IRON, 12, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6TNT"))
							shopManager.buyItem(p, ItemType.GOLD, 3, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Fallschirm"))
							shopManager.buyItem(p, ItemType.GOLD, 2, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Rettungsplattform"))
							shopManager.buyItem(p, ItemType.GOLD, 3, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Enderperle"))
							shopManager.buyItem(p, ItemType.GOLD, 14, e.getCurrentItem(), e.isShiftClick());
						else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Spinnenweben"))
							shopManager.buyItem(p, ItemType.BRONZE, 20, e.getCurrentItem(), e.isShiftClick());
					}
				}
			}
		}
	}
	
}