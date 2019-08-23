package me.palapon2545.SMDMain.Core;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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

	public static String rankColor(String rank) {
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
		else
			return 0;
	}

	public static int getPriority(Player p) {
		return getPriority(getRank(p));
	}

	public static String getRank(Player p) {
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(
				new File(new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + p.getName()),
						File.separator + "config.yml"));
		return playerData.getString("rank");
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
