package net.dev.bedwars.main;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.dev.bedwars.commands.BedWarsCommand;
import net.dev.bedwars.commands.ForceMapCommand;
import net.dev.bedwars.commands.StartCommand;
import net.dev.bedwars.game.BedWarsTeam;
import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.game.ScoreboardUtils;
import net.dev.bedwars.listeners.GameListener;
import net.dev.bedwars.listeners.InventoryListener;
import net.dev.bedwars.listeners.JoinQuitListener;
import net.dev.bedwars.listeners.ShopListener;
import net.dev.bedwars.utils.FileUtils;
import net.dev.bedwars.utils.GameFileUtils;
import net.dev.bedwars.utils.Utils;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		FileUtils.setupFiles();
		GameFileUtils.setupFiles();
		
		getCommand("bedwars").setExecutor(new BedWarsCommand());
		getCommand("start").setExecutor(new StartCommand());
		getCommand("forcemap").setExecutor(new ForceMapCommand());
		
		Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new GameListener(), this);
		Bukkit.getPluginManager().registerEvents(new ShopListener(), this);

		if(!(GameManager.map.equalsIgnoreCase("NONE"))) {
			String gameFormat = FileUtils.getConfigString("Settings.GameFormat");
			
			if(gameFormat.equalsIgnoreCase("8x1")) {
				GameManager.teams.add(new BedWarsTeam("Blue", "§9Blue §7| §9", "§9", Color.AQUA, 1, 11, 1));
				GameManager.teams.add(new BedWarsTeam("Red", "§cRed §7| §c", "§c", Color.RED, 1, 14, 2));
				GameManager.teams.add(new BedWarsTeam("White", "§fWhite §7| §f", "§f", Color.WHITE, 1, 0, 3));
				GameManager.teams.add(new BedWarsTeam("Black", "§0Black §7| §0", "§0", Color.BLACK, 1, 15, 4));
				GameManager.teams.add(new BedWarsTeam("Pink", "§dPink §7| §d", "§d", Color.FUCHSIA, 1, 6, 5));
				GameManager.teams.add(new BedWarsTeam("Yellow", "§eYellow §7| §e", "§e", Color.YELLOW, 1, 4, 6));
				GameManager.teams.add(new BedWarsTeam("Green", "§aGreen §7| §a", "§a", Color.LIME, 1, 5, 7));
				GameManager.teams.add(new BedWarsTeam("Purple", "§5Purple §7| §5", "§5", Color.PURPLE, 1, 10, 8));
				
				for (BedWarsTeam team : GameManager.teams) {
					team.setSpawnLocation(GameFileUtils.getLocation(GameManager.map, "Spawn" + team.getTeamName().toUpperCase()));
					team.setBedTop(GameFileUtils.getBlockLocation(GameManager.map, "Bed" + team.getTeamName().toUpperCase()));
					team.setBedBottom(team.getBedTop().getBlock().getRelative(GameFileUtils.getBedFacing(GameManager.map, "Bed" + team.getTeamName().toUpperCase()).getOppositeFace()).getLocation());
					team.placeBed();
				}
			} else if(gameFormat.equalsIgnoreCase("8x2")) {
				GameManager.teams.add(new BedWarsTeam("Blue", "§9Blue §7| §9", "§9", Color.AQUA, 2, 11, 1));
				GameManager.teams.add(new BedWarsTeam("Red", "§cRed §7| §c", "§c", Color.RED, 2, 14, 2));
				GameManager.teams.add(new BedWarsTeam("White", "§fWhite §7| §f", "§f", Color.WHITE, 2, 0, 3));
				GameManager.teams.add(new BedWarsTeam("Black", "§0Black §7| §0", "§0", Color.BLACK, 2, 15, 4));
				GameManager.teams.add(new BedWarsTeam("Pink", "§dPink §7| §d", "§d", Color.FUCHSIA, 2, 6, 5));
				GameManager.teams.add(new BedWarsTeam("Yellow", "§eYellow §7| §e", "§e", Color.YELLOW, 2, 4, 6));
				GameManager.teams.add(new BedWarsTeam("Green", "§aGreen §7| §a", "§a", Color.LIME, 2, 5, 7));
				GameManager.teams.add(new BedWarsTeam("Purple", "§5Purple §7| §5", "§5", Color.PURPLE, 2, 10, 8));
				
				for (BedWarsTeam team : GameManager.teams) {
					team.setSpawnLocation(GameFileUtils.getLocation(GameManager.map, "Spawn" + team.getTeamName().toUpperCase()));
					team.setBedTop(GameFileUtils.getBlockLocation(GameManager.map, "Bed" + team.getTeamName().toUpperCase()));
					team.setBedBottom(team.getBedTop().getBlock().getRelative(GameFileUtils.getBedFacing(GameManager.map, "Bed" + team.getTeamName().toUpperCase()).getOppositeFace()).getLocation());
					team.placeBed();
				}
			} else if(gameFormat.equalsIgnoreCase("4x2")) {
				GameManager.teams.add(new BedWarsTeam("Blue", "§9Blue §7| §9", "§9", Color.AQUA, 2, 11, 1));
				GameManager.teams.add(new BedWarsTeam("Red", "§cRed §7| §c", "§c", Color.RED, 2, 14, 2));
				GameManager.teams.add(new BedWarsTeam("Yellow", "§eYellow §7| §e", "§e", Color.YELLOW, 2, 4, 6));
				GameManager.teams.add(new BedWarsTeam("Green", "§aGreen §7| §a", "§a", Color.LIME, 2, 5, 7));
				
				for (BedWarsTeam team : GameManager.teams) {
					team.setSpawnLocation(GameFileUtils.getLocation(GameManager.map, "Spawn" + team.getTeamName().toUpperCase()));
					team.setBedTop(GameFileUtils.getBlockLocation(GameManager.map, "Bed" + team.getTeamName().toUpperCase()));
					team.setBedBottom(team.getBedTop().getBlock().getRelative(GameFileUtils.getBedFacing(GameManager.map, "Bed" + team.getTeamName().toUpperCase()).getOppositeFace()).getLocation());
					team.placeBed();
				}
			} else if(gameFormat.equalsIgnoreCase("4x4")) {
				GameManager.teams.add(new BedWarsTeam("Blue", "§9Blue §7| §9", "§9", Color.AQUA, 4, 11, 1));
				GameManager.teams.add(new BedWarsTeam("Red", "§cRed §7| §c", "§c", Color.RED, 4, 14, 2));
				GameManager.teams.add(new BedWarsTeam("Yellow", "§eYellow §7| §e", "§e", Color.YELLOW, 4, 4, 6));
				GameManager.teams.add(new BedWarsTeam("Green", "§aGreen §7| §a", "§a", Color.LIME, 4, 5, 7));
				
				for (BedWarsTeam team : GameManager.teams) {
					team.setSpawnLocation(GameFileUtils.getLocation(GameManager.map, "Spawn" + team.getTeamName().toUpperCase()));
					team.setBedTop(GameFileUtils.getBlockLocation(GameManager.map, "Bed" + team.getTeamName().toUpperCase()));
					team.setBedBottom(team.getBedTop().getBlock().getRelative(GameFileUtils.getBedFacing(GameManager.map, "Bed" + team.getTeamName().toUpperCase()).getOppositeFace()).getLocation());
					team.placeBed();
				}
			} else if(gameFormat.equalsIgnoreCase("2x1")) {
				GameManager.teams.add(new BedWarsTeam("Blue", "§9Blue §7| §9", "§9", Color.AQUA, 1, 11, 1));
				GameManager.teams.add(new BedWarsTeam("Red", "§cRed §7| §c", "§c", Color.RED, 1, 14, 2));
				
				for (BedWarsTeam team : GameManager.teams) {
					team.setSpawnLocation(GameFileUtils.getLocation(GameManager.map, "Spawn" + team.getTeamName().toUpperCase()));
					team.setBedTop(GameFileUtils.getBlockLocation(GameManager.map, "Bed" + team.getTeamName().toUpperCase()));
					team.setBedBottom(team.getBedTop().getBlock().getRelative(GameFileUtils.getBedFacing(GameManager.map, "Bed" + team.getTeamName().toUpperCase()).getOppositeFace()).getLocation());
					team.placeBed();
				}
			}
		}
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				for (World w : Bukkit.getWorlds()) {
					w.setStorm(false);
					w.setThundering(false);
					w.setTime(6000);
				}
				
				ScoreboardUtils.updateScoreboard();
			}
		}, 20, 20);
		
		Utils.sendConsole("§7The plugin has been§8: §aENABLED");
	}
	
	@Override
	public void onDisable() {
		for (Player all : Bukkit.getOnlinePlayers())
			all.kickPlayer("§cDer Server startet nun neu");
		
		for (World w : Bukkit.getWorlds()) {
			for (Entity e : w.getEntities()) {
				if(e.getType().equals(EntityType.DROPPED_ITEM))
					e.remove();
			}
		}
		
		for (Location loc : GameManager.buildedBlocks)
			loc.getWorld().getBlockAt(loc).setType(Material.AIR);
		
		for (Location loc : GameManager.structureBuildedBlocks.keySet())
			loc.getWorld().getBlockAt(loc).setType(GameManager.structureBuildedBlocks.get(loc));
		
		Utils.sendConsole("§7The plugin has been§8: §cDISABLED");
	}
	
}
