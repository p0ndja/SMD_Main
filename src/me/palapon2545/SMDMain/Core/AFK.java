package me.palapon2545.SMDMain.Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.palapon2545.SMDMain.Library.StockInt;

public class AFK {
	
	public static HashMap<String,Long> AFKCount = new HashMap<String,Long>();
	public static List<String> AFKListName = new ArrayList<String>();

	public static void createAFKData(Player p) {
		AFKCount.put(p.getName(), (long) 0);
	}

	public static void afkLoop() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			long level = AFKCount.get(p.getName());
			if (AFKListName.contains(p.getName()) && level == -1)
				noLongerAFK(p);
			AFKCount.put(p.getName(), level + 1);
			if (level >= StockInt.timeToSetAFK + 1 && !AFKListName.contains(p.getName()))
				setAFK(p);
		}
	}

	public static void setAFK(Player p) {
		Bukkit.broadcastMessage(
				p.getDisplayName() + ChatColor.WHITE + " is now " + ChatColor.BOLD + "AFK" + ChatColor.RESET + ".");
		p.setDisplayName("[AFK]" + ChatColor.RESET + p.getDisplayName());
		p.setPlayerListName(p.getDisplayName());
		AFKListName.add(p.getName());
	}

	public static void noLongerAFK(Player p) {
		if (AFKListName.contains(p.getName())) {
			long afktime = AFKCount.get(p.getName()) - StockInt.timeToSetAFK;
			long h = afktime / 3600;
			long m = (afktime % 3600) / 60;
			long s = (afktime % 3600) % 60;
			String sh = h + "h";
			String sm = m + "m";
			String ss = s + "s";
			if (h == 0)
				sh = "";
			if (m == 0)
				sm = "";
			if (s == 0)
				ss = "";
			AFKListName.remove(p.getName());
			p.setDisplayName(Rank.rankColor(Rank.getRank(p)) + p.getName());
			p.setPlayerListName(p.getDisplayName());
			AFKCount.put(p.getName(), (long) 0);
			Bukkit.broadcastMessage(p.getDisplayName() + ChatColor.WHITE + " is no longer " + ChatColor.BOLD + "AFK"
					+ ChatColor.RESET + ". " + ChatColor.YELLOW + ChatColor.ITALIC + "(" + sh + sm + ss + ")");
		} else {
			AFKCount.put(p.getName(), (long) 0);
		}
	}
}
