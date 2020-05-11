package net.dev.bedwars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dev.bedwars.BedWars;
import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.utils.Utils;

public class ForceMapCommand implements CommandExecutor {

	private BedWars bedWars;
	private Utils utils;
	
	public ForceMapCommand() {
		this.bedWars = BedWars.getInstance();
		this.utils = bedWars.getUtils();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		GameManager gameManager = bedWars.getGameManager();
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			String prefix = utils.getPrefix();
			
			if(p.hasPermission("bedwars.forcemap")) {
				if(!(gameManager.isForceMap())) {
					if(args.length >= 1) {
						String mapName = args[0].toUpperCase();
						
						if(bedWars.getGameFileUtils().getConfig().getStringList("Maps").contains(mapName)) {
							gameManager.setMap(mapName);
							gameManager.setForceMap(true);
							
							p.sendMessage(prefix + "§7Die Map wurde §aerfolgreich §7auf §e" + mapName + " §7gesetzt");
						} else
							p.sendMessage(prefix + "§7Die Map §e" + mapName + " §7existiert §cnicht");
					} else
						p.sendMessage(prefix + "§7/forcemap «Map»");
				} else
					p.sendMessage(prefix + "§7Die Map wurde §cbereits §7gesetzt");
			} else
				p.sendMessage(prefix + "§cYou do not have permission to perform this command");
		} else
			utils.sendConsole("§cOnly players can perform this command");
		
		return true;
	}

}
