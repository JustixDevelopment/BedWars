package net.dev.bedwars.utils;

import org.bukkit.Bukkit;

public class Utils {

	public static String prefix;
	
	public static void sendConsole(String msg) {
		Bukkit.getConsoleSender().sendMessage(prefix + msg);
	}
	
	public static void broadcastMessage(String msg) {
		Bukkit.getOnlinePlayers().forEach(all -> all.sendMessage(msg));
	}

}
