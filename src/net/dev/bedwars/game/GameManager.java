package net.dev.bedwars.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.dev.bedwars.BedWars;
import net.dev.bedwars.utils.GameFileUtils;
import net.dev.bedwars.utils.Utils;

public class GameManager {

	private HashMap<String, Inventory> teamChests = new HashMap<>();
	private HashMap<Location, Inventory> chests = new HashMap<>();
	private HashMap<Location, Material> structureBuildedBlocks = new HashMap<>();
	
	private ArrayList<Player> isGliding = new ArrayList<>();
	private ArrayList<Location> buildedBlocks = new ArrayList<>(); 
	private ArrayList<UUID> inGame = new ArrayList<>();
	private ArrayList<BedWarsTeam> teams = new ArrayList<>();
	
	private boolean running;
	private boolean countDownStarted;
	private int countDownTask;
	private int index = 60;
	private boolean gameStarting;
	private boolean ending;
	
	private HashMap<UUID, Boolean> goldVoted = new HashMap<>();
	private boolean spawnGold = true;
	private int goldVotesYes = 0;
	private int goldVotesNo = 0;
	
	private String map = "NONE";
	private boolean isForceMap = false;
	private HashMap<UUID, String> mapsVoted = new HashMap<>();
	private HashMap<String, Integer> maps = new HashMap<>();
	
	public BedWarsTeam getTeam(Player p) {
		BedWarsTeam team = null;
		
		for (BedWarsTeam bedWarsTeam : teams) {
			if(bedWarsTeam.isTeamMember(p))
				team = bedWarsTeam;
		}
		
		return team;
	}
	
	public BedWarsTeam getTeamByName(String name) {
		BedWarsTeam team = null;
		
		for (BedWarsTeam bedWarsTeam : teams) {
			if(bedWarsTeam.getTeamName().equalsIgnoreCase(name))
				team = bedWarsTeam;
		}
		
		return team;
	}
	
	public BedWarsTeam getFreeTeam() {
		ArrayList<BedWarsTeam> sortedTeams = new ArrayList<>(teams);
		
		Collections.sort(sortedTeams, new Comparator<BedWarsTeam>() {
			
			@Override
			public int compare(BedWarsTeam team1, BedWarsTeam team2) {
				return ((team1.getMembers().size() > team2.getMembers().size()) ? 1 : ((team1.getMembers().size() < team2.getMembers().size()) ? -1 : 0));
			}
			
		});
		
		return sortedTeams.get(0);
	}

	public void kickPlayerFromGame(Player p) {
		BedWars bedWars = BedWars.getInstance();
		Utils utils = bedWars.getUtils();
		
		String prefix = utils.getPrefix();
		BedWarsTeam team = getTeam(p);
		
		if(team != null)
			team.removeMember(p);
		
		if(running) {
			p.setGameMode(GameMode.SPECTATOR);
			p.teleport(team.getSpawnLocation());
			p.setExp(0F);
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			
			for (Player all : Bukkit.getOnlinePlayers()) {
				if(inGame.contains(all.getUniqueId()))
					all.hidePlayer(p);
			}
			
			if(team.getMembers().size() == 0)
				utils.broadcastMessage(prefix + "§4Das Team " + team.getColor() + team.getTeamName() + " §4wurde vernichtet");
			
			inGame.remove(p.getUniqueId());
			
			int livingCount = 0;
			
			for (BedWarsTeam bedWarsTeam : teams) {
				if(bedWarsTeam.getMembers().size() >= 1)
					livingCount++;
			}
			
			if(livingCount == 1) {
				running = false;
				ending = true;
				
				BedWarsTeam winnerTeam = null;
				
				for (BedWarsTeam bedWarsTeam : teams) {
					if(bedWarsTeam.getMembers().size() >= 1)
						winnerTeam = bedWarsTeam;
				}
				
				utils.broadcastMessage(prefix + "§7Das Team " + winnerTeam.getColor() + winnerTeam.getTeamName() + " §7hat das Spiel gewonnen");
				
				for (Player all : Bukkit.getOnlinePlayers()) {
					all.teleport(bedWars.getGameFileUtils().getLocation("Lobby"));
					all.setGameMode(GameMode.ADVENTURE);
					all.getInventory().clear();
					all.getInventory().setArmorContents(null);
					all.setHealth(20);
					all.setFoodLevel(20);
					
					for (Player all2 : Bukkit.getOnlinePlayers())
						all.showPlayer(all2);
				}
				
				Bukkit.getScheduler().scheduleSyncRepeatingTask(bedWars, new Runnable() {
					
					int i = 15;
					
					@Override
					public void run() {
						if((i == 15) || (i == 10) || (i == 5) || (i == 4) || (i == 3) || (i == 2) || (i == 1))
							utils.broadcastMessage(prefix + "§7Der Server startet in §e" + i + " Sekunden §7neu");
						else if(i == 0) {
							utils.broadcastMessage(prefix + "§7Der Server startet §ejetzt §7neu");
							
							for (Player all : Bukkit.getOnlinePlayers())
								all.kickPlayer("§cDas Spiel ist vorbei");
							
							Bukkit.getScheduler().cancelAllTasks();
							Bukkit.reload();
						}
						
						i--;
					}
				}, 20, 20);
			}
		} else if(!(ending)) {
			if(mapsVoted.containsKey(p.getUniqueId())) {
				String votedMap = mapsVoted.get(p.getUniqueId());
				
				maps.put(votedMap, maps.get(votedMap) - 1);
				mapsVoted.remove(p.getUniqueId());
			}
			
			if(goldVoted.containsKey(p.getUniqueId())) {
				if(goldVoted.get(p.getUniqueId()))
					goldVotesYes--;
				else
					goldVotesNo--;
				
				goldVoted.remove(p.getUniqueId());
			}
			
			if(countDownStarted && ((Bukkit.getOnlinePlayers().size() - 1) < bedWars.getFileUtils().getConfig().getInt("Settings.PlayersNeededToStart"))) {
				for (Player all : Bukkit.getOnlinePlayers())
					all.setLevel(0);
				
				gameStarting = false;
				countDownStarted = false;
				Bukkit.getScheduler().cancelTask(countDownTask);
			}
		}
	}

