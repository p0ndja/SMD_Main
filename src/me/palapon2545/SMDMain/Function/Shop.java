package me.palapon2545.SMDMain.Function;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.palapon2545.SMDMain.Library.Prefix;
import me.palapon2545.SMDMain.Library.StockInt;

public class Shop {
	@SuppressWarnings("deprecation")
	public static void buy(Player player, String item, int amount, long price, short data) {
		long money = Money.get(player);
		item = Function.makeFirstCapital(item);
		Material l = Material.getMaterial(item.toUpperCase());
		if (l != null) {
			Inventory inv = player.getInventory();
			ItemStack c = new ItemStack(l);
			c.getData().setData((byte) data);
			if (money >= price) {
				inv.addItem(new ItemStack(l, amount, data));
				Money.take(player, price, ChatColor.GRAY + "from buying " + ChatColor.AQUA + amount + "x " + item);
				Function.egg(player);
			} else {
				player.sendMessage(Prefix.server + Prefix.nom);
				Function.no(player);
			}
		} else {
			player.sendMessage(Prefix.server + "Item " + ChatColor.YELLOW + item + ChatColor.GRAY + " not found.");
			Function.no(player);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void sell(Player player, String item, int amount, long price, short data) {

		item = Function.makeFirstCapital(item);

		String playerName = player.getName();
		File userdata = new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		Material l = Material.getMaterial(item.toUpperCase());

		if (l != null) {
			long money = playerData.getLong("money");
			ItemStack curItem = new ItemStack(l, amount, data);
			int sellCount = 0;
			for (int lAmount = 0; lAmount < amount; lAmount++) {
				boolean cando = decreseitem1(player, curItem.getType().getId(), curItem.getData().getData(), true);
				if (cando == true) {
					sellCount++;
				}
			}

			// can sell
			if (sellCount > 0) {
				double gotPrice = ((double) price / (double) amount) * sellCount;
				try {
					playerData.set("money", money + gotPrice);
					playerData.save(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String coin = " Coins ";
				if (gotPrice <= 1) {
					coin = " Coin ";
				}
				player.sendMessage(Prefix.server + "You got " + ChatColor.GOLD + gotPrice + coin + ChatColor.GRAY
						+ "from selling " + ChatColor.AQUA + sellCount + "x " + item);

				Function.pickup(player);

			} else {
				player.sendMessage(Prefix.server + Prefix.notEnoughItem);
				Function.no(player);
			}
		} else {
			player.sendMessage(Prefix.server + "Item " + ChatColor.YELLOW + item + ChatColor.GRAY + " not found.");
			Function.no(player);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static boolean decreseitem1(Player player, int itemid, int itemdata, boolean forcetruedata) {
		ItemStack itm = null;
		int lenl = 0;

		if (itemid == 0) {
			return false;
		}

		for (lenl = 0; lenl < player.getInventory().getContents().length; lenl++) {
			if (player.getInventory().getItem(lenl) == null) {
				continue;
			}

			itm = player.getInventory().getItem(lenl);

			if (itm.getType().getId() != itemid) {
				continue;
			}

			if (forcetruedata == true) {
				if (itm.getData().getData() != itemdata) {
					continue;
				}
			}

			if (itm.getAmount() != 1) {
				itm.setAmount(itm.getAmount() - 1);
				return true;
			} else {
				ItemStack emy = player.getItemInHand();
				emy.setType(Material.AIR);

				player.getInventory().setItem(lenl, emy);

				return true;
			}

		}
		return false;
	}
}
