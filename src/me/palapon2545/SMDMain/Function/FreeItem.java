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

	public FreeItem(pluginMain pl) {
		this.pl = pl;
	}

	public static void openFreeGUI(Player p) {
		Inventory inv;
		inv = Bukkit.createInventory(null, 54, "Free Item");
		ItemStack black = new ItemStack(Blockto113.STAINED_GLASS_PANE.bukkitblock(), 1);
		ItemMeta bl = black.getItemMeta();
		bl.setDisplayName(ChatColor.WHITE + " ");
		black.setItemMeta(bl);
		int x1 = 0;
		int x2 = 53;
		for (int x = x1; x <= x2; x++) {
			inv.setItem(x, black);
		}

		ItemStack wooden_sword = new ItemStack(Blockto113.WOOD_SWORD.bukkitblock(), 1);
		ItemStack wooden_axe = new ItemStack(Blockto113.WOOD_AXE.bukkitblock(), 1);
		ItemStack wooden_pickaxe = new ItemStack(Blockto113.WOOD_PICKAXE.bukkitblock(), 1);
		ItemStack wooden_shovel = new ItemStack(Blockto113.WOOD_SPADE.bukkitblock(), 1);
		ItemStack wooden_hoe = new ItemStack(Blockto113.WOOD_HOE.bukkitblock(), 1);
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

		ItemStack bed = new ItemStack(Blockto113.BED.bukkitblock(), 3);
		ItemMeta bedm = black.getItemMeta();
		bedm.setDisplayName(ChatColor.RED + "+ Sethome " + ChatColor.GOLD + ChatColor.BOLD + "3 places");
		bed.setItemMeta(bedm);
		inv.setItem(22, bed);

		ItemStack flower = new ItemStack(Blockto113.DOUBLE_PLANT.bukkitblock(), 15);
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
		yesm.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Get all of them");
		yes.setItemMeta(yesm);
		inv.setItem(37, yes);
		inv.setItem(38, yes);
		inv.setItem(46, yes);
		inv.setItem(47, yes);

		ItemStack no = new ItemStack(Material.REDSTONE_BLOCK, 1);
		ItemMeta nom = black.getItemMeta();
		nom.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "I don't want them");
		no.setItemMeta(nom);
		inv.setItem(42, no);
		inv.setItem(43, no);
		inv.setItem(51, no);
		inv.setItem(52, no);

		p.openInventory(inv);
	}

}
