package me.palapon2545.SMDMain.Function;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.palapon2545.SMDMain.Library.Prefix;
import me.palapon2545.SMDMain.Main.pluginMain;

public class Shop extends JavaPlugin implements Listener {

	pluginMain pl;

	public Shop(pluginMain pl) {
		this.pl = pl;
	}
	
	public void buy(Player player, String item, int amount, long price, short data) {
		String playerName = player.getName();
		File userdata = new File(getDataFolder(), File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		Material l = Material.getMaterial(item.toUpperCase());
		if (l != null) {
			Inventory inv = player.getInventory();
			long money = playerData.getLong("money");
			ItemStack c = new ItemStack(l);
			c.getData().setData((byte) data);
			if (money >= price) {
				inv.addItem(new ItemStack(l, amount, data));
				try {
					playerData.set("money", money - price);
					playerData.save(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
				player.sendMessage(Prefix.sv + "You paid " + ChatColor.GOLD + price + " Coin(s) " + ChatColor.GRAY
						+ "from buying " + ChatColor.AQUA + amount + "x " + item);
			} else {
				player.sendMessage(Prefix.sv + Prefix.nom);
				pl.no(player);
			}
		} else {
			player.sendMessage(Prefix.sv + "Item " + ChatColor.YELLOW + item + ChatColor.GRAY + " not found.");
			pl.no(player);
		}
	}

	public void sell(Player player, String item, int amount, long price, short data) {
		String playerName = player.getName();
		File userdata = new File(pl.getDataFolder(), File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		Material l = Material.getMaterial(item.toUpperCase());

		if (l != null) {

			Inventory inv = player.getInventory();
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
				player.sendMessage(Prefix.sv + "You got " + ChatColor.GOLD + gotPrice + " Coin(s) " + ChatColor.GRAY
						+ "from selling " + ChatColor.AQUA + sellCount + "x " + item);
			} else {
				player.sendMessage(Prefix.sv + Prefix.noi);
				pl.no(player);
			}
		} else {
			player.sendMessage(Prefix.sv + "Item " + ChatColor.YELLOW + item + ChatColor.GRAY + " not found.");
			pl.no(player);
		}

	}

	public boolean decreseitem1(Player player, int itemid, int itemdata, boolean forcetruedata) {
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
				emy.setTypeId(0);

				player.getInventory().setItem(lenl, emy);

				return true;
			}

		}
		return false;
	}

}
