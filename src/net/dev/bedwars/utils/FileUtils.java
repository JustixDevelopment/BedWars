package net.dev.bedwars.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import net.dev.bedwars.BedWars;

public class FileUtils {

	private File directory, file;
	private YamlConfiguration cfg;
	
	public FileUtils() {
		directory = new File("plugins/BedWars/");
		file = new File("plugins/BedWars/config.yml");
		
		if(!(directory.exists()))
			directory.mkdir();
		
		if(!(file.exists())) {
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		}
		
		cfg = YamlConfiguration.loadConfiguration(file);
		cfg.addDefault("Messages.Prefix", "&8[&bBedWars&8] &7");
		cfg.addDefault("Settings.MapName", "NONE");
		cfg.addDefault("Settings.GameFormat", "8x1");
		cfg.addDefault("Settings.PlayersNeededToStart", 2);
		cfg.options().copyDefaults(true);
		saveFiles();
	}
	
	public void saveFiles() {
		BedWars bedWars = BedWars.getInstance();
		
		try {
			cfg.save(file);
		} catch (IOException e) {
		}
		
		bedWars.getUtils().setPrefix(getConfigString("Messages.Prefix"));
		bedWars.getGameManager().setMap(getConfigString("Settings.MapName").toUpperCase());
	}
	
	public String getConfigString(String path) {
		return cfg.getString(path).replace("&", "§");
	}
	
	public YamlConfiguration getConfig() {
		return cfg;
	}
	
}