	public void startGame() {
		BedWars bedWars = BedWars.getInstance();
		GameFileUtils gameFileUtils = bedWars.getGameFileUtils();
		Utils utils = bedWars.getUtils();
		
		String prefix = utils.getPrefix();
		
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
			if(getTeam(all) == null)
				getFreeTeam().addMember(all);
			
			inGame.add(all.getUniqueId());
		}
		
		utils.broadcastMessage(prefix + "§7Map§8: §e" + map);
		
		World w = teams.get(0).getSpawnLocation().getWorld();
		
		for (BedWarsTeam team : teams) {
			boolean empty = true;
			
			for (UUID uuid : team.getMembers()) {
				Player t = Bukkit.getPlayer(uuid);
				
				t.teleport(team.getSpawnLocation());
				t.setHealth(20);
				t.setFoodLevel(20);
				t.getInventory().clear();
				t.getInventory().setArmorContents(null);
				
				t.sendMessage(prefix + "§7Du bist in Team " + team.getColor() + team.getTeamName());
				empty = false;
			}
			
			if(!(empty))
				team.placeBed();
		}
		
		countDownStarted = false;
		running = true;
		bedWars.getScoreboardUtils().setScoreboards(new HashMap<>());
		
		Bukkit.getScheduler().cancelTask(countDownTask);
		
		countDownTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(bedWars, new Runnable() {
			
			int i = 0;
			
			@Override
			public void run() {
				i++;
				
				if(running) {
					if((i == 30) || (i == 60)) {
						for (int i = 1; i <= gameFileUtils.getConfig().getInt("Locations." + map + ".IronCount"); i++) {
							if(gameFileUtils.getConfig().contains("Locations." + map + ".Iron-" + i))
								w.dropItemNaturally(gameFileUtils.getBlockLocation(map, "Iron-" + i), new ItemStack(Material.IRON_INGOT));
						}
					}
					
					if(i == 60) {
						if(spawnGold) {
							for (int i = 1; i <= gameFileUtils.getConfig().getInt("Locations." + map + ".GoldCount"); i++) {
								if(gameFileUtils.getConfig().contains("Locations." + map + ".Gold-" + i))
									w.dropItemNaturally(gameFileUtils.getBlockLocation(map, "Gold-" + i), new ItemStack(Material.GOLD_INGOT));
							}
						}
						
						i = 0;
					}
					
					for (int i = 1; i <= gameFileUtils.getConfig().getInt("Locations." + map + ".BronzeCount"); i++) {
						if(gameFileUtils.getConfig().contains("Locations." + map + ".Bronze-" + i))
							w.dropItemNaturally(gameFileUtils.getBlockLocation(map, "Bronze-" + i), new ItemStack(Material.CLAY_BRICK));
					}
				} else
					Bukkit.getScheduler().cancelTask(countDownTask);
			}
		}, 10, 10);
	}
	
	public ArrayList<BedWarsTeam> getTeams() {
		return teams;
	}
	
	public HashMap<String, Inventory> getTeamChests() {
		return teamChests;
	}
	
	public HashMap<Location, Inventory> getChests() {
		return chests;
	}
	
	public HashMap<Location, Material> getStructureBuildedBlocks() {
		return structureBuildedBlocks;
	}
	
	public ArrayList<Player> getIsGliding() {
		return isGliding;
	}
	
	public ArrayList<Location> getBuildedBlocks() {
		return buildedBlocks;
	}
	
	public ArrayList<UUID> getInGame() {
		return inGame;
	}
	
	public boolean isCountDownStarted() {
		return countDownStarted;
	}
	
	public boolean isGameStarting() {
		return gameStarting;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public boolean isEnding() {
		return ending;
	}
	
	public int getIndex() {
		return index;
	}
	
	public HashMap<UUID, Boolean> getGoldVoted() {
		return goldVoted;
	}
	
	public HashMap<UUID, String> getMapsVoted() {
		return mapsVoted;
	}
	
	public HashMap<String, Integer> getMaps() {
		return maps;
	}
	
	public int getCountDownTask() {
		return countDownTask;
	}
	
	public String getMap() {
		return map;
	}

	public int getGoldVotesNo() {
		return goldVotesNo;
	}
	
	public int getGoldVotesYes() {
		return goldVotesYes;
	}
	
	public boolean isSpawnGold() {
		return spawnGold;
	}
	
	public boolean isForceMap() {
		return isForceMap;
	}
	
	public void setMap(String map) {
		this.map = map;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void setCountDownStarted(boolean countDownStarted) {
		this.countDownStarted = countDownStarted;
	}

	public void setCountDownTask(int countDownTask) {
		this.countDownTask = countDownTask;
	}

	public void setInGame(ArrayList<UUID> inGame) {
		this.inGame = inGame;
	}

	public void setGameStarting(boolean gameStarting) {
		this.gameStarting = gameStarting;
	}

	public void setGoldVotesNo(int goldVotesNo) {
		this.goldVotesNo = goldVotesNo;
	}
	
	public void setGoldVotesYes(int goldVotesYes) {
		this.goldVotesYes = goldVotesYes;
	}
	
	public void setForceMap(boolean isForceMap) {
		this.isForceMap = isForceMap;
	}
	
}
