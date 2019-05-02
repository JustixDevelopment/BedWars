package net.dev.bedwars.game;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.dev.bedwars.utils.FileUtils;

public class ScoreboardUtils {

	public static HashMap<String, Scoreboard> scoreboards = new HashMap<>();
	
	public static void updateScoreboard() {
		for (Player all : Bukkit.getOnlinePlayers()) {
			if(all.isOnline()) {
				if(!(scoreboards.containsKey(all.getName())))
					setScoreboard(all);
				
				Scoreboard sb = scoreboards.get(all.getName());
				
				if(GameManager.isRunning) {
					int i = 0;
					
					for (BedWarsTeam team : GameManager.teams) {
						sb.getTeam("" + i).setPrefix(team.getBedSymbol() + " ");
						sb.getObjective(DisplaySlot.SIDEBAR).getScore("§" + i).setScore(team.getMembers().size());
						
						i++;
					}
				} else if(!(GameManager.isEnding)) {
					sb.getTeam("e").setPrefix("§8» §e" + GameManager.map);
					sb.getTeam("e").setSuffix(" §7(" + FileUtils.getConfigString("Settings.GameFormat") + ")");
					sb.getTeam("b").setPrefix("§8» §e" + Bukkit.getOnlinePlayers().size() + "§7/§e" + (GameManager.teams.size() * GameManager.teams.get(0).getTeamSize()));
				}
				
				for (BedWarsTeam team : GameManager.teams) {
					Team t = sb.getTeam("0" + team.getScoreboardCount() + team.getTeamName());
					
					for (UUID uuid : team.getMembers()) {
						Player target = Bukkit.getPlayer(uuid);
						
						if(!(t.hasEntry(target.getName()))) {
							t.addEntry(target.getName());
							
							target.setDisplayName(t.getPrefix() + target.getName() + t.getSuffix());
						}
					}
				}
				
				for (Player all2 : Bukkit.getOnlinePlayers()) {
					if(GameManager.getTeam(all2) == null) {
						Team t = sb.getTeam("10NONE");
						
						if(!(t.hasEntry(all2.getName()))) {
							t.addEntry(all2.getName());
							
							all2.setDisplayName(t.getPrefix() + all2.getName() + t.getSuffix());
						}
					}
				}
			} else
				scoreboards.remove(all.getName());
		}
	}

	private static void setScoreboard(Player p) {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o = sb.registerNewObjective("BedWars", "Scoreboard");

		o.setDisplayName("§b§lBEDWARS");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		if(GameManager.isRunning) {
			registerNewTeam(sb, "r", "§r", "§r", "§r");
			o.getScore("§r").setScore(GameManager.teams.get(0).getTeamSize() + 1);
			
			int i = 0;
			
			for (BedWarsTeam team : GameManager.teams) {
				registerNewTeam(sb, "" + i, team.getBedSymbol() + " ", team.getColor() + team.getTeamName(), "§" + i);
				o.getScore("§" + i).setScore(team.getMembers().size());
				
				i++;
			}
			
			registerNewTeam(sb, "a", "§a", "§a", "§a");
			o.getScore("§a").setScore(-1);
			
			registerNewTeam(sb, "b", "§7Gold§8:", "§b", "§b");
			o.getScore("§b").setScore(-2);
			
			registerNewTeam(sb, "c", GameManager.spawnGold ? "§aAn" : "§cAus", "§c", "§c");
			o.getScore("§c").setScore(-3);
			
			registerNewTeam(sb, "d", "§d", "§d", "§d");
			o.getScore("§d").setScore(-4);
		} else {
			registerNewTeam(sb, "f", "§7Map", "§f", "§f");
			o.getScore("§f").setScore(6);
			
			registerNewTeam(sb, "e", "§8» §e" + GameManager.map, " §7(" + FileUtils.getConfigString("Settings.GameFormat") + ")", "§e");
			o.getScore("§e").setScore(5);
			
			registerNewTeam(sb, "d", "§d", "§d", "§d");
			o.getScore("§d").setScore(4);
			
			registerNewTeam(sb, "c", "§7Online", "§c", "§c");
			o.getScore("§c").setScore(3);
			
			registerNewTeam(sb, "b", "§8» §e" + Bukkit.getOnlinePlayers().size() + "§7/§e" + (GameManager.teams.size() * GameManager.teams.get(0).getTeamSize()), "§b", "§b");
			o.getScore("§b").setScore(2);
			
			registerNewTeam(sb, "a", "§a", "§a", "§a");
			o.getScore("§a").setScore(1);
		}
		
		for (BedWarsTeam team : GameManager.teams) {
			Team t = sb.registerNewTeam("0" + team.getScoreboardCount() + team.getTeamName());
			t.setPrefix(team.getPrefix());
		}
		
		Team t = sb.registerNewTeam("10NONE");
		t.setPrefix("§7");
		
		p.setScoreboard(sb);
		scoreboards.put(p.getName(), sb);
	}

	private static void registerNewTeam(Scoreboard sb, String teamName, String prefix, String suffix, String toAdd) {
		Team t = sb.registerNewTeam(teamName);
		t.setPrefix(prefix);
		t.setSuffix(suffix);
		t.addEntry(toAdd);
	}
	
}
