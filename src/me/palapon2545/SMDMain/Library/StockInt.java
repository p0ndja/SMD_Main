package me.palapon2545.SMDMain.Library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;

public class StockInt {
	public static long CountdownLength = -2;
	public static long CountdownStartLength = -2;
	public static String CountdownMessage = null;
	public static boolean BarAPIHook = false;
	public static boolean BossBarAPIHook = false;
	public static boolean LoginFeature = false;
	public static boolean spawnOnJoin = false;
	public static boolean privateServerPondJa = false;
	public static int ServerVersion = 0;
	
	public static int timeToSetAFK = 600;
	
	public static long moneyDonated = 0;
	public static long moneyTargeted = 1;
	
	public static List<String> blockLogin = new ArrayList<String>();
	
	public static List<String> voteToRestart = new ArrayList<String>();
	
	public static String pluginName = "PondJa-Core";
			
	public static String pluginDir = "plugins/" + pluginName + "/";
	
	public static Material[] luckyClick_Simple = {Material.DIRT};
	public static Material[] luckyClick_High = {Material.DIAMOND, Material.COBBLESTONE};
	public static Material[] luckyClick_Rare = {Material.DIAMOND_BLOCK, Material.EMERALD, };
}
