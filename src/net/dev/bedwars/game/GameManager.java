package net.dev.bedwars.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.dev.bedwars.main.Main;
import net.dev.bedwars.utils.GameFileUtils;
import net.dev.bedwars.utils.Utils;

public class GameManager {

	public static HashMap<String, Inventory> teamChests = new HashMap<>();
	public static HashMap<Location, Inventory> chests = new HashMap<>();
	public static HashMap<Location, Material> structureBuildedBlocks = new HashMap<>();
	
	public static ArrayList<Player> isGliding = new ArrayList<>();
	public static ArrayList<Location> buildedBlocks = new ArrayList<>(); 
	public static ArrayList<UUID> inGame = new ArrayList<>();
	public static ArrayList<BedWarsTeam> teams = new ArrayList<>();
	
	public static boolean isRunning;
	public static boolean isCountDownStarted;
	public static int countDownTask;
	public static int i;
	public static boolean gameIsStarting;
	public static boolean isEnding;
	
	public static HashMap<UUID, Boolean> goldVoted = new HashMap<>();
	public static boolean spawnGold = true;
	public static int goldVotesYes = 0;
	public static int goldVotesNo = 0;
	
	public static String map = "NONE";
	public static boolean isForceMap = false;
	public static HashMap<UUID, String> mapsVoted = new HashMap<>();
	public static HashMap<String, Integer> maps = new HashMap<>();
	
	public static BedWarsTeam getTeam(Player p) {
		BedWarsTeam team = null;
		
		for (BedWarsTeam bedWarsTeam : teams) {
			if(bedWarsTeam.isTeamMember(p))
				team = bedWarsTeam;
		}
		
		return team;
	}
	
	public static BedWarsTeam getTeamByName(String name) {
		BedWarsTeam team = null;
		
		for (BedWarsTeam bedWarsTeam : teams) {
			if(bedWarsTeam.getTeamName().equalsIgnoreCase(name))
				team = bedWarsTeam;
		}
		
		return team;
	}
	
	public static BedWarsTeam getRandomFreeTeam() {
		BedWarsTeam team = null;
		
		for (BedWarsTeam bedWarsTeam : teams) {
			if(bedWarsTeam.getMembers().size() < bedWarsTeam.getTeamSize()) {
				if(bedWarsTeam.getTeamSize() == 1)
					team = bedWarsTeam;
				else
					team = bedWarsTeam;
			}
				
		}
		
		return team;
	}

	public static void kickPlayerFromGame(Player p) {
		p.setGameMode(GameMode.SPECTATOR);
		p.teleport(teams.get(0).getSpawnLocation());
		p.setExp(0F);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			if(inGame.contains(all.getUniqueId()))
				all.hidePlayer(p);
		}
		
		BedWarsTeam team = getTeam(p);
		team.removeMember(p);
		
		if(team.getMembers().size() == 0)
			Utils.broadcastMessage(Utils.prefix + "§4Das Team " + team.getColor() + team.getTeamName() + " §4wurde vernichtet");
		
		inGame.remove(p.getUniqueId());
		
		int livingCount = 0;
		
		for (BedWarsTeam bedWarsTeam : teams) {
			if(bedWarsTeam.getMembers().size() >= 1)
				livingCount++;
		}
		
