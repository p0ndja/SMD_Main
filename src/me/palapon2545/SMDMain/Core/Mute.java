package me.palapon2545.SMDMain.Core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.palapon2545.SMDMain.Function.Function;

public class Mute {
	public static List<String> list = new ArrayList<String>();

	public static boolean isMute(Player p) {
		if (list.contains(p.getName())) {
			sendMessage(p);
			return true;
		} else
			return false;
	}

	public static void setMute(Player p, boolean b) {
		if (b) {
			list.add(p.getName());
		} else {
			list.remove(p.getName());
		}
	}

	public static void sendMessage(Player p) {
		p.sendMessage(ChatColor.BLUE + "Chat> " + ChatColor.GRAY + "You have been muted.");
		p.sendMessage(ChatColor.BLUE + "Chat> " + ChatColor.YELLOW + "Reason: " + ChatColor.GRAY + "*undefined*");
		Function.no(p);
	}
}
