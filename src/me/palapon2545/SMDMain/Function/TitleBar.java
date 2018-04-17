package me.palapon2545.SMDMain.Function;

import java.lang.reflect.Constructor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TitleBar extends JavaPlugin implements Listener {
	public static void sendTitle(Player player, String text, int fadeInTime, int showTime, int fadeOutTime,
			ChatColor color) {
		try {
			Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
					.invoke(null, "{\"text\": \"" + text + "\",color:" + color.name().toLowerCase() + "}");

			Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
					getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
					int.class, int.class, int.class);
			Object packet = titleConstructor.newInstance(
					getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle,
					fadeInTime, showTime, fadeOutTime);

			sendPacket(player, packet);
		}

		catch (Exception ex) {
			// Do something
		}
	}

	public static void sendSubTitle(Player player, String text, int fadeInTime, int showTime, int fadeOutTime,
			ChatColor color) {
		try {
			Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
					.invoke(null, "{\"text\": \"" + text + "\",color:" + color.name().toLowerCase() + "}");

			Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
					getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
					int.class, int.class, int.class);
			Object packet = titleConstructor.newInstance(
					getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatTitle,
					fadeInTime, showTime, fadeOutTime);

			sendPacket(player, packet);
		}

		catch (Exception ex) {
			// Do something
		}
	}

	private static void sendPacket(Player player, Object packet) {
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
		} catch (Exception ex) {
			// Do something
		}
	}

	/**
	 * Get NMS class using reflection
	 * 
	 * @param name
	 *            Name of the class
	 * @return Class
	 */
	private static Class<?> getNMSClass(String name) {
		try {
			return Class.forName("net.minecraft.server"
					+ Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
		} catch (ClassNotFoundException ex) {
			// Do something
		}
		return null;
	}
}
