package me.palapon2545.SMDMain.Function;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.*;
import org.bukkit.entity.Player;

public class BossBarAPI {
	
	public static void create(String title, BarColor color, BarStyle style, BarFlag flags) {
		BossBar bar = (BossBar) Bukkit.createBossBar(title, color, style, flags);
		bar.setVisible(true);
	}
	
	public static void remove(NamespacedKey title) {
		Bukkit.removeBossBar(title);
	}
	
	public static void show(NamespacedKey title, Player p) {
		BossBar bar = (BossBar) Bukkit.getBossBar(title);
		bar.addPlayer(p);
	}

	public static void show(NamespacedKey title) {
		BossBar bar = (BossBar) Bukkit.getBossBar(title);
		for (Player p : Bukkit.getOnlinePlayers()) bar.addPlayer(p);
	}
	
	public static void hide(NamespacedKey title, Player p) {
		BossBar bar = (BossBar) Bukkit.getBossBar(title);
		bar.removePlayer(p);
	}
	
	public static void hide(NamespacedKey title) {
		BossBar bar = (BossBar) Bukkit.getBossBar(title);
		for (Player p : Bukkit.getOnlinePlayers()) bar.removePlayer(p);
	}
}
