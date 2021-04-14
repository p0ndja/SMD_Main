package me.palapon2545.SMDMain.Function;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.ancash.actionbar.ActionBarAPI;

public class ActionBarAPI_api {
	
	public static void send(Player p, String msg) {
		ActionBarAPI.sendActionBar(p, msg);
	}
	
	public static void send(String msg) {
		for (Player p : Bukkit.getOnlinePlayers()) { 
			send(p, msg);	
		}
	}
}
