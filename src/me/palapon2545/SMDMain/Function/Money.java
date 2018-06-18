package me.palapon2545.SMDMain.Function;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.palapon2545.SMDMain.Library.Prefix;
import me.palapon2545.SMDMain.Library.StockInt;
import me.palapon2545.SMDMain.Main.pluginMain;

public class Money extends JavaPlugin {
	
	static String coin = " Coins ";

	public static void tranfer(Player payer, Player receiver, long amount) {
		String payerName = payer.getName();
		File payerLocate = new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + payerName);
		File payerFile = new File(payerLocate, File.separator + "config.yml");
		FileConfiguration payerData = YamlConfiguration.loadConfiguration(payerFile);
		String receiverName = receiver.getName();
		File receiverLocate = new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + receiverName);
		File receiverFile = new File(receiverLocate, File.separator + "config.yml");
		FileConfiguration receiverData = YamlConfiguration.loadConfiguration(receiverFile);
		long payerMoney = payerData.getLong("money");
		long receiverMoney = receiverData.getLong("money");
		if (amount >= 0) {
			if (payerMoney >= amount) { // CAN TRANFER
				try {
					payerData.set("money", payerMoney - amount);
					payerData.save(payerFile);
					receiverData.set("money", receiverMoney + amount);
					receiverData.save(receiverFile);
				} catch (IOException e) {
					Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
				}
				
				if (amount == 1) {
					coin = " Coin ";
				}
				
				payer.sendMessage(Prefix.server + ChatColor.GRAY + "You paid " + ChatColor.GREEN + amount + coin + ChatColor.GRAY
						+ "to " + ChatColor.YELLOW + receiver.getDisplayName());
				receiver.sendMessage(Prefix.server + ChatColor.GRAY + "You received " + ChatColor.GREEN + amount + coin + ChatColor.GRAY
						+ "from " + ChatColor.YELLOW + payer.getDisplayName());
				yes(payer);
				yes(receiver);
			}
		} else {
			payer.sendMessage(Prefix.server + "Payment need to more than " + ChatColor.YELLOW + "0" + ChatColor.GRAY + ".");
			no(payer);
		}
	}
	
	public static void no(Player p) {
		p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 0);
	}
	
	public static void yes(Player p) {
		p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
	}

	public static void take(Player payer, long amount) {
		String payerName = payer.getName();
		File payerLocate = new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + payerName);
		File payerFile = new File(payerLocate, File.separator + "config.yml");
		FileConfiguration payerData = YamlConfiguration.loadConfiguration(payerFile);
		long payerMoney = payerData.getLong("money");
		if (amount >= 0) {
			if (payerMoney - amount > 0) {
				try {
					payerData.set("money", payerMoney - amount);
					payerData.save(payerFile);
				} catch (IOException e) {
					Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
				}
				
				if (amount == Math.abs(1) || amount == 0) {
					coin = " Coin ";
				}
				
				payer.sendMessage(Prefix.server + ChatColor.GRAY + "You paid " + ChatColor.GREEN + amount + coin + ChatColor.GRAY
						+ "to " + ChatColor.YELLOW + "CONSOLE.");
				
			} else {
				payer.sendMessage(Prefix.server + Prefix.nom);
				no(payer);
			}
		} else {
			payer.sendMessage(Prefix.server + "Payment need to more than " + ChatColor.YELLOW + "0" + ChatColor.GRAY + ".");
			no(payer);
		}
	}

	public static void give(Player receiver, long amount) {
		String receiverName = receiver.getName();
		File receiverLocate = new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + receiverName);
		File receiverFile = new File(receiverLocate, File.separator + "config.yml");
		FileConfiguration receiverData = YamlConfiguration.loadConfiguration(receiverFile);
		long receiverMoney = receiverData.getLong("money");

		try {
			receiverData.set("money", receiverMoney + amount);
			receiverData.save(receiverFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (amount == 1) {
			coin = " Coin ";
		}

		receiver.sendMessage(Prefix.server + ChatColor.GRAY + "You received " + ChatColor.GREEN + amount + coin + ChatColor.GRAY
				+ "from " + ChatColor.YELLOW + "CONSOLE.");
		yes(receiver);
	}

}
