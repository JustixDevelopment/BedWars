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

import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.utils.GameFileUtils;
import net.dev.bedwars.utils.Utils;

public class BedWarsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(p.hasPermission("bedwars.setup")) {
				if(args.length >= 1) {
					if(args[0].equalsIgnoreCase("setlobby")) {
						GameFileUtils.setLocation("Lobby", p.getLocation());
						
						p.sendMessage(Utils.prefix + "§7Die Position §eLobby §7wurde §aerfolgreich §7gesetzt");
					} else if(args[0].equalsIgnoreCase("createShop")) {
						Villager v = (Villager) p.getWorld().spawnEntity(GameFileUtils.getBetterLocation(p.getLocation()), EntityType.VILLAGER);
						
						v.setCustomName("§6§lShop");
						v.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255));
						v.setVelocity(new Vector(0, 0, 0));
						
						p.sendMessage(Utils.prefix + "§7Der Shop §7wurde §aerfolgreich §7erstellt");
					} else if(args.length >= 2) {
						if(args[0].equalsIgnoreCase("createMap")) {
							List<String> maps = GameFileUtils.cfg.getStringList("Maps");
							
							if(!(maps.contains(args[1].toUpperCase()))) {
								maps.add(args[1].toUpperCase());
								GameFileUtils.cfg.set("Maps", maps);
								GameFileUtils.saveFiles();
								
								p.sendMessage(Utils.prefix + "§7Die Map §e" + args[1].toUpperCase() + " §7wurde §aerfolgreich §7erstellt");
							} else {
								p.sendMessage(Utils.prefix + "§7Die Map §e" + args[1].toUpperCase() + " §7existiert §cbereits");
							}
						} else if(args[0].equalsIgnoreCase("deleteMap")) {
							List<String> maps = GameFileUtils.cfg.getStringList("Maps");
							
							if(maps.contains(args[1].toUpperCase())) {
								maps.remove(args[1].toUpperCase());
								GameFileUtils.cfg.set("Maps", maps);
								
								String path = "Locations." + args[1].toUpperCase();
								
								GameFileUtils.cfg.set(path, null);
								GameFileUtils.saveFiles();
								
								p.sendMessage(Utils.prefix + "§7Die Map §e" + args[1].toUpperCase() + " §7wurde §aerfolgreich §7gelöscht");
							} else {
								p.sendMessage(Utils.prefix + "§7Die Map §e" + args[1].toUpperCase() + " §7existiert §cnicht");
							}
						} else if(args.length >= 3) {
							if(args[0].equalsIgnoreCase("setSpawn")) {
								if(GameFileUtils.cfg.getStringList("Maps").contains(args[1].toUpperCase())) {
									if(GameManager.getTeamByName(args[2]) != null) {
										GameFileUtils.setLocation(GameManager.map, "Spawn" + args[2].toUpperCase(), p.getLocation());
										
										p.sendMessage(Utils.prefix + "§7Die Position §eSpawn-" + args[2].toUpperCase() + " §7wurde §aerfolgreich §7gesetzt");
									} else {
										p.sendMessage(Utils.prefix + "§7Das Team §e" + args[2].toUpperCase() + " §7existiert §cnicht");
									}
								} else {
									p.sendMessage(Utils.prefix + "§7Die Map §e" + args[1].toUpperCase() + " §7existiert §cnicht");
								}
							} else if(args[0].equalsIgnoreCase("createSpawner")) {
								if(GameFileUtils.cfg.getStringList("Maps").contains(args[1].toUpperCase())) {
									if(args[2].equalsIgnoreCase("BRONZE")) {
										int count = 0;
										
										if(GameFileUtils.cfg.contains("Locations." + args[1].toUpperCase() + ".BronzeCount"))
											count = GameFileUtils.cfg.getInt("Locations." + args[1].toUpperCase() + ".BronzeCount");
										
										GameFileUtils.cfg.set("Locations." + args[1].toUpperCase() + ".BronzeCount", count + 1);
										GameFileUtils.saveFiles();
										
										GameFileUtils.setBlockLocation(args[1].toUpperCase(), "Bronze-" + (count + 1), p.getLocation());
										
										p.sendMessage(Utils.prefix + "§7Die Position §eBronze-" + (count + 1) + " §7wurde §aerfolgreich §7gesetzt");
									} else if(args[2].equalsIgnoreCase("IRON")) {
										int count = 0;
										
										if(GameFileUtils.cfg.contains("Locations." + args[1].toUpperCase() + ".IronCount"))
											count = GameFileUtils.cfg.getInt("Locations." + args[1].toUpperCase() + ".IronCount");
										
										GameFileUtils.cfg.set("Locations." + args[1].toUpperCase() + ".IronCount", count + 1);
										GameFileUtils.saveFiles();
										
										GameFileUtils.setBlockLocation(args[1].toUpperCase(), "Iron-" + (count + 1), p.getLocation());
										
										p.sendMessage(Utils.prefix + "§7Die Position §eIron-" + (count + 1) + " §7wurde §aerfolgreich §7gesetzt");
									} else if(args[2].equalsIgnoreCase("GOLD")) {
										int count = 0;
										
										if(GameFileUtils.cfg.contains("Locations." + args[1].toUpperCase() + ".GoldCount"))
											count = GameFileUtils.cfg.getInt("Locations." + args[1].toUpperCase() + ".GoldCount");
										
										GameFileUtils.cfg.set("Locations." + args[1].toUpperCase() + ".GoldCount", count + 1);
										GameFileUtils.saveFiles();
										
										GameFileUtils.setBlockLocation(args[1].toUpperCase(), "Gold-" + (count + 1), p.getLocation());
										
										p.sendMessage(Utils.prefix + "§7Die Position §eGold-" + (count + 1) + " §7wurde §aerfolgreich §7gesetzt");
									}
								} else {
									p.sendMessage(Utils.prefix + "§7Die Map §e" + args[1].toUpperCase() + " §7existiert §cnicht");
								}
							} else if(args.length >= 4) {
								if(args[0].equalsIgnoreCase("setBed")) {
									if(GameFileUtils.cfg.getStringList("Maps").contains(args[1].toUpperCase())) {
										if(GameManager.getTeamByName(args[2]) != null) {
											if(args[3].equalsIgnoreCase("NORTH") || args[3].equalsIgnoreCase("EAST") || args[3].equalsIgnoreCase("SOUTH") || args[3].equalsIgnoreCase("WEST")) {
												GameFileUtils.setBedLocation(GameManager.map, "Bed" + args[2].toUpperCase(), p.getLocation(), args[3].toUpperCase());
												
												p.sendMessage(Utils.prefix + "§7Die Position §eBed-" + args[2].toUpperCase() + " §7wurde §aerfolgreich §7gesetzt");
											} else {
												p.sendMessage(Utils.prefix + "§7Das Facing §e" + args[3].toUpperCase() + " §7existiert §cnicht");
												p.sendMessage(Utils.prefix + "§7Alle Facings§8: §eNORTH | EAST | SOUTH | WEST");
											}
										} else {
											p.sendMessage(Utils.prefix + "§7Das Team §e" + args[2].toUpperCase() + " §7existiert §cnicht");
										}
									} else {
										p.sendMessage(Utils.prefix + "§7Die Map §e" + args[1].toUpperCase() + " §7existiert §cnicht");
									}
								} else {
									sendHelp(p);
								}
							} else {
								sendHelp(p);
							}
						} else {
							sendHelp(p);
						}
					} else {
						sendHelp(p);
					}
				} else {
					sendHelp(p);
				}
			} else {
				p.sendMessage(Utils.prefix + "§cYou do not have permission to perform this command");
			}
		} else {
			Utils.sendConsole("§cOnly players can perform this command");
		}
		
		return true;
	}

	private void sendHelp(Player p) {
		p.sendMessage(Utils.prefix + "§7/bedwars «setLobby»");
		p.sendMessage(Utils.prefix + "§7/bedwars «createShop»");
		p.sendMessage(Utils.prefix + "§7/bedwars «createMap|deleteMap» «Name»");
		p.sendMessage(Utils.prefix + "§7/bedwars «setSpawn» «Map» «TeamName»");
		p.sendMessage(Utils.prefix + "§7/bedwars «createSpawner» «Map» «BRONZE | IRON | GOLD»");
		p.sendMessage(Utils.prefix + "§7/bedwars «setBed» «Map» «TeamName» «Facing»");
	}

}
