package me.palapon2545.SMDMain.Function;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Ping {
	public static int get(Player p) {
		Class<?> CPClass;
		String bpName = Bukkit.getServer().getClass().getPackage().getName(),
				version = bpName.substring(bpName.lastIndexOf(".") + 1, bpName.length());
		try {
			CPClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
			Object CraftPlayer = CPClass.cast(p);
			Method getHandle = CraftPlayer.getClass().getMethod("getHandle", new Class[0]);
			Object EntityPlayer = getHandle.invoke(CraftPlayer, new Object[0]);
			Field ping = EntityPlayer.getClass().getDeclaredField("ping");
			return ping.getInt(EntityPlayer);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	public static String pingWithColor(Player p) {
		int ping = Ping.get(p);
		return color(ping) + "" + ping;
	}
	
	public static ChatColor color(Player p) {
		ChatColor color;
		int ping = Ping.get(p);
		if (ping < 31)
			color = ChatColor.AQUA;
		else if (ping > 30 && ping < 81)
			color = ChatColor.GREEN;
		else if (ping > 80 && ping < 151)
			color = ChatColor.GOLD;
		else if (ping > 150 && ping < 501)
			color = ChatColor.RED;
		else if (ping > 500)
			color = ChatColor.DARK_RED;
		else
			color = ChatColor.WHITE;
		return color;
	}
	
	public static ChatColor color(int ping) {
		ChatColor color;
		if (ping < 31)
			color = ChatColor.AQUA;
		else if (ping > 30 && ping < 81)
			color = ChatColor.GREEN;
		else if (ping > 80 && ping < 151)
			color = ChatColor.GOLD;
		else if (ping > 150 && ping < 501)
			color = ChatColor.RED;
		else if (ping > 500)
			color = ChatColor.DARK_RED;
		else
			color = ChatColor.WHITE;
		return color;
	}
}
