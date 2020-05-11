package net.dev.bedwars.utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemUtils {

	public ItemStack createItem(Material mat, int amount, String displayName, String... lore) {
		ItemStack is = new ItemStack(mat, amount);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(displayName);
		im.setLore(Arrays.asList(lore));
		is.setItemMeta(im);
		
		return is;
	}
	
	public ItemStack createItem(Material mat, int amount, int metaData, String displayName, String... lore) {
		ItemStack is = new ItemStack(mat, amount, (byte) metaData);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(displayName);
		im.setLore(Arrays.asList(lore));
		is.setItemMeta(im);
		
		return is;
	}
	
	public ItemStack createItem(Material mat, String displayName, String... lore) {
		ItemStack is = new ItemStack(mat);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(displayName);
		im.setLore(Arrays.asList(lore));
		is.setItemMeta(im);
		
		return is;
	}
	
	public ItemStack createItem(Material mat, int amount, String displayName, boolean hasEnchants) {
		ItemStack i = new ItemStack(mat, amount);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(displayName);
		
		if(hasEnchants) {
			m.addEnchant(Enchantment.DURABILITY, 0, true);
		}
		
		i.setItemMeta(m);

		return i;
	}
	
	public ItemStack createLeatherItem(Material mat, int amount, String displayName, Color color, String... lore) {
		ItemStack i = new ItemStack(mat, amount);
		LeatherArmorMeta m = (LeatherArmorMeta) i.getItemMeta();
		m.setDisplayName(displayName);
		m.setColor(color);
		m.setLore(Arrays.asList(lore));
		i.setItemMeta(m);

		return i;
	}
	
	public ItemStack createPotion(int amount, String displayName, PotionEffectType type, int duration, int amplifier, String... lore) {
		ItemStack i = new ItemStack(Material.getMaterial(373), amount);
		PotionMeta m = (PotionMeta) i.getItemMeta();
		m.setDisplayName(displayName);
		m.addCustomEffect(new PotionEffect(type, duration, amplifier), true);
		m.setLore(Arrays.asList(lore));
		i.setItemMeta(m);

		return i;
	}

	public ItemStack addEnchantment(ItemStack item, Enchantment ench, int amplifier) {
		ItemMeta m = item.getItemMeta();
		m.addEnchant(ench, amplifier, true);
		item.setItemMeta(m);
		
		return item;
	}

	public ItemStack resetLore(ItemStack item) {
		ItemMeta m = item.getItemMeta();
		m.setLore(new ArrayList<String>());
		item.setItemMeta(m);
		
		return item;
	}
	
}
