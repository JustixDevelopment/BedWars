package net.dev.bedwars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.dev.bedwars.BedWars;
import net.dev.bedwars.game.BedWarsTeam;
import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.utils.Utils;

public class PlayerDeathListener implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		BedWars bedWars = BedWars.getInstance();
		GameManager gameManager = bedWars.getGameManager();
		Utils utils = bedWars.getUtils();
		
		Player p = e.getEntity();
		String prefix = utils.getPrefix();
		
		if(gameManager.getInGame().contains(p.getUniqueId())) {
			BedWarsTeam team = gameManager.getTeam(p);
			
			if(team.getBedSymbol().equalsIgnoreCase("§c✖"))
				gameManager.kickPlayerFromGame(p);
			
			if(p.getKiller() != null)
				e.setDeathMessage(prefix + "§7Der Spieler " + p.getDisplayName() + " §7wurde von dem Spieler " + p.getKiller().getDisplayName() + " §7getötet");
			else
				e.setDeathMessage(prefix + "§7Der Spieler " + p.getDisplayName() + " §7ist gestorben");
		}
		
		if(gameManager.getIsGliding().contains(p))
			gameManager.getIsGliding().remove(p);
		
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
	
}
