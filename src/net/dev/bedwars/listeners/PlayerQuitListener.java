package net.dev.bedwars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.dev.bedwars.BedWars;
import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.game.ScoreboardUtils;
import net.dev.bedwars.utils.Utils;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		BedWars bedWars = BedWars.getInstance();
		GameManager gameManager = bedWars.getGameManager();
		Utils utils = bedWars.getUtils();
		ScoreboardUtils scoreboardUtils = bedWars.getScoreboardUtils();
		
		Player p = e.getPlayer();
		String prefix = utils.getPrefix();
		
		if(scoreboardUtils.getScoreboards().containsKey(p.getName()))
			scoreboardUtils.getScoreboards().remove(p.getName());
		
		gameManager.kickPlayerFromGame(p);
		
		if(gameManager.isRunning()) {
			if(gameManager.getInGame().contains(p.getUniqueId()))
				e.setQuitMessage(prefix + "§7Der Spieler " + p.getDisplayName() + " §7hat das Spiel verlassen");
			else
				e.setQuitMessage(null);
		} else
			e.setQuitMessage(prefix + "§7Der Spieler §e" + p.getName() + " §7hat das Spiel verlassen §8[" + (Bukkit.getOnlinePlayers().size() - 1) + "/" + (gameManager.getTeams().size() * gameManager.getTeams().get(0).getTeamSize()) + "]");
	}
	
}
