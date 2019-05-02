package net.dev.bedwars.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;

public class GameFileUtils {

	public static File folder = new File("plugins/BedWars/");
	public static File file = new File("plugins/BedWars/data.yml");
	public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	
	public static void saveFiles() {
		try {
			cfg.save(file);
		} catch (IOException e) {
		}
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
		
		cfg.addDefault("Maps", new ArrayList<>());
		cfg.options().copyDefaults(true);
		saveFiles();
	}

	public static Location getBetterLocation(Location loc) {
		Location newLoc = new Location(loc.getWorld(), loc.getBlockX() + 0.5, loc.getBlockY(), loc.getBlockZ());
		newLoc.setYaw(Math.round(loc.getYaw() / 45) * 45);
		newLoc.setPitch(Math.round(loc.getPitch() / 45) * 45);
		
		return newLoc;
	}
	
	public static void setLocation(String name, Location loc) {
		cfg.set("Locations." + name + ".X", loc.getBlockX() + 0.5);
		cfg.set("Locations." + name + ".Y", loc.getBlockY());
		cfg.set("Locations." + name + ".Z", loc.getBlockZ() + 0.5);
		cfg.set("Locations." + name + ".Yaw", Math.round(loc.getYaw() / 45) * 45);
		cfg.set("Locations." + name + ".Pitch", Math.round(loc.getPitch() / 45) * 45);
		cfg.set("Locations." + name + ".World", loc.getWorld().getName());
		saveFiles();
	}
	
	public static void setLocation(String mapName, String name, Location loc) {
		cfg.set("Locations." + mapName + "." + name + ".X", loc.getBlockX() + 0.5);
		cfg.set("Locations." + mapName + "." + name + ".Y", loc.getBlockY());
		cfg.set("Locations." + mapName + "." + name + ".Z", loc.getBlockZ() + 0.5);
		cfg.set("Locations." + mapName + "." + name + ".Yaw", Math.round(loc.getYaw() / 45) * 45);
		cfg.set("Locations." + mapName + "." + name + ".Pitch", Math.round(loc.getPitch() / 45) * 45);
		cfg.set("Locations." + mapName + "." + name + ".World", loc.getWorld().getName());
		saveFiles();
	}
	
	public static void setBedLocation(String mapName, String name, Location loc, String facing) {
		cfg.set("Locations." + mapName + "." + name + ".X", loc.getBlockX() + 0.5);
		cfg.set("Locations." + mapName + "." + name + ".Y", loc.getBlockY());
		cfg.set("Locations." + mapName + "." + name + ".Z", loc.getBlockZ() + 0.5);
		cfg.set("Locations." + mapName + "." + name + ".World", loc.getWorld().getName());
		cfg.set("Locations." + mapName + "." + name + ".Facing", facing);
		saveFiles();
	}
	
	public static void setBlockLocation(String mapName, String name, Location loc) {
		cfg.set("Locations." + mapName + "." + name + ".X", loc.getBlockX() + 0.5);
		cfg.set("Locations." + mapName + "." + name + ".Y", loc.getBlockY());
		cfg.set("Locations." + mapName + "." + name + ".Z", loc.getBlockZ() + 0.5);
		cfg.set("Locations." + mapName + "." + name + ".World", loc.getWorld().getName());
		saveFiles();
	}
	
	public static Location getBlockLocation(String mapName, String name) {
		String mainPath = "Locations." + mapName + "." + name;
		
		return (new Location(Bukkit.getWorld(cfg.getString(mainPath + ".World")), cfg.getDouble(mainPath + ".X"), cfg.getDouble(mainPath + ".Y"), cfg.getDouble(mainPath + ".Z")));
	}
	
	public static Location getLocation(String name) {
		String mainPath = "Locations." + name;
		
		Location loc = new Location(Bukkit.getWorld(cfg.getString(mainPath + ".World")), cfg.getDouble(mainPath + ".X"), cfg.getDouble(mainPath + ".Y"), cfg.getDouble(mainPath + ".Z"));
		loc.setYaw((float) cfg.getDouble(mainPath + ".Yaw"));
		loc.setPitch((float) cfg.getDouble(mainPath + ".Pitch"));
		
		return loc;
	}
	
	public static Location getLocation(String mapName, String name) {
		String mainPath = "Locations." + mapName + "." + name;
		
		Location loc = new Location(Bukkit.getWorld(cfg.getString(mainPath + ".World")), cfg.getDouble(mainPath + ".X"), cfg.getDouble(mainPath + ".Y"), cfg.getDouble(mainPath + ".Z"));
		loc.setYaw((float) cfg.getDouble(mainPath + ".Yaw"));
		loc.setPitch((float) cfg.getDouble(mainPath + ".Pitch"));
		
		return loc;
	}
	
	public static BlockFace getBedFacing(String mapName, String name) {
		return BlockFace.valueOf(cfg.getString("Locations." + mapName + "." + name + ".Facing"));
	}
	
}