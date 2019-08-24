package me.palapon2545.SMDMain.Core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.palapon2545.SMDMain.Function.Function;
import me.palapon2545.SMDMain.Library.Prefix;
import me.palapon2545.SMDMain.Library.StockInt;
import me.palapon2545.SMDMain.Main.pluginMain;

public class Rank {

	pluginMain pl;

	public Rank(pluginMain pl) {
		this.pl = pl;
	}

	public static String Staff = ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Staff" + ChatColor.BLUE;
	public static String Vip = ChatColor.GREEN + "" + ChatColor.BOLD + "VIP" + ChatColor.DARK_GREEN;
	public static String Admin = ChatColor.DARK_RED + "" + ChatColor.BOLD + "Admin" + ChatColor.RED;
	public static String Owner = ChatColor.GOLD + "" + ChatColor.BOLD + "Owner" + ChatColor.YELLOW;
	public static String Builder = ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Builder" + ChatColor.GREEN;
	public static String Helper = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Helper" + ChatColor.WHITE;
	public static String Default = ChatColor.BLUE + "";

	public static String prefix(String rank) {
		rank.toLowerCase();
		if (rank.equalsIgnoreCase("owner"))
			return Rank.Owner;
		else if (rank.equalsIgnoreCase("admin"))
			return Rank.Admin;
		else if (rank.equalsIgnoreCase("staff"))
			return Rank.Staff;
		else if (rank.equalsIgnoreCase("builder"))
			return Rank.Builder;
		else if (rank.equalsIgnoreCase("vip"))
			return Rank.Vip;
		else if (rank.equalsIgnoreCase("helper"))
			return Rank.Helper;
		else
			return Rank.Default;
	}

	public static boolean setRank(Player p, String rank) {
		rank.toLowerCase();

		if (getPriority(rank) == -1) {
			Prefix.sendErrorMessage("Rank", "setRank(p, rank)", p.getName(), "Rank " + rank + " not found");
			return false;
		}

		PlayerDatabase.set(p, "rank", rank);
		for (Player po : Bukkit.getOnlinePlayers())
			Function.yes(po);
		
		String prefix = prefix(rank);
		if (rank == "default")
			prefix = "DEFAULT";
		Bukkit.broadcastMessage(Prefix.database + "Player " + ChatColor.YELLOW + p.getName() + "'s rank "
				+ ChatColor.GRAY + "has been updated to " + prefix);
		
		setDisplay(p);

		return true;

	}

	public static void setDisplay(Player p) {
		p.setDisplayName(prefix(getRank(p)) + p.getName() + ChatColor.RESET);
		p.setPlayerListName(prefix(getRank(p)) + p.getName() + ChatColor.RESET);
	}

	public static int getPriority(String rank) {
		rank.toLowerCase();
		if (rank.equalsIgnoreCase("owner") || rank.equalsIgnoreCase("admin"))
			return 5;
		else if (rank.equalsIgnoreCase("staff"))
			return 3;
		else if (rank.equalsIgnoreCase("builder"))
			return 4;
		else if (rank.equalsIgnoreCase("helper"))
			return 2;
		else if (rank.equalsIgnoreCase("vip"))
			return 1;
		else if (rank.equalsIgnoreCase("default"))
			return 0;
		else 
			return -1;
	}

	public static int getPriority(Player p) {
		return getPriority(getRank(p));
	}

	public static String getRank(Player p) {
		// FileConfiguration playerData = YamlConfiguration.loadConfiguration(new
		// File(new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" +
		// p.getName()), File.separator + "config.yml"));
		return (String) PlayerDatabase.get(p, "rank");
	}

	public static boolean enoughRank(Player p, int a) {
		if (getPriority(p) >= a)
			return true;
		else
			return false;
	}

	public static boolean enoughRank(Player p, String r) {
		if (getPriority(p) >= getPriority(r))
			return true;
		else
			return false;
	}
}
