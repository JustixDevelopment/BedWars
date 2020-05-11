package net.dev.bedwars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dev.bedwars.BedWars;
import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.utils.Utils;

public class StartCommand implements CommandExecutor {

	private BedWars bedWars;
	private Utils utils;
	
	public StartCommand() {
		this.bedWars = BedWars.getInstance();
		this.utils = bedWars.getUtils();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		GameManager gameManager = bedWars.getGameManager();
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			String prefix = utils.getPrefix();
			
			if(p.hasPermission("bedwars.start")) {
				if(!(gameManager.isRunning()) && !(gameManager.isGameStarting())) {
					gameManager.setIndex(10);
					
					p.sendMessage(prefix + "§7Das Spiel wird §agestartet");
				} else
					p.sendMessage(prefix + "§7Das Spiel startet §cbereits");
			} else
				p.sendMessage(prefix + "§cYou do not have permission to perform this command");
		} else
			utils.sendConsole("§cOnly players can perform this command");
		
		return true;
	}

}
