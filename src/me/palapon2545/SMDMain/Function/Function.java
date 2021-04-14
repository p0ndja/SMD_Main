package me.palapon2545.SMDMain.Function;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.palapon2545.SMDMain.Function.Sound;
import me.palapon2545.SMDMain.Library.Prefix;
import me.palapon2545.SMDMain.Library.StockInt;

public class Function {
	public static String makeFirstCapital(String original) {
		if (original == null || original.length() == 0)
			return original;
		return original.substring(0, 1).toUpperCase() + original.substring(1);
	}
	
	public static void no(Player p) {
		p.playSound(p.getLocation(), Sound.NOTE_BASS.bukkitSound(), 1, 0);
	}

	public static void orb(Player p) {
		p.playSound(p.getLocation(), Sound.ORB_PICKUP.bukkitSound(), 1, 1);
	}

	public static void orb(Player p, float pitch) {
		p.playSound(p.getLocation(), Sound.ORB_PICKUP.bukkitSound(), 1, pitch);
	}

	public static void anvil(Player p) {
		p.playSound(p.getLocation(), Sound.ANVIL_LAND.bukkitSound(), 1, 1);
	}

	public static void anvil(Player p, float pitch) {
		p.playSound(p.getLocation(), Sound.ANVIL_LAND.bukkitSound(), 1, pitch);
	}

	public static void pling(Player p) {
		p.playSound(p.getLocation(), Sound.NOTE_PLING.bukkitSound(), 1, 0);
	}

	public static void pling(Player p, float pitch) {
		p.playSound(p.getLocation(), Sound.NOTE_PLING.bukkitSound(), 1, pitch);
	}
	
	public static void pling(float pitch) {
		for (Player p : Bukkit.getOnlinePlayers())
			p.playSound(p.getLocation(), Sound.NOTE_PLING.bukkitSound(), 1, pitch);
	}

	public static void egg(Player p) {
		p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP.bukkitSound(), 1, 0);
	}

	public static void egg(Player p, float pitch) {
		p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP.bukkitSound(), 1, pitch);
	}

	public static void yes(Player p) {
		p.playSound(p.getLocation(), Sound.LEVEL_UP.bukkitSound(), 1, 1);
	}

	public static void yes() {
		for (Player p : Bukkit.getOnlinePlayers())
			yes(p);
	}

	public static void pickup(Player p) {
		p.playSound(p.getLocation(), Sound.ITEM_PICKUP.bukkitSound(), 1, 1);
	}

	public static void pickup(Player p, float pitch) {
		p.playSound(p.getLocation(), Sound.ITEM_PICKUP.bukkitSound(), 1, pitch);
	}
	
	public static String calTime(long second) {
		String stackMsg = "";
		
		long c = second;
		long w = c / 604800;
		long wm = c % 604800;
		long d = wm / 86400;
		long dm = wm % 86400;
		long h = dm / 3600;
		long hm = dm % 3600;
		long m = hm / 60;
		long s = hm % 60;

		if (w > 1)
			stackMsg += w + " weeks ";
		else if (w == 1)
			stackMsg += w + " week ";

		if (d > 1)
			stackMsg += d + " days ";
		else if (d == 1)
			stackMsg += d + " day ";

		if (h > 1)
			stackMsg += h + " hours ";
		else if (h == 1)
			stackMsg += h + " hour ";

		if (m > 1)
			stackMsg += m + " minutes ";
		else if (m == 1)
			stackMsg += m + " minute ";

		if (s > 1)
			stackMsg += s + " seconds";
		else if (s == 1)
			stackMsg += s + " second";

		if (c == 5) {
			stackMsg = ChatColor.AQUA + stackMsg;
		} else if (c == 4) {
			stackMsg = ChatColor.GREEN + stackMsg;
		} else if (c == 3) {
			stackMsg = ChatColor.YELLOW + stackMsg;
		} else if (c == 2) {
			stackMsg = ChatColor.GOLD + stackMsg;
		} else if (c == 1) {
			stackMsg = ChatColor.RED + stackMsg;
		} else if (c == 0) {
			stackMsg = ChatColor.LIGHT_PURPLE + "TIME UP!";
		}

		return stackMsg;
	}
}
