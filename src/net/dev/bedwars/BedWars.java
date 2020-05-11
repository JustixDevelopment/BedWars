package net.dev.bedwars;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.dev.bedwars.commands.BedWarsCommand;
import net.dev.bedwars.commands.ForceMapCommand;
import net.dev.bedwars.commands.StartCommand;
import net.dev.bedwars.game.BedWarsTeam;
import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.game.ScoreboardUtils;
import net.dev.bedwars.listeners.AsyncPlayerChatListener;
import net.dev.bedwars.listeners.BlockBreakListener;
import net.dev.bedwars.listeners.BlockPlaceListener;
import net.dev.bedwars.listeners.EntityDamageByEntityListener;
import net.dev.bedwars.listeners.EntityDamageListener;
import net.dev.bedwars.listeners.EntityExplodeListener;
import net.dev.bedwars.listeners.FoodLevelChangeListener;
import net.dev.bedwars.listeners.InventoryClickListener;
import net.dev.bedwars.listeners.PlayerDeathListener;
import net.dev.bedwars.listeners.PlayerDropItemListener;
import net.dev.bedwars.listeners.PlayerInteractEntityListener;
import net.dev.bedwars.listeners.PlayerInteractListener;
import net.dev.bedwars.listeners.PlayerJoinListener;
import net.dev.bedwars.listeners.PlayerKickListener;
import net.dev.bedwars.listeners.PlayerMoveListener;
import net.dev.bedwars.listeners.PlayerQuitListener;
import net.dev.bedwars.listeners.PlayerRespawnListener;
import net.dev.bedwars.utils.FileUtils;
import net.dev.bedwars.utils.GameFileUtils;
import net.dev.bedwars.utils.ItemUtils;
import net.dev.bedwars.utils.ShopManager;
import net.dev.bedwars.utils.Utils;

public class BedWars extends JavaPlugin {

	private static BedWars instance;
	
	public static BedWars getInstance() {
		return instance;
	}
	
	private FileUtils fileUtils;
	private GameFileUtils gameFileUtils;
	private GameManager gameManager;
	private Utils utils;
	private ScoreboardUtils scoreboardUtils;
	private ItemUtils itemUtils;
	private ShopManager shopManager;
	
	@Override
	public void onEnable() {
		instance = this;
		gameManager = new GameManager();
		utils = new Utils();
		fileUtils = new FileUtils();
		gameFileUtils = new GameFileUtils();
		scoreboardUtils = new ScoreboardUtils();
		itemUtils = new ItemUtils();
		shopManager = new ShopManager();
		
		getCommand("bedwars").setExecutor(new BedWarsCommand());
		getCommand("start").setExecutor(new StartCommand());
		getCommand("forcemap").setExecutor(new ForceMapCommand());
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerJoinListener(), this);
		pm.registerEvents(new PlayerQuitListener(), this);
		pm.registerEvents(new PlayerKickListener(), this);
		pm.registerEvents(new PlayerMoveListener(), this);
		pm.registerEvents(new AsyncPlayerChatListener(), this);
		pm.registerEvents(new BlockBreakListener(), this);
		pm.registerEvents(new BlockPlaceListener(), this);
		pm.registerEvents(new EntityDamageListener(), this);
		pm.registerEvents(new EntityDamageByEntityListener(), this);
		pm.registerEvents(new EntityExplodeListener(), this);
		pm.registerEvents(new FoodLevelChangeListener(), this);
		pm.registerEvents(new PlayerInteractListener(), this);
		pm.registerEvents(new PlayerInteractEntityListener(), this);
		pm.registerEvents(new InventoryClickListener(), this);
		pm.registerEvents(new PlayerDropItemListener(), this);
		pm.registerEvents(new PlayerDeathListener(), this);
		pm.registerEvents(new PlayerRespawnListener(), this);
		
