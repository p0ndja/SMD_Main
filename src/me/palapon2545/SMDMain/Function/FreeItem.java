package me.palapon2545.SMDMain.Function;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.palapon2545.SMDMain.Main.pluginMain;

public class FreeItem extends JavaPlugin {
	
	pluginMain pl;
	public FreeItem (pluginMain pl) {
		this.pl = pl;
	}
	
	public static void openFreeGUI(Player p) {
		Inventory inv;
		inv = Bukkit.createInventory(null, 54, "Free Item");
		ItemStack black = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta bl = black.getItemMeta();
		bl.setDisplayName(ChatColor.WHITE + " ");
		black.setItemMeta(bl);
		int x1 = 0;
		int x2 = 53;
		for (int x = x1; x <= x2; x++) {
			inv.setItem(x, black);
		}

		ItemStack wooden_sword = new ItemStack(Material.WOOD_SWORD, 1);
		ItemStack wooden_axe = new ItemStack(Material.WOOD_AXE, 1);
		ItemStack wooden_pickaxe = new ItemStack(Material.WOOD_PICKAXE, 1);
		ItemStack wooden_shovel = new ItemStack(Material.WOOD_SPADE, 1);
		ItemStack wooden_hoe = new ItemStack(Material.WOOD_HOE, 1);
		ItemStack bread = new ItemStack(Material.BREAD, 16);
		inv.setItem(11, wooden_sword);
		inv.setItem(12, wooden_axe);
		inv.setItem(13, wooden_pickaxe);
		inv.setItem(14, wooden_shovel);
		inv.setItem(15, wooden_hoe);
		inv.setItem(20, bread);

		ItemStack emerald = new ItemStack(Material.EMERALD, 1);
		ItemMeta em = black.getItemMeta();
		em.setDisplayName(ChatColor.GREEN + "+ Money " + ChatColor.GOLD + ChatColor.BOLD + "1k Coin");
		emerald.setItemMeta(em);
		inv.setItem(21, emerald);

		ItemStack bed = new ItemStack(Material.BED, 3);
		ItemMeta bedm = black.getItemMeta();
		bedm.setDisplayName(ChatColor.RED + "+ Sethome " + ChatColor.GOLD + ChatColor.BOLD + "3 places");
		bed.setItemMeta(bedm);
		inv.setItem(22, bed);

		ItemStack flower = new ItemStack(Material.DOUBLE_PLANT, 15);
		ItemMeta flowerm = black.getItemMeta();
		flowerm.setDisplayName(
				ChatColor.LIGHT_PURPLE + "+ LuckyClick Quota " + ChatColor.AQUA + ChatColor.BOLD + "15 quotas");
		flower.setItemMeta(flowerm);
		inv.setItem(23, flower);

		ItemStack teleport = new ItemStack(Material.ENDER_PEARL, 10);
		ItemMeta teleportm = black.getItemMeta();
		teleportm.setDisplayName(ChatColor.BLUE + "+ TPR Quota " + ChatColor.AQUA + ChatColor.BOLD + "10 quotas");
		teleport.setItemMeta(teleportm);
		inv.setItem(24, teleport);

		ItemStack yes = new ItemStack(Material.EMERALD_BLOCK, 1);
		ItemMeta yesm = black.getItemMeta();
		yesm.setDisplayName(ChatColor.GREEN + "Get all of them");
		inv.setItem(37, yes);
		inv.setItem(38, yes);
		inv.setItem(46, yes);
		inv.setItem(47, yes);

		ItemStack no = new ItemStack(Material.REDSTONE_BLOCK, 1);
		ItemMeta nom = black.getItemMeta();
		nom.setDisplayName(ChatColor.GREEN + "I don't want them");
		inv.setItem(42, no);
		inv.setItem(43, no);
		inv.setItem(51, no);
		inv.setItem(52, no);

		p.openInventory(inv);
	}

}
