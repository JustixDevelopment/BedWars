package net.dev.bedwars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.dev.bedwars.BedWars;
import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.utils.FileUtils;
import net.dev.bedwars.utils.ItemUtils;
import net.dev.bedwars.utils.Utils;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		BedWars bedWars = BedWars.getInstance();
		FileUtils fileUtils = bedWars.getFileUtils();
		GameManager gameManager = bedWars.getGameManager();
		ItemUtils itemUtils = bedWars.getItemUtils();
		Utils utils = bedWars.getUtils();
		
		Player p = e.getPlayer();
		String prefix = utils.getPrefix();
		
		if(gameManager.isRunning()) {
			p.setGameMode(GameMode.SPECTATOR);
			p.teleport(gameManager.getTeams().get(0).getSpawnLocation());
			p.setExp(0F);
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.setHealth(20);
			p.setFoodLevel(20);
			
			for (Player all : Bukkit.getOnlinePlayers()) {
				if(gameManager.getInGame().contains(all.getUniqueId()))
					all.hidePlayer(p);
			}
			
			e.setJoinMessage(null);
		} else {
			if(Bukkit.getOnlinePlayers().size() > (gameManager.getTeams().size() * gameManager.getTeams().get(0).getTeamSize())) {
				e.setJoinMessage(null);
				
				p.kickPlayer("§cDas Spiel ist voll");
				return;
			}
			
			e.setJoinMessage(prefix + "§7Der Spieler §e" + p.getName() + " §7hat das Spiel betreten §8[" + Bukkit.getOnlinePlayers().size() + "/" + (gameManager.getTeams().size() * gameManager.getTeams().get(0).getTeamSize()) + "]");
			
			p.setGameMode(GameMode.SURVIVAL);
			p.teleport(bedWars.getGameFileUtils().getLocation("Lobby"));
			p.setExp(0F);
			p.setLevel(gameManager.getIndex());
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.setHealth(20);
			p.setFoodLevel(20);
			
			p.getInventory().setItem(0, itemUtils.createItem(Material.GOLD_INGOT, "§6Gold-Voting", "§7Rechtsklick um für Gold an/aus zu voten"));
			p.getInventory().setItem(1, itemUtils.createItem(Material.PAPER, "§7Map-Voting", "§7Rechtsklick um für eine Map zu voten"));
			p.getInventory().setItem(4, itemUtils.createItem(Material.BED, "§aWähle dein Team", "§7Rechtsklick um dein Team zu wählen"));
			p.getInventory().setItem(8, itemUtils.createItem(Material.ARROW, "§cVerlassen", "§7Rechtsklick um das Spiel zu verlassen"));
			
			if(!(gameManager.isCountDownStarted()) && (Bukkit.getOnlinePlayers().size() == fileUtils.getConfig().getInt("Settings.PlayersNeededToStart"))) {
				gameManager.setCountDownStarted(true);
				gameManager.setIndex(60);
				
				gameManager.setCountDownTask(Bukkit.getScheduler().scheduleSyncRepeatingTask(bedWars, new Runnable() {
					
					@Override
					public void run() {
						int i = gameManager.getIndex();
						
						for (Player all : Bukkit.getOnlinePlayers())
							all.setLevel(i);
						
						if(i == 10)
							gameManager.setGameStarting(true);
						
						if((i == 60) || (i == 50) || (i == 40) || (i == 30) || (i == 20) || (i == 10) || (i == 5) || (i == 4) || (i == 3) || (i == 2) || (i == 1))
							utils.broadcastMessage(prefix + "§7Das Spiel startet in §e" + i + " Sekunden");
						else if(i == 0) {
							utils.broadcastMessage(prefix + "§7Das Spiel startet §ejetzt");
							gameManager.startGame();
							return;
						}
						
						gameManager.setIndex(gameManager.getIndex() - 1);
					}
				}, 20, 20));
			}
		}
	}
	
}
