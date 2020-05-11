package net.dev.bedwars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.dev.bedwars.BedWars;
import net.dev.bedwars.game.GameManager;

public class AsyncPlayerChatListener implements Listener {

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
		BedWars bedWars = BedWars.getInstance();
		GameManager gameManager = bedWars.getGameManager();
		
		Player p = e.getPlayer();
		
		if(gameManager.isRunning() && !(gameManager.isEnding())) {
			e.setCancelled(true);
			
			if(gameManager.getInGame().contains(p.getUniqueId())) {
				if(e.getMessage().toLowerCase().startsWith("@a ")) {
					bedWars.getUtils().broadcastMessage("§8[§7Global§8] " + p.getDisplayName() + "§8: §r" + e.getMessage().substring(3));
				} else
					gameManager.getTeam(p).getMembers().forEach(uuid -> Bukkit.getPlayer(uuid).sendMessage(p.getDisplayName() + "§8: §r" + e.getMessage()));
			} else
				Bukkit.getOnlinePlayers().stream().filter(all -> !gameManager.getInGame().contains(all.getUniqueId())).forEach(all -> all.sendMessage("§8[§7Spectator-Chat§8] §7" + p.getName() + "§8: §r" + e.getMessage()));
		} else
			e.setFormat(p.getDisplayName() + "§8: §r" + e.getMessage());
	}
	
}
