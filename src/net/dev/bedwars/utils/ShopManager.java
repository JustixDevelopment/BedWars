package net.dev.bedwars.utils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import net.dev.bedwars.game.GameManager;

public class ShopManager {

	public static void openShopPage(Player p, int page) {
		if(page == 0) {
			Inventory inv = Bukkit.createInventory(null, 18, "§7» §3Shop§8: §8[Blöcke]");
			
			for (int i = 0; i < inv.getSize(); i++) {
				inv.setItem(i, ItemUtils.createItem(Material.STAINED_GLASS_PANE, 1, "§0"));
			}
			
			inv.setItem(0, ItemUtils.createItem(Material.SANDSTONE, 1, "§7» §3Blöcke", true));
			inv.setItem(1, ItemUtils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§7» §3Rüstungen"));
			inv.setItem(2, ItemUtils.createItem(Material.STONE_PICKAXE, 1, "§7» §3Spitzhacken"));
			inv.setItem(3, ItemUtils.createItem(Material.WOOD_SWORD, 1, "§7» §3Schwerter"));
			inv.setItem(4, ItemUtils.createItem(Material.BOW, 1, "§7» §3Bögen"));
			inv.setItem(5, ItemUtils.createItem(Material.CAKE, 1, "§7» §3Nahrung"));
			inv.setItem(6, ItemUtils.createItem(Material.ENDER_CHEST, 1, "§7» §3Kisten"));
			inv.setItem(7, ItemUtils.createItem(Material.getMaterial(373), 1, "§7» §3Tränke"));
			inv.setItem(8, ItemUtils.createItem(Material.EMERALD, 1, "§7» §3Spezial"));
			
			inv.setItem(11, ItemUtils.createItem(Material.RED_SANDSTONE, 4, "§7Sandstein", "", "  §c§l1 Bronze"));
			inv.setItem(12, ItemUtils.createItem(Material.ENDER_STONE, 1, "§7Endstein", "", "  §c§l6  Bronze"));
			inv.setItem(13, ItemUtils.createItem(Material.IRON_BLOCK, 1, "§7Eisenblock", "", "  §7§l3 Eisen"));
			inv.setItem(14, ItemUtils.createItem(Material.STAINED_GLASS, 1, "§7Glas ", "", "  §c§l3 Bronze"));
			inv.setItem(15, ItemUtils.createItem(Material.GLOWSTONE, 1, "§7Glowstone", "", "  §c§l14 Bronze"));
			
			p.openInventory(inv);
		} else if(page == 1) {
			Inventory inv = Bukkit.createInventory(null, 18, "§7» §3Shop§8: §8[Rüstungen]");
			
			for (int i = 0; i < inv.getSize(); i++) {
				inv.setItem(i, ItemUtils.createItem(Material.STAINED_GLASS_PANE, 1, "§0"));
			}
			
			inv.setItem(0, ItemUtils.createItem(Material.SANDSTONE, 1, "§7» §3Blöcke"));
			inv.setItem(1, ItemUtils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§7» §3Rüstungen", true));
			inv.setItem(2, ItemUtils.createItem(Material.STONE_PICKAXE, 1, "§7» §3Spitzhacken"));
			inv.setItem(3, ItemUtils.createItem(Material.WOOD_SWORD, 1, "§7» §3Schwerter"));
			inv.setItem(4, ItemUtils.createItem(Material.BOW, 1, "§7» §3Bögen"));
			inv.setItem(5, ItemUtils.createItem(Material.CAKE, 1, "§7» §3Nahrung"));
			inv.setItem(6, ItemUtils.createItem(Material.ENDER_CHEST, 1, "§7» §3Kisten"));
			inv.setItem(7, ItemUtils.createItem(Material.getMaterial(373), 1, "§7» §3Tränke"));
			inv.setItem(8, ItemUtils.createItem(Material.EMERALD, 1, "§7» §3Spezial"));
			
			inv.setItem(9, ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.createLeatherItem(Material.LEATHER_HELMET, 1, "§9Lederhelm", GameManager.getTeam(p).getArmorColor(), "", "  §c§l1 Bronze"), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.DURABILITY, 1));
			inv.setItem(10, ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.createLeatherItem(Material.LEATHER_LEGGINGS, 1, "§9Lederhose", GameManager.getTeam(p).getArmorColor(), "", "  §c§l1 Bronze"), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.DURABILITY, 1));
			inv.setItem(11, ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.createLeatherItem(Material.LEATHER_BOOTS, 1, "§9Lederschuhe", GameManager.getTeam(p).getArmorColor(), "", "  §c§l1 Bronze"), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.DURABILITY, 1));
			inv.setItem(13, ItemUtils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§1Brustplatte", "", "  §7§l1 Eisen"));
			inv.setItem(14, ItemUtils.addEnchantment(ItemUtils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§1Brustplatte I", "", "  §7§l3 Eisen"), Enchantment.PROTECTION_ENVIRONMENTAL, 1));
			inv.setItem(15, ItemUtils.addEnchantment(ItemUtils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§1Brustplatte II", "", "  §7§l7 Eisen"), Enchantment.PROTECTION_ENVIRONMENTAL, 2));
			inv.setItem(16, ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§1Brustplatte III", "", "  §7§l11 Eisen"), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.THORNS, 1));
			inv.setItem(17, ItemUtils.createLeatherItem(Material.LEATHER_CHESTPLATE, 1, "§1Sprengweste", Color.BLACK, "", "  §6§l6 Gold"));
			
			p.openInventory(inv);
		} else if(page == 2) {
			Inventory inv = Bukkit.createInventory(null, 18, "§7» §3Shop§8: §8[Spitzhacken]");
			
			for (int i = 0; i < inv.getSize(); i++) {
				inv.setItem(i, ItemUtils.createItem(Material.STAINED_GLASS_PANE, 1, "§0"));
			}
			
			inv.setItem(0, ItemUtils.createItem(Material.SANDSTONE, 1, "§7» §3Blöcke"));
			inv.setItem(1, ItemUtils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§7» §3Rüstungen"));
			inv.setItem(2, ItemUtils.createItem(Material.STONE_PICKAXE, 1, "§7» §3Spitzhacken", true));
			inv.setItem(3, ItemUtils.createItem(Material.WOOD_SWORD, 1, "§7» §3Schwerter"));
			inv.setItem(4, ItemUtils.createItem(Material.BOW, 1, "§7» §3Bögen"));
			inv.setItem(5, ItemUtils.createItem(Material.CAKE, 1, "§7» §3Nahrung"));
			inv.setItem(6, ItemUtils.createItem(Material.ENDER_CHEST, 1, "§7» §3Kisten"));
			inv.setItem(7, ItemUtils.createItem(Material.getMaterial(373), 1, "§7» §3Tränke"));
			inv.setItem(8, ItemUtils.createItem(Material.EMERALD, 1, "§7» §3Spezial"));
			
			inv.setItem(12, ItemUtils.createItem(Material.WOOD_PICKAXE, 1, "§eSpitzhacke I", "", "  §c§l7 Bronze"));
			inv.setItem(13, ItemUtils.createItem(Material.STONE_PICKAXE, 1, "§eSpitzhacke II", "", "  §7§l2 Eisen"));
			inv.setItem(14, ItemUtils.createItem(Material.IRON_PICKAXE, 1, "§eSpitzhacke III", "", "  §6§l1 Gold"));
			
			p.openInventory(inv);
		} else if(page == 3) {
			Inventory inv = Bukkit.createInventory(null, 18, "§7» §3Shop§8: §8[Schwerter]");
			
			for (int i = 0; i < inv.getSize(); i++) {
				inv.setItem(i, ItemUtils.createItem(Material.STAINED_GLASS_PANE, 1, "§0"));
			}
			
			inv.setItem(0, ItemUtils.createItem(Material.SANDSTONE, 1, "§7» §3Blöcke"));
			inv.setItem(1, ItemUtils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§7» §3Rüstungen"));
			inv.setItem(2, ItemUtils.createItem(Material.STONE_PICKAXE, 1, "§7» §3Spitzhacken"));
			inv.setItem(3, ItemUtils.createItem(Material.WOOD_SWORD, 1, "§7» §3Schwerter", true));
			inv.setItem(4, ItemUtils.createItem(Material.BOW, 1, "§7» §3Bögen"));
			inv.setItem(5, ItemUtils.createItem(Material.CAKE, 1, "§7» §3Nahrung"));
			inv.setItem(6, ItemUtils.createItem(Material.ENDER_CHEST, 1, "§7» §3Kisten"));
			inv.setItem(7, ItemUtils.createItem(Material.getMaterial(373), 1, "§7» §3Tränke"));
			inv.setItem(8, ItemUtils.createItem(Material.EMERALD, 1, "§7» §3Spezial"));
			
			inv.setItem(11, ItemUtils.addEnchantment(ItemUtils.createItem(Material.STICK, 1, "§cKnüppel", "", "  §c§l10 Bronze"), Enchantment.KNOCKBACK, 1));
			inv.setItem(12, ItemUtils.createItem(Material.WOOD_SWORD, 1, "§cHolzschwert", "", "  §7§l1 Eisen"));
			inv.setItem(13, ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.createItem(Material.WOOD_SWORD, 1, "§cHolzschwert I", "", "  §7§l4 Eisen"), Enchantment.DURABILITY, 1), Enchantment.DAMAGE_ALL, 1));
			inv.setItem(14, ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.createItem(Material.WOOD_SWORD, 1, "§cHolzschwert II", "", "  §7§l6 Eisen"), Enchantment.DURABILITY, 1), Enchantment.DAMAGE_ALL, 2));
			inv.setItem(15, ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.createItem(Material.IRON_SWORD, 1, "§4Eisenschwert", "", "  §6§l6 Gold"), Enchantment.KNOCKBACK, 1), Enchantment.DAMAGE_ALL, 2));
			
			p.openInventory(inv);
		} else if(page == 4) {
			Inventory inv = Bukkit.createInventory(null, 18, "§7» §3Shop§8: §8[Bögen]");
			
			for (int i = 0; i < inv.getSize(); i++) {
				inv.setItem(i, ItemUtils.createItem(Material.STAINED_GLASS_PANE, 1, "§0"));
			}
			
			inv.setItem(0, ItemUtils.createItem(Material.SANDSTONE, 1, "§7» §3Blöcke"));
			inv.setItem(1, ItemUtils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§7» §3Rüstungen"));
			inv.setItem(2, ItemUtils.createItem(Material.STONE_PICKAXE, 1, "§7» §3Spitzhacken"));
			inv.setItem(3, ItemUtils.createItem(Material.WOOD_SWORD, 1, "§7» §3Schwerter"));
			inv.setItem(4, ItemUtils.createItem(Material.BOW, 1, "§7» §3Bögen", true));
			inv.setItem(5, ItemUtils.createItem(Material.CAKE, 1, "§7» §3Nahrung"));
			inv.setItem(6, ItemUtils.createItem(Material.ENDER_CHEST, 1, "§7» §3Kisten"));
			inv.setItem(7, ItemUtils.createItem(Material.getMaterial(373), 1, "§7» §3Tränke"));
			inv.setItem(8, ItemUtils.createItem(Material.EMERALD, 1, "§7» §3Spezial"));
			
			inv.setItem(11, ItemUtils.addEnchantment(ItemUtils.createItem(Material.BOW, 1, "§5Bogen I", "", "  §6§l3 Gold"), Enchantment.ARROW_INFINITE, 1));
			inv.setItem(12, ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.createItem(Material.BOW, 1, "§5Bogen II", "", "  §6§l7 Gold"), Enchantment.ARROW_INFINITE, 1), Enchantment.ARROW_DAMAGE, 1));
			inv.setItem(13, ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.createItem(Material.BOW, 1, "§5Bogen III", "", "  §6§l11 Gold"), Enchantment.ARROW_INFINITE, 1), Enchantment.ARROW_DAMAGE, 2));
			inv.setItem(15, ItemUtils.createItem(Material.ARROW, 1, "§dPfeil", "", "  §6§l1 Gold"));
			
