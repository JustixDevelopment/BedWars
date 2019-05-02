package net.dev.bedwars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dev.bedwars.game.GameManager;
import net.dev.bedwars.utils.Utils;

public class StartCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(p.hasPermission("bedwars.start")) {
				if(!(GameManager.isRunning) && !(GameManager.gameIsStarting)) {
					GameManager.i = 10;
					
					p.sendMessage(Utils.prefix + "§7Das Spiel wird §agestartet");
				} else {
					p.sendMessage(Utils.prefix + "§7Das Spiel startet §cbereits");
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
