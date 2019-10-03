package me.palapon2545.SMDMain.Core;

import org.bukkit.entity.Player;

public class Quota {
	
	public static long get(Player p, String quota) {
		return (long) PlayerDatabase.get(p, "Quota." + quota);
	}
	
	public static boolean give(Player p, String quota, long l) {
		quota.toLowerCase();
		if (set(p, quota, get(p, quota) + 1)) { //Maybe check
			set(p, quota, get(p, quota) + 1);
			return true;
		}
		return false;
	}
	
	public static boolean set(Player p, String quota, long l) {
		quota.toLowerCase();
		if (quota.equalsIgnoreCase("luckyclick") || quota.equalsIgnoreCase("tpr") || quota.equalsIgnoreCase("sethome")) {
			PlayerDatabase.set(p, "Quota." + quota, l);
			return true;
		}
		return false;
	}
	
	public static boolean take(Player p, String quota, long l) {
		if (get(p, quota) - l < 0)
			return false;
		if (set(p, quota, get(p, quota) - 1)) { //Maybe check
			set(p, quota, get(p, quota) - 1);
			return true;
		}
		return false;
	}
}