		if(livingCount == 1) {
			isRunning = false;
			isEnding = true;
			
			BedWarsTeam winnerTeam = null;
			
			for (BedWarsTeam bedWarsTeam : teams) {
				if(bedWarsTeam.getMembers().size() >= 1)
					winnerTeam = bedWarsTeam;
			}
			
			Utils.broadcastMessage(Utils.prefix + "§7Das Team " + winnerTeam.getColor() + winnerTeam.getTeamName() + " §7hat das Spiel gewonnen");
			
			for (Player all : Bukkit.getOnlinePlayers()) {
				all.teleport(GameFileUtils.getLocation("Lobby"));
				all.setGameMode(GameMode.ADVENTURE);
				all.getInventory().clear();
				all.getInventory().setArmorContents(null);
				all.setHealth(20);
				all.setFoodLevel(20);
			}
			
			Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
				
				int i = 15;
				
				@Override
				public void run() {
					if((i == 15) || (i == 10) || (i == 5) || (i == 4) || (i == 3) || (i == 2) || (i == 1))
						Utils.broadcastMessage(Utils.prefix + "§7Der Server startet in §e" + i + " Sekunden §7neu");
					else if(i == 0) {
						Utils.broadcastMessage(Utils.prefix + "§7Der Server startet §ejetzt §7neu");
						
						for (Player all : Bukkit.getOnlinePlayers()) {
							all.kickPlayer("§cDas Spiel ist vorbei");
						}
						
						Bukkit.getScheduler().cancelAllTasks();
						Bukkit.reload();
					}
					
					i--;
				}
			}, 20, 20);
		}
	}

	public static void startGame() {
		if(goldVotesYes >= goldVotesNo)
			spawnGold = true;
		else
			spawnGold = false;
		
		if(!(isForceMap)) {
			String highestVotedMap = map;
			int highestValue = 0;
			
			for (String mapName : maps.keySet()) {
				if(maps.get(mapName) > highestValue) {
					highestVotedMap = mapName;
					highestValue = maps.get(mapName);
				}
			}
			
			map = highestVotedMap;
		}
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			if(GameManager.getTeam(all) == null) {
				GameManager.getRandomFreeTeam().addMember(all);
			}
			
			GameManager.inGame.add(all.getUniqueId());
		}
		
		World w = teams.get(0).getSpawnLocation().getWorld();
		
		for (BedWarsTeam team : GameManager.teams) {
			for (UUID uuid : team.getMembers()) {
				Player t = Bukkit.getPlayer(uuid);
				
				t.teleport(team.getSpawnLocation());
				t.setHealth(20);
				t.setFoodLevel(20);
				t.getInventory().clear();
				t.getInventory().setArmorContents(null);
				
				t.sendMessage(Utils.prefix + "§7Du bist in Team " + team.getColor() + team.getTeamName());
			}
		}
		
		isRunning = true;
		ScoreboardUtils.scoreboards = new HashMap<>();
		
		Utils.broadcastMessage(Utils.prefix + "§7Map§8: §e" + map);
		
		countDownTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
			
			int i = 0;
			
			@Override
			public void run() {
				i++;
				
				if(isRunning) {
					if((i == 15) || (i == 30)) {
						for (int i = 1; i <= GameFileUtils.cfg.getInt("Locations." + GameManager.map + ".IronCount"); i++) {
							if(GameFileUtils.cfg.contains("Locations." + GameManager.map + ".Iron-" + i)) {
								Item item = w.dropItem(GameFileUtils.getBlockLocation(GameManager.map, "Iron-" + i), new ItemStack(Material.IRON_INGOT));
								item.setVelocity(new Vector(0, 0, 0));
							}
						}
					}
					
					if(i == 30) {
						if(spawnGold) {
							for (int i = 1; i <= GameFileUtils.cfg.getInt("Locations." + GameManager.map + ".GoldCount"); i++) {
								if(GameFileUtils.cfg.contains("Locations." + GameManager.map + ".Gold-" + i)) {
									Item item = w.dropItem(GameFileUtils.getBlockLocation(GameManager.map, "Gold-" + i), new ItemStack(Material.GOLD_INGOT));
									item.setVelocity(new Vector(0, 0, 0));
								}
							}
						}
						
						i = 0;
					}
					
					for (int i = 1; i <= GameFileUtils.cfg.getInt("Locations." + GameManager.map + ".BronzeCount"); i++) {
						if(GameFileUtils.cfg.contains("Locations." + GameManager.map + ".Bronze-" + i)) {
							Item item = w.dropItem(GameFileUtils.getBlockLocation(GameManager.map, "Bronze-" + i), new ItemStack(Material.CLAY_BRICK));
							item.setVelocity(new Vector(0, 0, 0));
						}
					}
				} else {
					Bukkit.getScheduler().cancelTask(countDownTask);
				}
			}
		}, 20, 20);
	}
	
}
