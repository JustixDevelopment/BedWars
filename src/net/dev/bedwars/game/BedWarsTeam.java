package net.dev.bedwars.game;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.material.Bed;
import org.bukkit.scoreboard.Team;

import net.dev.bedwars.BedWars;

public class BedWarsTeam {

	private String teamName;
	private String prefix;
	private String color;
	private Color armorColor;
	private int teamSize;
	private ArrayList<UUID> members;
	private Location spawnLocation;
	private Location bedTop, bedBottom;
	private int woolColor;
	private int scoreboardCount;
	
	public BedWarsTeam(String teamName, String prefix, String color, Color armorColor, int teamSize, int woolColor, int scoreboardCount) {
		this.teamName = teamName;
		this.prefix = prefix;
		this.color = color;
		this.armorColor = armorColor;
		this.teamSize = teamSize;
		this.woolColor = woolColor;
		this.scoreboardCount = scoreboardCount;
		this.members = new ArrayList<>();
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public String getColor() {
		return color;
	}
	
	public Color getArmorColor() {
		return armorColor;
	}
	
	public int getTeamSize() {
		return teamSize;
	}
	
	public ArrayList<UUID> getMembers() {
		return members;
	}
	
	public boolean isTeamMember(Player p) {
		return members.contains(p.getUniqueId());
	}
	
	public void setSpawnLocation(Location spawnLocation) {
		this.spawnLocation = spawnLocation;
	}
	
	public Location getSpawnLocation() {
		return spawnLocation;
	}
	
	public void setBedTop(Location bedTop) {
		this.bedTop = new Location(bedTop.getWorld(), bedTop.getBlockX(), bedTop.getBlockY(), bedTop.getBlockZ());
	}
	
	public Location getBedTop() {
		return bedTop;
	}
	
	public void setBedBottom(Location bedBottom) {
		this.bedBottom = new Location(bedBottom.getWorld(), bedBottom.getBlockX(), bedBottom.getBlockY(), bedBottom.getBlockZ());
	}
	
	public Location getBedBottom() {
		return bedBottom;
	}
	
	public int getWoolColor() {
		return woolColor;
	}
	
	public int getScoreboardCount() {
		return scoreboardCount;
	}
	
	public String getBedSymbol() {
		return (getBedTop().getBlock().getType().equals(Material.BED_BLOCK) ? "§a✔" : "§c✖");
	}
	
	public void placeBed() {
		BedWars bedWars = BedWars.getInstance();
		
		BlockFace face = bedWars.getGameFileUtils().getBedFacing(bedWars.getGameManager().getMap(), "Bed" + teamName.toUpperCase());
		
		if(bedBottom != null) {
			Block b = bedBottom.getBlock();
			b.setType(Material.BED_BLOCK);
			
			BlockState state = b.getState();
			Bed bed = new Bed(Material.BED_BLOCK);
			bed.setHeadOfBed(false);
			bed.setFacingDirection(face);
			state.setData(bed);
			state.update(true);
		}
		
		if(bedTop != null) {
			Block b = bedTop.getBlock();
			b.setType(Material.BED_BLOCK);
			
			BlockState state = b.getState();
			Bed bed = new Bed(Material.BED_BLOCK);
			bed.setHeadOfBed(true);
			bed.setFacingDirection(face);
			state.setData(bed);
			state.update(true);
		}
	}
	
	public void addMember(Player p) {
		BedWars bedWars = BedWars.getInstance();
		ScoreboardUtils scoreboardUtils = bedWars.getScoreboardUtils();
		
		if(!(isTeamMember(p))) {
			for (BedWarsTeam team : bedWars.getGameManager().getTeams())
				team.removeMember(p);
			
			if(scoreboardUtils.getScoreboards().containsKey(p.getName())) {
				Team t = scoreboardUtils.getScoreboards().get(p.getName()).getTeam("10NONE");
				
				if(t.hasEntry(p.getName()))
					t.removeEntry(p.getName());
			}
			
			members.add(p.getUniqueId());
		}
	}
	
	public void removeMember(Player p) {
		ScoreboardUtils scoreboardUtils = BedWars.getInstance().getScoreboardUtils();
		
		if(isTeamMember(p)) {
			members.remove(p.getUniqueId());
			
			if(scoreboardUtils.getScoreboards().containsKey(p.getName())) {
				Team t = scoreboardUtils.getScoreboards().get(p.getName()).getTeam("0" + scoreboardCount + teamName);
						
				if(t.hasEntry(p.getName()))
					t.removeEntry(p.getName());
			}
		}
	}

}
