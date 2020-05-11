package net.dev.bedwars.utils;

import org.bukkit.Bukkit;

public class Utils {

	private String prefix;
	
	public void sendConsole(String msg) {
		Bukkit.getConsoleSender().sendMessage(prefix + msg);
	}
	
	public void broadcastMessage(String msg) {
		Bukkit.getOnlinePlayers().forEach(all -> all.sendMessage(msg));
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
