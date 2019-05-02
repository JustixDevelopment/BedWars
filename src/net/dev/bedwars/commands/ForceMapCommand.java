package net.dev.bedwars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.utils.GameFileUtils;
import net.dev.bedwars.utils.Utils;

public class ForceMapCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(p.hasPermission("bedwars.forcemap")) {
				if(!(GameManager.isForceMap)) {
					if(args.length >= 1) {
						String mapName = args[0].toUpperCase();
						
						if(GameFileUtils.cfg.getStringList("Maps").contains(mapName)) {
							GameManager.map = mapName;
							GameManager.isForceMap = true;
							
							p.sendMessage(Utils.prefix + "§7Die Map wurde §aerfolgreich §7auf §e" + mapName + " §7gesetzt");
						} else {
							p.sendMessage(Utils.prefix + "§7Die Map §e" + mapName + " §7existiert §cnicht");
						}
					} else {
						p.sendMessage(Utils.prefix + "§7/forcemap «Map»");
					}
				} else {
					p.sendMessage(Utils.prefix + "§7Die Map wurde §cbereits §7gesetzt");
				}
			} else {
				p.sendMessage(Utils.prefix + "§cYou do not have permission to perform this command");
			}
		} else {
			Utils.sendConsole("§cOnly players can perform this command");
		}
		
		return true;
	}

}
