package me.palapon2545.SMDMain.Library;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public class StockInt {
	public static long CountdownLength = -3;
	public static long CountdownStartLength = -3;
	public static String CountdownMessage = "null";
	public static boolean BarAPIHook = false;
	public static boolean LoginFeature = false;
	public static List<String> blockLogin = new ArrayList<String>();
	
	public static Material[] luckyClick_Simple = {Material.DIRT};
	public static Material[] luckyClick_High = {Material.DIAMOND, Material.COBBLESTONE};
	public static Material[] luckyClick_Rare = {Material.DIAMOND_BLOCK, Material.EMERALD, };
}
