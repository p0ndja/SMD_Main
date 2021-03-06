package me.palapon2545.SMDMain.Core;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.palapon2545.SMDMain.Library.Prefix;
import me.palapon2545.SMDMain.Library.StockInt;

public class PlayerDatabase {

	public static FileConfiguration get(Player p) {
		return YamlConfiguration.loadConfiguration(
				new File(new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + p.getName()),
						File.separator + "config.yml"));
	}

	public static FileConfiguration getFile(Player p, String fileName) {
		return YamlConfiguration.loadConfiguration(
				new File(new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + p.getName()),
						File.separator + fileName + ".yml"));
	}
	
	public static Object get(Player p, String whatYouWant) {
		return YamlConfiguration.loadConfiguration(
				new File(new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + p.getName()),
						File.separator + "config.yml")).get(whatYouWant);
	}

	public static void set(Player p, String key, Object value) {
		File userdata = new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + p.getName());
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		try {
			playerData.set(key, value);
			playerData.save(f);
		} catch (IOException e) {
			e.printStackTrace();
			Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
		}
	}

	public static void set(Player p, String fileName, String key, String value) {
		File userdata = new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + p.getName());
		File f = new File(userdata, File.separator + fileName + ".yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		try {
			playerData.set(key, value);
			playerData.save(f);
		} catch (IOException e) {
			e.printStackTrace();
			Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
		}
	}
}
