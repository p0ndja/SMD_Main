package me.palapon2545.SMDMain.Library;

import org.bukkit.ChatColor;

public class Prefix {
	public static String server = ChatColor.BLUE + "Server> " + ChatColor.GRAY;
	public static String event = ChatColor.LIGHT_PURPLE + "Event> " + ChatColor.WHITE;
	public static String portal = ChatColor.DARK_PURPLE + "Portal> " + ChatColor.GRAY;
	public static String j = ChatColor.GREEN + "Join> ";
	public static String permission = ChatColor.DARK_RED + "Permission> " + ChatColor.RED + ChatColor.BOLD + "ACCESS DENIED!" + ChatColor.GRAY
			+ " You don't have enough permission!";
	public static String l = ChatColor.RED + "Left> ";
	public static String lc = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Lucky" + ChatColor.YELLOW + ChatColor.BOLD
			+ "Click> " + ChatColor.WHITE;
	public static String type = ChatColor.GRAY + "Type: " + ChatColor.GREEN;
	
	public static String notEnoughItem = ChatColor.RED + "You don't have enough item.";
	public static String nom = ChatColor.RED + "You don't have enough money.";
	public static String non = ChatColor.GRAY + " is not number." + ChatColor.DARK_GRAY + ChatColor.ITALIC +" [Not support decimal]";
	public static String database_error = ChatColor.RED + "There are some errors that interrupt database.";
	public static String database = ChatColor.GOLD + "Database> " + ChatColor.GRAY;
	public static String cd = ChatColor.AQUA + "" + ChatColor.BOLD + "[Countdown]: " + ChatColor.WHITE;
	public static String tc = ChatColor.AQUA + "" + ChatColor.BOLD + " ..Teleporting.. ";
	public static String tcc = ChatColor.DARK_RED + "" + ChatColor.BOLD + " Teleportation cancelled! ";
	
	public static String Ampersand = Character.toString((char) 0x00A7);
}