			p.openInventory(inv);
		} else if(page == 5) {
			Inventory inv = Bukkit.createInventory(null, 18, "§7» §3Shop§8: §8[Nahrung]");
			
			for (int i = 0; i < inv.getSize(); i++) {
				inv.setItem(i, ItemUtils.createItem(Material.STAINED_GLASS_PANE, 1, "§0"));
			}
			
			inv.setItem(0, ItemUtils.createItem(Material.SANDSTONE, 1, "§7» §3Blöcke"));
			inv.setItem(1, ItemUtils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§7» §3Rüstungen"));
			inv.setItem(2, ItemUtils.createItem(Material.STONE_PICKAXE, 1, "§7» §3Spitzhacken"));
			inv.setItem(3, ItemUtils.createItem(Material.WOOD_SWORD, 1, "§7» §3Schwerter"));
			inv.setItem(4, ItemUtils.createItem(Material.BOW, 1, "§7» §3Bögen"));
			inv.setItem(5, ItemUtils.createItem(Material.CAKE, 1, "§7» §3Nahrung", true));
			inv.setItem(6, ItemUtils.createItem(Material.ENDER_CHEST, 1, "§7» §3Kisten"));
			inv.setItem(7, ItemUtils.createItem(Material.getMaterial(373), 1, "§7» §3Tränke"));
			inv.setItem(8, ItemUtils.createItem(Material.EMERALD, 1, "§7» §3Spezial"));
			
			inv.setItem(11, ItemUtils.createItem(Material.APPLE, 1, "§2Apfel", "", "  §c§l2 Bronze"));
			inv.setItem(12, ItemUtils.createItem(Material.COOKED_BEEF, 1, "§2Fleisch", "", "  §c§l4 Bronze"));
			inv.setItem(13, ItemUtils.createItem(Material.CAKE, 1, "§2Kuchen", "", "  §7§l1 Eisen"));
			inv.setItem(15, ItemUtils.createItem(Material.GOLDEN_APPLE, 1, "§2Goldapfel", "", "  §6§l2 Gold"));
			
			p.openInventory(inv);
		} else if(page == 6) {
			Inventory inv = Bukkit.createInventory(null, 18, "§7» §3Shop§8: §8[Kisten]");
			
			for (int i = 0; i < inv.getSize(); i++) {
				inv.setItem(i, ItemUtils.createItem(Material.STAINED_GLASS_PANE, 1, "§0"));
			}
			
			inv.setItem(0, ItemUtils.createItem(Material.SANDSTONE, 1, "§7» §3Blöcke"));
			inv.setItem(1, ItemUtils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§7» §3Rüstungen"));
			inv.setItem(2, ItemUtils.createItem(Material.STONE_PICKAXE, 1, "§7» §3Spitzhacken"));
			inv.setItem(3, ItemUtils.createItem(Material.WOOD_SWORD, 1, "§7» §3Schwerter"));
			inv.setItem(4, ItemUtils.createItem(Material.BOW, 1, "§7» §3Bögen"));
			inv.setItem(5, ItemUtils.createItem(Material.CAKE, 1, "§7» §3Nahrung"));
			inv.setItem(6, ItemUtils.createItem(Material.ENDER_CHEST, 1, "§7» §3Kisten", true));
			inv.setItem(7, ItemUtils.createItem(Material.getMaterial(373), 1, "§7» §3Tränke"));
			inv.setItem(8, ItemUtils.createItem(Material.EMERALD, 1, "§7» §3Spezial"));
			
			inv.setItem(12, ItemUtils.createItem(Material.CHEST, 1, "§aKiste", "", "  §7§l2 Eisen"));
			inv.setItem(14, ItemUtils.createItem(Material.ENDER_CHEST, 1, "§aTeamkiste", "", "  §6§l2 Gold"));
			
			p.openInventory(inv);
		} else if(page == 7) {
			Inventory inv = Bukkit.createInventory(null, 18, "§7» §3Shop§8: §8[Tränke]");
			
			for (int i = 0; i < inv.getSize(); i++) {
				inv.setItem(i, ItemUtils.createItem(Material.STAINED_GLASS_PANE, 1, "§0"));
			}
			
			inv.setItem(0, ItemUtils.createItem(Material.SANDSTONE, 1, "§7» §3Blöcke"));
			inv.setItem(1, ItemUtils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§7» §3Rüstungen"));
			inv.setItem(2, ItemUtils.createItem(Material.STONE_PICKAXE, 1, "§7» §3Spitzhacken"));
			inv.setItem(3, ItemUtils.createItem(Material.WOOD_SWORD, 1, "§7» §3Schwerter"));
			inv.setItem(4, ItemUtils.createItem(Material.BOW, 1, "§7» §3Bögen"));
			inv.setItem(5, ItemUtils.createItem(Material.CAKE, 1, "§7» §3Nahrung"));
			inv.setItem(6, ItemUtils.createItem(Material.ENDER_CHEST, 1, "§7» §3Kisten"));
			inv.setItem(7, ItemUtils.createItem(Material.getMaterial(373), 1, "§7» §3Tränke", true));
			inv.setItem(8, ItemUtils.createItem(Material.EMERALD, 1, "§7» §3Spezial"));
			
			inv.setItem(9, ItemUtils.createPotion(1, "§3Heilung I", PotionEffectType.HEAL, 20, 0, "§7Direktheilung", "", "  §7§l5 Eisen"));
			inv.setItem(11, ItemUtils.createPotion(1, "§3Heilung II", PotionEffectType.HEAL, 20, 1, "§7Direktheilung II", "", "  §7§l8 Eisen"));
			inv.setItem(13, ItemUtils.createPotion(1, "§3Stärke", PotionEffectType.INCREASE_DAMAGE, 20 * 60 * 3, 0, "§7Stärke (3:00)", "", "§5§lAuswirkungen:", "§9+130% Angriffsschaden", "", "  §6§l10 Gold"));
			inv.setItem(15, ItemUtils.createPotion(1, "§3Haste", PotionEffectType.FAST_DIGGING, 200000, 0, "",  "  §6§l5 Gold"));
			inv.setItem(17, ItemUtils.createPotion(1, "§3Sprungkraft", PotionEffectType.JUMP, 20 * 30 * 3, 1, "§7Sprungkraft II (1:30)", "", "  §6§l3 Gold"));
			
			p.openInventory(inv);
		} else if(page == 8) {
			Inventory inv = Bukkit.createInventory(null, 18, "§7» §3Shop§8: §8[Spezial]");
			
			for (int i = 0; i < inv.getSize(); i++) {
				inv.setItem(i, ItemUtils.createItem(Material.STAINED_GLASS_PANE, 1, "§0"));
			}
			
			inv.setItem(0, ItemUtils.createItem(Material.SANDSTONE, 1, "§7» §3Blöcke"));
			inv.setItem(1, ItemUtils.createItem(Material.CHAINMAIL_CHESTPLATE, 1, "§7» §3Rüstungen"));
			inv.setItem(2, ItemUtils.createItem(Material.STONE_PICKAXE, 1, "§7» §3Spitzhacken"));
			inv.setItem(3, ItemUtils.createItem(Material.WOOD_SWORD, 1, "§7» §3Schwerter"));
			inv.setItem(4, ItemUtils.createItem(Material.BOW, 1, "§7» §3Bögen"));
			inv.setItem(5, ItemUtils.createItem(Material.CAKE, 1, "§7» §3Nahrung"));
			inv.setItem(6, ItemUtils.createItem(Material.ENDER_CHEST, 1, "§7» §3Kisten"));
			inv.setItem(7, ItemUtils.createItem(Material.getMaterial(373), 1, "§7» §3Tränke"));
			inv.setItem(8, ItemUtils.createItem(Material.EMERALD, 1, "§7» §3Spezial", true));
			
			inv.setItem(9, ItemUtils.createItem(Material.LADDER, 1, "§6Leiter", "", "  §c§l2 Bronze"));
			inv.setItem(10, ItemUtils.createItem(Material.FIREWORK, 1, "§6Teleporter", "§e§lTeleportiert dich bei Aktivierung", "§e§lzurück in deine Base", "", "  §7§l10 Eisen"));
			inv.setItem(11, ItemUtils.createItem(Material.ARMOR_STAND, 1, "§6Mobiler Shop", "", "  §7§l12 Eisen"));
			inv.setItem(12, ItemUtils.createItem(Material.TNT, 1, "§6TNT", "", "  §6§l3 Gold"));
			inv.setItem(14, ItemUtils.createItem(Material.MONSTER_EGG, 1, "§6Fallschirm", "", "  §6§l2 Gold"));
			inv.setItem(15, ItemUtils.createItem(Material.NETHER_STAR, 1, "§6Rettungsplattform", "", "  §6§l3 Gold"));
			inv.setItem(16, ItemUtils.createItem(Material.ENDER_PEARL, 1, "§6Enderperle", "", "  §6§l14 Gold"));
			inv.setItem(17, ItemUtils.createItem(Material.WEB, 1, "§6Spinnenweben", "", "  §c§l20 Bronze"));
			
			p.openInventory(inv);
		}
	}
	
	public static void buyItem(Player p, ItemType type, int neededAmount, ItemStack result, boolean isShiftClick) {
		ItemStack item = new ItemStack((type == ItemType.BRONZE) ? Material.CLAY_BRICK : ((type == ItemType.IRON) ? Material.IRON_INGOT : Material.GOLD_INGOT));
		
		if(!(isShiftClick)) {
			if(p.getInventory().containsAtLeast(item, neededAmount)) {
				p.getInventory().removeItem(new ItemStack(item.getType(), neededAmount));
				
				p.getInventory().addItem(ItemUtils.resetLore(result));
			} else {
				p.sendMessage(Utils.prefix + "§7Du hast nicht genügend §e" + type.name());
				p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 5, 5);
			}
		} else {
			for (int i = 0; i < 64; i++) {
				if(p.getInventory().containsAtLeast(item, neededAmount)) {
					p.getInventory().removeItem(new ItemStack(item.getType(), neededAmount));
					
					p.getInventory().addItem(ItemUtils.resetLore(result));
				} else {
					p.sendMessage(Utils.prefix + "§7Du hast nicht genügend §e" + type.name());
					p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 5, 5);
					break;
				}
			}
		}
	}
	
	public enum ItemType {
		
		BRONZE,
		IRON,
		GOLD;
		
	}

}
