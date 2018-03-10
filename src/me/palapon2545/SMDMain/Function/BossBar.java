package me.palapon2545.SMDMain.Function;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.confuser.barapi.BarAPI;

public class BossBar {
	public static void sendBar(Player p, String s) {
		BarAPI.setMessage(p, s);
	}

	public static void sendBarAll(String s) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			BarAPI.setMessage(p, s);
		}
	}

	public static void setBarHealth(Player p, float percent) {
		BarAPI.setHealth(p, percent);
	}

	public static void setBarHealthAll(float percent) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			BarAPI.setHealth(p, percent);
		}
	}

	public static void removeBar(Player p) {
		BarAPI.removeBar(p);
	}

	public static void removeBarAll() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			BarAPI.removeBar(p);
		}
	}
}
