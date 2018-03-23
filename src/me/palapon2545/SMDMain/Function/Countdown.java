package me.palapon2545.SMDMain.Function;

import org.bukkit.Bukkit;
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
		if (c == -3) {
			StockInt.CountdownLength = -3;
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
		} else if (w == 1) {
			week = w + " week ";
		}

		if (w > 1) {
			week = w + " weeks ";
		} else if (w == 1) {
			week = w + " week ";
		}

		if (d > 1) {
			day = d + " days ";
		} else if (d == 1) {
			day = d + " day ";
		}

		if (h > 1) {
			hour = h + " hours ";
		} else if (h == 1) {
			hour = h + " hour ";
		}

		if (m > 1) {
			minute = m + " minutes ";
		} else if (m == 1) {
			minute = m + " minute ";
		}

		if (s > 1) {
			second = s + " seconds";
		} else if (s == 1) {
			second = s + " second";
		}

		if (c == 5) {
			second = ChatColor.AQUA + "" + s + " seconds";
		} else if (c == 4) {
			second = ChatColor.GREEN + "" + s + " seconds";
		} else if (c == 3) {
			second = ChatColor.YELLOW + "" + s + " seconds";
		} else if (c == 2) {
			second = ChatColor.GOLD + "" + s + " seconds";
		} else if (c == 1) {
			second = ChatColor.RED + "" + s + " second";
		} else if (c == 0) {
			second = ChatColor.LIGHT_PURPLE + "TIME UP!";
		} else if (c == -2) {
			if (StockInt.BarAPIHook == true) {
				BossBar.removeBarAll();
			}
		} else {
			// NOTHING
		}

		long percent = ((c * 100) / StockInt.CountdownStartLength);

		if (c >= 0) {
			if (StockInt.BarAPIHook == true) {
				BossBar.sendBarAll(Prefix.cd + week + day + hour + minute + second, (float) percent);
			} else {
				ActionBarAPI.sendToAll(Prefix.cd + week + day + hour + minute + second);
			}
		}

	}

}
