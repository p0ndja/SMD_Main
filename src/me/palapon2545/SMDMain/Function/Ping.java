package me.palapon2545.SMDMain.Function;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
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
}
