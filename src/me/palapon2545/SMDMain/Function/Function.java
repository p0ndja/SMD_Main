package me.palapon2545.SMDMain.Function;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.palapon2545.SMDMain.Library.StockInt;

public class Function {
	public static String makeFirstCapital(String original) {
		if (original == null || original.length() == 0)
			return original;
		return original.substring(0, 1).toUpperCase() + original.substring(1);
	}
	
	public static void no(Player p) {
		Sound a;
		if (StockInt.ServerVersion == 1 || StockInt.ServerVersion == 2)
			a = Sound18to19.NOTE_BASS.bukkitSound();
		else
			a = Sound18to113.NOTE_BASS.bukkitSound();
		p.playSound(p.getLocation(), a, 1, 0);
	}

	public static void orb(Player p) {
		Sound a;
		if (StockInt.ServerVersion == 1 || StockInt.ServerVersion == 2)
			a = Sound18to19.ORB_PICKUP.bukkitSound();
		else
			a = Sound18to113.ORB_PICKUP.bukkitSound();
		p.playSound(p.getLocation(), a, 1, 1);
	}

	public static void orb(Player p, float pitch) {
		Sound a;
		if (StockInt.ServerVersion == 1 || StockInt.ServerVersion == 2)
			a = Sound18to19.ORB_PICKUP.bukkitSound();
		else
			a = Sound18to113.ORB_PICKUP.bukkitSound();
		p.playSound(p.getLocation(), a, 1, pitch);
	}

	public static void anvil(Player p) {
		Sound a;
		if (StockInt.ServerVersion == 1 || StockInt.ServerVersion == 2)
			a = Sound18to19.ANVIL_LAND.bukkitSound();
		else
			a = Sound18to113.ANVIL_LAND.bukkitSound();
		p.playSound(p.getLocation(), a, 1, 1);
	}

	public static void anvil(Player p, float pitch) {
		Sound a;
		if (StockInt.ServerVersion == 1 || StockInt.ServerVersion == 2)
			a = Sound18to19.ANVIL_LAND.bukkitSound();
		else
			a = Sound18to113.ANVIL_LAND.bukkitSound();
		p.playSound(p.getLocation(), a, 1, pitch);
	}

	public static void pling(Player p) {
		Sound a;
		if (StockInt.ServerVersion == 1 || StockInt.ServerVersion == 2)
			a = Sound18to19.NOTE_PLING.bukkitSound();
		else
			a = Sound18to113.NOTE_PLING.bukkitSound();
		p.playSound(p.getLocation(), a, 1, 0);
	}

	public static void pling(Player p, float pitch) {
		Sound a;
		if (StockInt.ServerVersion == 1 || StockInt.ServerVersion == 2)
			a = Sound18to19.NOTE_PLING.bukkitSound();
		else
			a = Sound18to113.NOTE_PLING.bukkitSound();
		p.playSound(p.getLocation(), a, 1, pitch);
	}
	
	public static void pling(float pitch) {
		Sound a;
		if (StockInt.ServerVersion == 1 || StockInt.ServerVersion == 2)
			a = Sound18to19.NOTE_PLING.bukkitSound();
		else
			a = Sound18to113.NOTE_PLING.bukkitSound();
		for (Player p : Bukkit.getOnlinePlayers())
		p.playSound(p.getLocation(), a, 1, pitch);
	}

	public static void egg(Player p) {
		Sound a;
		if (StockInt.ServerVersion == 1 || StockInt.ServerVersion == 2)
			a = Sound18to19.CHICKEN_EGG_POP.bukkitSound();
		else
			a = Sound18to113.CHICKEN_EGG_POP.bukkitSound();
		p.playSound(p.getLocation(), a, 1, 0);
	}

	public static void egg(Player p, float pitch) {
		Sound a;
		if (StockInt.ServerVersion == 1 || StockInt.ServerVersion == 2)
			a = Sound18to19.CHICKEN_EGG_POP.bukkitSound();
		else
			a = Sound18to113.CHICKEN_EGG_POP.bukkitSound();
		p.playSound(p.getLocation(), a, 1, pitch);
	}

	public static void yes(Player p) {
		Sound a;
		if (StockInt.ServerVersion == 1 || StockInt.ServerVersion == 2)
			a = Sound18to19.LEVEL_UP.bukkitSound();
		else
			a = Sound18to113.LEVEL_UP.bukkitSound();
		p.playSound(p.getLocation(), a, 1, 1);
	}

	public static void yesAll() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			yes(p);
		}
	}

	public static void pickup(Player p) {
		Sound a;
		if (StockInt.ServerVersion == 1 || StockInt.ServerVersion == 2)
			a = Sound18to19.ITEM_PICKUP.bukkitSound();
		else
			a = Sound18to113.ITEM_PICKUP.bukkitSound();
		p.playSound(p.getLocation(), a, 1, 1);
	}

	public static void pickup(Player p, float pitch) {
		Sound a;
		if (StockInt.ServerVersion == 1 || StockInt.ServerVersion == 2)
			a = Sound18to19.ITEM_PICKUP.bukkitSound();
		else
			a = Sound18to113.ITEM_PICKUP.bukkitSound();
		p.playSound(p.getLocation(), a, 1, pitch);
	}
}