		if(!(gameManager.getMap().equalsIgnoreCase("NONE"))) {
			String gameFormat = fileUtils.getConfigString("Settings.GameFormat");
			
			if(gameFormat.equalsIgnoreCase("8x1")) {
				gameManager.getTeams().add(new BedWarsTeam("Blue", "§9Blau §7| §9", "§9", Color.AQUA, 1, 11, 1));
				gameManager.getTeams().add(new BedWarsTeam("Red", "§cRot §7| §c", "§c", Color.RED, 1, 14, 2));
				gameManager.getTeams().add(new BedWarsTeam("White", "§fWeiß §7| §f", "§f", Color.WHITE, 1, 0, 3));
				gameManager.getTeams().add(new BedWarsTeam("Black", "§0Schwarz §7| §0", "§0", Color.BLACK, 1, 15, 4));
				gameManager.getTeams().add(new BedWarsTeam("Pink", "§dPink §7| §d", "§d", Color.FUCHSIA, 1, 6, 5));
				gameManager.getTeams().add(new BedWarsTeam("Yellow", "§eGelb §7| §e", "§e", Color.YELLOW, 1, 4, 6));
				gameManager.getTeams().add(new BedWarsTeam("Green", "§aGrün §7| §a", "§a", Color.LIME, 1, 5, 7));
				gameManager.getTeams().add(new BedWarsTeam("Purple", "§5Violett §7| §5", "§5", Color.PURPLE, 1, 10, 8));
			} else if(gameFormat.equalsIgnoreCase("8x2")) {
				gameManager.getTeams().add(new BedWarsTeam("Blue", "§9Blau §7| §9", "§9", Color.AQUA, 2, 11, 1));
				gameManager.getTeams().add(new BedWarsTeam("Red", "§cRot §7| §c", "§c", Color.RED, 2, 14, 2));
				gameManager.getTeams().add(new BedWarsTeam("White", "§fWeiß §7| §f", "§f", Color.WHITE, 2, 0, 3));
				gameManager.getTeams().add(new BedWarsTeam("Black", "§0Schwarz §7| §0", "§0", Color.BLACK, 2, 15, 4));
				gameManager.getTeams().add(new BedWarsTeam("Pink", "§dPink §7| §d", "§d", Color.FUCHSIA, 2, 6, 5));
				gameManager.getTeams().add(new BedWarsTeam("Yellow", "§eGelb §7| §e", "§e", Color.YELLOW, 2, 4, 6));
				gameManager.getTeams().add(new BedWarsTeam("Green", "§aGrün §7| §a", "§a", Color.LIME, 2, 5, 7));
				gameManager.getTeams().add(new BedWarsTeam("Purple", "§5Violett §7| §5", "§5", Color.PURPLE, 2, 10, 8));
			} else if(gameFormat.equalsIgnoreCase("4x2")) {
				gameManager.getTeams().add(new BedWarsTeam("Blue", "§9Blau §7| §9", "§9", Color.AQUA, 2, 11, 1));
				gameManager.getTeams().add(new BedWarsTeam("Red", "§cRot §7| §c", "§c", Color.RED, 2, 14, 2));
				gameManager.getTeams().add(new BedWarsTeam("Yellow", "§eGelb §7| §e", "§e", Color.YELLOW, 2, 4, 6));
				gameManager.getTeams().add(new BedWarsTeam("Green", "§aGrün §7| §a", "§a", Color.LIME, 2, 5, 7));
			} else if(gameFormat.equalsIgnoreCase("4x3")) {
				gameManager.getTeams().add(new BedWarsTeam("Blue", "§9Blau §7| §9", "§9", Color.AQUA, 3, 11, 1));
				gameManager.getTeams().add(new BedWarsTeam("Red", "§cRot §7| §c", "§c", Color.RED, 3, 14, 2));
				gameManager.getTeams().add(new BedWarsTeam("Yellow", "§eGelb §7| §e", "§e", Color.YELLOW, 3, 4, 6));
				gameManager.getTeams().add(new BedWarsTeam("Green", "§aGrün §7| §a", "§a", Color.LIME, 3, 5, 7));
			} else if(gameFormat.equalsIgnoreCase("4x4")) {
				gameManager.getTeams().add(new BedWarsTeam("Blue", "§9Blau §7| §9", "§9", Color.AQUA, 4, 11, 1));
				gameManager.getTeams().add(new BedWarsTeam("Red", "§cRot §7| §c", "§c", Color.RED, 4, 14, 2));
				gameManager.getTeams().add(new BedWarsTeam("Yellow", "§eGelb §7| §e", "§e", Color.YELLOW, 4, 4, 6));
				gameManager.getTeams().add(new BedWarsTeam("Green", "§aGrün §7| §a", "§a", Color.LIME, 4, 5, 7));
			} else if(gameFormat.equalsIgnoreCase("2x1")) {
				gameManager.getTeams().add(new BedWarsTeam("Blue", "§9Blau §7| §9", "§9", Color.AQUA, 1, 11, 1));
				gameManager.getTeams().add(new BedWarsTeam("Red", "§cRot §7| §c", "§c", Color.RED, 1, 14, 2));
			} else if(gameFormat.equalsIgnoreCase("2x4")) {
				gameManager.getTeams().add(new BedWarsTeam("Blue", "§9Blau §7| §9", "§9", Color.AQUA, 4, 11, 1));
				gameManager.getTeams().add(new BedWarsTeam("Red", "§cRot §7| §c", "§c", Color.RED, 4, 14, 2));
			} else if(gameFormat.equalsIgnoreCase("2x2")) {
				gameManager.getTeams().add(new BedWarsTeam("Blue", "§9Blau §7| §9", "§9", Color.AQUA, 2, 11, 1));
				gameManager.getTeams().add(new BedWarsTeam("Red", "§cRot §7| §c", "§c", Color.RED, 2, 14, 2));
			}
			
			for (BedWarsTeam team : gameManager.getTeams()) {
				team.setSpawnLocation(gameFileUtils.getLocation(gameManager.getMap(), "Spawn" + team.getTeamName().toUpperCase()));
				team.setBedTop(gameFileUtils.getBlockLocation(gameManager.getMap(), "Bed" + team.getTeamName().toUpperCase()));
				
				if(team.getBedTop() != null)
					team.setBedBottom(team.getBedTop().getBlock().getRelative(gameFileUtils.getBedFacing(gameManager.getMap(), "Bed" + team.getTeamName().toUpperCase()).getOppositeFace()).getLocation());
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
				
				if(!(gameManager.getTeams().isEmpty()))
					scoreboardUtils.updateScoreboard();
			}
		}, 20, 20);
		
		utils.sendConsole("§7The plugin has been§8: §aENABLED");
	}
	
	@Override
	public void onDisable() {
		Bukkit.getOnlinePlayers().forEach(all -> all.kickPlayer("§cDer Server startet nun neu"));
		
		for (World w : Bukkit.getWorlds()) {
			for (Entity e : w.getEntities()) {
				if(e.getType().equals(EntityType.DROPPED_ITEM))
					e.remove();
			}
		}
		
		gameManager.getBuildedBlocks().forEach(loc -> loc.getWorld().getBlockAt(loc).setType(Material.AIR));
		gameManager.getStructureBuildedBlocks().keySet().forEach(loc -> loc.getWorld().getBlockAt(loc).setType(gameManager.getStructureBuildedBlocks().get(loc)));
		
		utils.sendConsole("§7The plugin has been§8: §cDISABLED");
	}
	
	public FileUtils getFileUtils() {
		return fileUtils;
	}
	
	public GameFileUtils getGameFileUtils() {
		return gameFileUtils;
	}
	
	public GameManager getGameManager() {
		return gameManager;
	}
	
	public Utils getUtils() {
		return utils;
	}
	
	public ScoreboardUtils getScoreboardUtils() {
		return scoreboardUtils;
	}
	
	public ItemUtils getItemUtils() {
		return itemUtils;
	}
	
	public ShopManager getShopManager() {
		return shopManager;
	}
}
