package net.dev.bedwars.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import net.dev.bedwars.game.GameManager;

public class FileUtils {

	public static File folder = new File("plugins/BedWars/");
	public static File file = new File("plugins/BedWars/config.yml");
	public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	
	public static void saveFiles() {
		try {
			cfg.save(file);
		} catch (IOException e) {
		}
		
		Utils.prefix = getConfigString("Messages.Prefix");
		GameManager.map = getConfigString("Settings.MapName");
	}
	
	public static void setupFiles() {
		if(!(folder.exists()))
			folder.mkdir();
		
		if(!(file.exists())) {
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		}
		
		cfg.addDefault("Messages.Prefix", "&8[&bBedWars&8] &7");
		cfg.addDefault("Settings.MapName", "NONE");
		cfg.addDefault("Settings.GameFormat", "8x1");
		cfg.addDefault("Settings.PlayersNeededToStart", 2);
		cfg.options().copyDefaults(true);
		saveFiles();
	}
	
	public static String getConfigString(String path) {
		return cfg.getString(path).replace("&", "§");
	}
	
}