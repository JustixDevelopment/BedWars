package net.dev.bedwars.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.dev.bedwars.BedWars;
import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.utils.GameFileUtils;
import net.dev.bedwars.utils.Utils;

public class BedWarsCommand implements CommandExecutor {

	private BedWars bedWars;
	private Utils utils;
	
	public BedWarsCommand() {
		this.bedWars = BedWars.getInstance();
		this.utils = bedWars.getUtils();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		GameFileUtils gameFileUtils = bedWars.getGameFileUtils();
		GameManager gameManager = bedWars.getGameManager();
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			String prefix = utils.getPrefix();
			
			if(p.hasPermission("bedwars.setup")) {
				if(args.length >= 1) {
					if(args[0].equalsIgnoreCase("setlobby")) {
						gameFileUtils.setLocation("Lobby", p.getLocation());
						
						p.sendMessage(prefix + "§7Die Position §eLobby §7wurde §aerfolgreich §7gesetzt");
					} else if(args[0].equalsIgnoreCase("createShop")) {
						Villager v = (Villager) p.getWorld().spawnEntity(gameFileUtils.getBetterLocation(p.getLocation()), EntityType.VILLAGER);
						
						v.setCustomName("§6§lShop");
						v.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255));
						v.setVelocity(new Vector(0, 0, 0));
						
						p.sendMessage(prefix + "§7Der Shop §7wurde §aerfolgreich §7erstellt");
					} else if(args.length >= 2) {
						if(args[0].equalsIgnoreCase("createMap")) {
							List<String> maps = gameFileUtils.getConfig().getStringList("Maps");
							
							if(!(maps.contains(args[1].toUpperCase()))) {
								maps.add(args[1].toUpperCase());
								gameFileUtils.getConfig().set("Maps", maps);
								gameFileUtils.saveFiles();
								
								p.sendMessage(prefix + "§7Die Map §e" + args[1].toUpperCase() + " §7wurde §aerfolgreich §7erstellt");
							} else
								p.sendMessage(prefix + "§7Die Map §e" + args[1].toUpperCase() + " §7existiert §cbereits");
						} else if(args[0].equalsIgnoreCase("deleteMap")) {
							List<String> maps = gameFileUtils.getConfig().getStringList("Maps");
							
							if(maps.contains(args[1].toUpperCase())) {
								maps.remove(args[1].toUpperCase());
								gameFileUtils.getConfig().set("Maps", maps);
								
								String path = "Locations." + args[1].toUpperCase();
								
								gameFileUtils.getConfig().set(path, null);
								gameFileUtils.saveFiles();
								
								p.sendMessage(prefix + "§7Die Map §e" + args[1].toUpperCase() + " §7wurde §aerfolgreich §7gelöscht");
							} else
								p.sendMessage(prefix + "§7Die Map §e" + args[1].toUpperCase() + " §7existiert §cnicht");
						} else if(args.length >= 3) {
							if(args[0].equalsIgnoreCase("setSpawn")) {
								if(gameFileUtils.getConfig().getStringList("Maps").contains(args[1].toUpperCase())) {
									if(gameManager.getTeamByName(args[2]) != null) {
										gameFileUtils.setLocation(gameManager.getMap(), "Spawn" + args[2].toUpperCase(), p.getLocation());
										
										p.sendMessage(prefix + "§7Die Position §eSpawn-" + args[2].toUpperCase() + " §7wurde §aerfolgreich §7gesetzt");
									} else
										p.sendMessage(prefix + "§7Das Team §e" + args[2].toUpperCase() + " §7existiert §cnicht");
								} else
									p.sendMessage(prefix + "§7Die Map §e" + args[1].toUpperCase() + " §7existiert §cnicht");
							} else if(args[0].equalsIgnoreCase("createSpawner")) {
								if(gameFileUtils.getConfig().getStringList("Maps").contains(args[1].toUpperCase())) {
									if(args[2].equalsIgnoreCase("BRONZE")) {
										int count = 0;
										
										if(gameFileUtils.getConfig().contains("Locations." + args[1].toUpperCase() + ".BronzeCount"))
											count = gameFileUtils.getConfig().getInt("Locations." + args[1].toUpperCase() + ".BronzeCount");
										
										gameFileUtils.getConfig().set("Locations." + args[1].toUpperCase() + ".BronzeCount", count + 1);
										gameFileUtils.saveFiles();
										
										gameFileUtils.setBlockLocation(args[1].toUpperCase(), "Bronze-" + (count + 1), p.getLocation());
										
										p.sendMessage(prefix + "§7Die Position §eBronze-" + (count + 1) + " §7wurde §aerfolgreich §7gesetzt");
									} else if(args[2].equalsIgnoreCase("IRON")) {
										int count = 0;
										
										if(gameFileUtils.getConfig().contains("Locations." + args[1].toUpperCase() + ".IronCount"))
											count = gameFileUtils.getConfig().getInt("Locations." + args[1].toUpperCase() + ".IronCount");
										
										gameFileUtils.getConfig().set("Locations." + args[1].toUpperCase() + ".IronCount", count + 1);
										gameFileUtils.saveFiles();
										
										gameFileUtils.setBlockLocation(args[1].toUpperCase(), "Iron-" + (count + 1), p.getLocation());
										
										p.sendMessage(prefix + "§7Die Position §eIron-" + (count + 1) + " §7wurde §aerfolgreich §7gesetzt");
									} else if(args[2].equalsIgnoreCase("GOLD")) {
										int count = 0;
										
										if(gameFileUtils.getConfig().contains("Locations." + args[1].toUpperCase() + ".GoldCount"))
											count = gameFileUtils.getConfig().getInt("Locations." + args[1].toUpperCase() + ".GoldCount");
										
										gameFileUtils.getConfig().set("Locations." + args[1].toUpperCase() + ".GoldCount", count + 1);
										gameFileUtils.saveFiles();
										
										gameFileUtils.setBlockLocation(args[1].toUpperCase(), "Gold-" + (count + 1), p.getLocation());
										
										p.sendMessage(prefix + "§7Die Position §eGold-" + (count + 1) + " §7wurde §aerfolgreich §7gesetzt");
									}
								} else
									p.sendMessage(prefix + "§7Die Map §e" + args[1].toUpperCase() + " §7existiert §cnicht");
							} else if(args.length >= 4) {
								if(args[0].equalsIgnoreCase("setBed")) {
									if(gameFileUtils.getConfig().getStringList("Maps").contains(args[1].toUpperCase())) {
										if(gameManager.getTeamByName(args[2]) != null) {
											if(args[3].equalsIgnoreCase("NORTH") || args[3].equalsIgnoreCase("EAST") || args[3].equalsIgnoreCase("SOUTH") || args[3].equalsIgnoreCase("WEST")) {
												gameFileUtils.setBedLocation(gameManager.getMap(), "Bed" + args[2].toUpperCase(), p.getLocation(), args[3].toUpperCase());
												
												p.sendMessage(prefix + "§7Die Position §eBed-" + args[2].toUpperCase() + " §7wurde §aerfolgreich §7gesetzt");
											} else {
												p.sendMessage(prefix + "§7Das Facing §e" + args[3].toUpperCase() + " §7existiert §cnicht");
												p.sendMessage(prefix + "§7Alle Facings§8: §eNORTH | EAST | SOUTH | WEST");
											}
										} else
											p.sendMessage(prefix + "§7Das Team §e" + args[2].toUpperCase() + " §7existiert §cnicht");
									} else
										p.sendMessage(prefix + "§7Die Map §e" + args[1].toUpperCase() + " §7existiert §cnicht");
								} else
									sendHelp(p);
							} else
								sendHelp(p);
						} else
							sendHelp(p);
					} else
						sendHelp(p);
				} else
					sendHelp(p);
			} else
				p.sendMessage(prefix + "§cYou do not have permission to perform this command");
		} else
			utils.sendConsole("§cOnly players can perform this command");
		
		return true;
	}

	private void sendHelp(Player p) {
		String prefix = utils.getPrefix();
		
		p.sendMessage(prefix + "§7/bedwars «setLobby»");
		p.sendMessage(prefix + "§7/bedwars «createShop»");
		p.sendMessage(prefix + "§7/bedwars «createMap|deleteMap» «Name»");
		p.sendMessage(prefix + "§7/bedwars «setSpawn» «Map» «TeamName»");
		p.sendMessage(prefix + "§7/bedwars «createSpawner» «Map» «BRONZE | IRON | GOLD»");
		p.sendMessage(prefix + "§7/bedwars «setBed» «Map» «TeamName» «NORTH | EAST | SOUTH | WEST»");
	}

}
