package me.palapon2545.SMDMain.Function;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import me.palapon2545.SMDMain.Library.Prefix;
import me.palapon2545.SMDMain.Library.StockInt;
import me.palapon2545.SMDMain.Main.pluginMain;
import me.palapon2545.SMDMain.Function.BossBar;

public class Countdown {

	static boolean CountdownDisplayMessageBoolean = false;
	
	public static boolean Countdown() {
		long c = StockInt.CountdownLength;
		long n = c - 1;
		long value = c;
		long h = value / 3600;
		long m = value % 3600;
		long s = m % 60;
		if (StockInt.CountdownMessage.equalsIgnoreCase("null")) {
			CalculateTimer();
		} else {
			if (s % 4 == 0) {
				if (c < 11) {
					CountdownDisplayMessageBoolean = false;
				} else {
					if (CountdownDisplayMessageBoolean == true) {
						CountdownDisplayMessageBoolean = false;
					} else {
						CountdownDisplayMessageBoolean = true;
					}
				}
			}
			if (CountdownDisplayMessageBoolean == true) {
				if (StockInt.BarAPIHook == true) {
					// long p = cn / an;
					// Bukkit.broadcastMessage("debug_percent");
					BossBar.sendBarAll(Prefix.cd + StockInt.CountdownMessage);
				} else {
					ActionBarAPI.sendToAll(Prefix.cd + StockInt.CountdownMessage);
				}
			} else {
				CalculateTimer();
			}
		}
		if (c == -2) {
			StockInt.CountdownLength = -2;
		} else {
			StockInt.CountdownLength = n;
		}
		return false;
	}
	
	public static void CalculateTimer() {
		long c = StockInt.CountdownLength;
		long w = c / 604800;
		long wm = c % 604800;
		long d = wm / 86400;
		long dm = wm % 86400;
		long h = dm / 3600;
		long hm = dm % 3600;
		long m = hm / 60;
		long s = hm % 60;
		String week = "";
		String day = "";
		String hour = "";
		String minute = "";
		String second = "";

		if (w > 1) {
			week = w + " weeks ";
		}
		if (w == 1) {
			week = w + " week ";
		}
		if (w == 0) {
			week = "";
		}

		if (w > 1) {
			week = w + " weeks ";
		}
		if (w == 1) {
			week = w + " week ";
		}
		if (w == 0) {
			week = "";
		}

		if (d > 1) {
			day = d + " days ";
		}
		if (d == 1) {
			day = d + " day ";
		}
		if (d == 0) {
			hour = "";
		}

		if (h > 1) {
			hour = h + " hours ";
		}
		if (h == 1) {
			hour = h + " hour ";
		}
		if (h == 0) {
			hour = "";
		}

		if (m > 1) {
			minute = m + " minutes ";
		}
		if (m == 1) {
			minute = m + " minute ";
		}
		if (m == 0) {
			minute = "";
		}

		if (s > 1) {
			second = s + " seconds";
		}
		if (s == 1) {
			second = s + " second";
		}
		if (s == 0) {
			second = "";
		}

		if (c > 5) {
			second = s + " seconds";
		}
		if (c == 5) {
			second = ChatColor.AQUA + "" + s + " seconds";
		}
		if (c == 4) {
			second = ChatColor.GREEN + "" + s + " seconds";
		}
		if (c == 3) {
			second = ChatColor.YELLOW + "" + s + " seconds";
		}
		if (c == 2) {
			second = ChatColor.GOLD + "" + s + " seconds";
		}
		if (c == 1) {
			second = ChatColor.RED + "" + s + " second";
		}
		if (c == 0) {
			second = ChatColor.LIGHT_PURPLE + "TIME UP!";
		} if (c == -1) {
			BossBar.removeBarAll();
		}

		if (c >= 0) {
			if (StockInt.BarAPIHook == true) {
				BossBar.sendBarAll(Prefix.cd + week + day + hour + minute + second);
			} else {
				ActionBarAPI.sendToAll(Prefix.cd + week + day + hour + minute + second);
			}
		}

	}

}
