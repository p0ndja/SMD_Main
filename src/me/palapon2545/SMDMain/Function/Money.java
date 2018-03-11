package me.palapon2545.SMDMain.Function;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.palapon2545.SMDMain.Library.Prefix;
import me.palapon2545.SMDMain.Main.pluginMain;

public class Money extends JavaPlugin {

	static pluginMain pl;

	public Money(pluginMain pl) {
		this.pl = pl;
	}

	public static void tranfer(Player payer, Player receiver, long amount) {
		String payerName = payer.getName();
		File payerLocate = new File(pl.getDataFolder(), File.separator + "PlayerDatabase/" + payerName);
		File payerFile = new File(payerLocate, File.separator + "config.yml");
		FileConfiguration payerData = YamlConfiguration.loadConfiguration(payerFile);
		String receiverName = receiver.getName();
		File receiverLocate = new File(pl.getDataFolder(), File.separator + "PlayerDatabase/" + receiverName);
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
					Bukkit.broadcastMessage(Prefix.db + Prefix.dbe);
				}
				payer.sendMessage(Prefix.sv + ChatColor.GRAY + "You paid " + ChatColor.GREEN + amount + ChatColor.GRAY
						+ " to " + ChatColor.YELLOW + receiver.getDisplayName());
				receiver.sendMessage(Prefix.sv + ChatColor.GRAY + "You received " + ChatColor.GREEN + amount + ChatColor.GRAY
						+ " from " + ChatColor.YELLOW + payer.getDisplayName());
				pl.yes(payer);
				pl.yes(receiver);
			}
		} else {
			payer.sendMessage(Prefix.sv + "Payment need to more than " + ChatColor.YELLOW + "0" + ChatColor.GRAY + ".");
			pl.no(payer);
		}
	}

	public static void take(Player payer, long amount) {
		String payerName = payer.getName();
		File payerLocate = new File(pl.getDataFolder(), File.separator + "PlayerDatabase/" + payerName);
		File payerFile = new File(payerLocate, File.separator + "config.yml");
		FileConfiguration payerData = YamlConfiguration.loadConfiguration(payerFile);
		long payerMoney = payerData.getLong("money");
		if (amount >= 0) {
			if (payerMoney - amount > 0) {
				try {
					payerData.set("money", payerMoney - amount);
					payerData.save(payerFile);
				} catch (IOException e) {
					Bukkit.broadcastMessage(Prefix.db + Prefix.dbe);
				}
				
				payer.sendMessage(Prefix.sv + ChatColor.GRAY + "You paid " + ChatColor.GREEN + amount + ChatColor.GRAY
						+ " to " + ChatColor.YELLOW + "CONSOLE.");
				
			} else {
				payer.sendMessage(Prefix.sv + Prefix.nom);
				pl.no(payer);
			}
		} else {
			payer.sendMessage(Prefix.sv + "Payment need to more than " + ChatColor.YELLOW + "0" + ChatColor.GRAY + ".");
			pl.no(payer);
		}
	}

	public static void give(Player receiver, long amount) {
		String receiverName = receiver.getName();
		File receiverLocate = new File(pl.getDataFolder(), File.separator + "PlayerDatabase/" + receiverName);
		File receiverFile = new File(receiverLocate, File.separator + "config.yml");
		FileConfiguration receiverData = YamlConfiguration.loadConfiguration(receiverFile);
		long receiverMoney = receiverData.getLong("money");

		try {
			receiverData.set("money", receiverMoney + amount);
			receiverData.save(receiverFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		receiver.sendMessage(Prefix.sv + ChatColor.GRAY + "You received " + ChatColor.GREEN + amount + ChatColor.GRAY
				+ " from " + ChatColor.YELLOW + "CONSOLE.");
		pl.yes(receiver);
	}

}
