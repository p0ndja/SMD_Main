package me.palapon2545.SMDMain.Library;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public class StockInt {
	public static long CountdownLength = -2;
	public static long CountdownStartLength = -2;
	public static String CountdownMessage = "null";
	public static boolean BarAPIHook = false;
	public static boolean BossBarAPIHook = false;
	public static boolean LoginFeature = false;
	public static int ServerVersion = 0;
	public static List<String> blockLogin = new ArrayList<String>();
	public static List<String> afkListName = new ArrayList<String>();
	
	public static List<String> pleaseDropItemBeforeChat = new ArrayList<String>();
	
	public static String pluginDir = "plugins/SMDMain/";
	
	public static Material[] luckyClick_Simple = {Material.DIRT};
	public static Material[] luckyClick_High = {Material.DIAMOND, Material.COBBLESTONE};
	public static Material[] luckyClick_Rare = {Material.DIAMOND_BLOCK, Material.EMERALD, };
}
