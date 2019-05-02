package net.dev.bedwars.listeners;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.game.ScoreboardUtils;
import net.dev.bedwars.main.Main;
import net.dev.bedwars.utils.FileUtils;
import net.dev.bedwars.utils.GameFileUtils;
import net.dev.bedwars.utils.ItemUtils;
import net.dev.bedwars.utils.Utils;

public class JoinQuitListener implements Listener {

	public ArrayList<UUID> kicked = new ArrayList<>();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if(GameManager.isRunning) {
			p.setGameMode(GameMode.SPECTATOR);
			p.teleport(GameManager.teams.get(0).getSpawnLocation());
			p.setExp(0F);
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			
			for (Player all : Bukkit.getOnlinePlayers()) {
				if(GameManager.inGame.contains(all.getUniqueId()))
					all.hidePlayer(p);
			}
			
			e.setJoinMessage(null);
		} else {
			if(Bukkit.getOnlinePlayers().size() > (GameManager.teams.size() * GameManager.teams.get(0).getTeamSize())) {
				e.setJoinMessage(null);
				
				kicked.add(p.getUniqueId());
				
				p.kickPlayer("§cDas Spiel ist voll");
				return;
			}
			
			e.setJoinMessage(Utils.prefix + "§7Der Spieler §e" + p.getName() + " §7hat das Spiel betreten §8[" + Bukkit.getOnlinePlayers().size() + "/" + (GameManager.teams.size() * GameManager.teams.get(0).getTeamSize()) + "]");
			
			p.setGameMode(GameMode.SURVIVAL);
			p.teleport(GameFileUtils.getLocation("Lobby"));
			p.setExp(0F);
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			
			for (int i = 0; i < 9; i++)
				p.getInventory().setItem(i, ItemUtils.createItem(Material.STAINED_GLASS_PANE, 1, 15, "§8"));
			
			p.getInventory().setItem(0, ItemUtils.createItem(Material.GOLD_INGOT, "§6Gold-Voting", "§7Rechtsklick um für Gold an/aus zu voten"));
			p.getInventory().setItem(1, ItemUtils.createItem(Material.PAPER, "§7Map-Voting", "§7Rechtsklick um für eine Map zu voten"));
			p.getInventory().setItem(4, ItemUtils.createItem(Material.BED, "§aWähle dein Team", "§7Rechtsklick um dein Team zu wählen"));
			p.getInventory().setItem(8, ItemUtils.createItem(Material.ARROW, "§cVerlassen", "§7Rechtsklick um das Spiel zu verlassen"));
			
			if(!(GameManager.isCountDownStarted) && (Bukkit.getOnlinePlayers().size() == FileUtils.cfg.getInt("Settings.PlayersNeededToStart"))) {
				GameManager.isCountDownStarted = true;
				GameManager.i = 60;
				
				GameManager.countDownTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
					
					@Override
					public void run() {
						if(Bukkit.getOnlinePlayers().size() >= FileUtils.cfg.getInt("Settings.PlayersNeededToStart")) {
							int i = GameManager.i;
							
							for (Player all : Bukkit.getOnlinePlayers())
								all.setLevel(i);
							
							if(i == 10)
								GameManager.gameIsStarting = true;
							
							if((i == 60) || (i == 50) || (i == 40) || (i == 30) || (i == 20) || (i == 10) || (i == 5) || (i == 4) || (i == 3) || (i == 2) || (i == 1))
								Utils.broadcastMessage(Utils.prefix + "§7Das Spiel startet in §e" + i + " Sekunden");
							else if(i == 0) {
								Utils.broadcastMessage(Utils.prefix + "§7Das Spiel startet §ejetzt");
								
								GameManager.isCountDownStarted = false;
								Bukkit.getScheduler().cancelTask(GameManager.countDownTask);
								
								GameManager.startGame();
							}
							
							GameManager.i--;
						} else {
							for (Player all : Bukkit.getOnlinePlayers())
								all.setLevel(0);
							
							GameManager.gameIsStarting = false;
							GameManager.isCountDownStarted = false;
							Bukkit.getScheduler().cancelTask(GameManager.countDownTask);
						}
					}
				}, 20, 20);
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		if(ScoreboardUtils.scoreboards.containsKey(p.getName()))
			ScoreboardUtils.scoreboards.remove(p.getName());
		
		if(GameManager.isRunning) {
			if(GameManager.inGame.contains(p.getUniqueId())) {
				GameManager.kickPlayerFromGame(p);
				
				e.setQuitMessage(Utils.prefix + "§7Der Spieler " + p.getDisplayName() + " §7hat das Spiel verlassen");
			} else
				e.setQuitMessage(null);
		} else
			e.setQuitMessage(Utils.prefix + "§7Der Spieler §e" + p.getName() + " §7hat das Spiel verlassen §8[" + (Bukkit.getOnlinePlayers().size() - 1) + "/" + (GameManager.teams.size() * GameManager.teams.get(0).getTeamSize()) + "]");
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		Player p = e.getPlayer();
		
		if(kicked.contains(p.getUniqueId())) {
			e.setLeaveMessage(null);
			
			return;
		}
		
		if(ScoreboardUtils.scoreboards.containsKey(p.getName()))
			ScoreboardUtils.scoreboards.remove(p.getName());
		
		if(GameManager.isRunning) {
			if(GameManager.inGame.contains(p.getUniqueId())) {
				GameManager.kickPlayerFromGame(p);
				
				e.setLeaveMessage(Utils.prefix + "§7Der Spieler " + p.getDisplayName() + " §7hat das Spiel verlassen");
			} else
				e.setLeaveMessage(null);
		} else
			e.setLeaveMessage(Utils.prefix + "§7Der Spieler §e" + p.getName() + " §7hat das Spiel verlassen §8[" + (Bukkit.getOnlinePlayers().size() - 1) + "/" + (GameManager.teams.size() * GameManager.teams.get(0).getTeamSize()) + "]");
	}
	
}
