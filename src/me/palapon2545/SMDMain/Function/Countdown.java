package me.palapon2545.SMDMain.Function;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import me.palapon2545.SMDMain.Library.Prefix;
import me.palapon2545.SMDMain.Library.StockInt;
import me.palapon2545.SMDMain.Function.BarAPI_api;

public class Countdown {

	private static boolean CountdownDisplayMessageBoolean = false;

	public static void run() {
		long c = StockInt.CountdownLength - 1;
		long s = (c % 3600) % 60;
		float curPercent = (c*100 / StockInt.CountdownStartLength);
		if (StockInt.CountdownMessage != null) {
			if (s % 4 == 0) {
				if (c < 11)
					CountdownDisplayMessageBoolean = false;
				else
					CountdownDisplayMessageBoolean = !CountdownDisplayMessageBoolean;
				
			}
			if (CountdownDisplayMessageBoolean) {
				if (StockInt.BarAPIHook) {
					// long p = cn / an;
					// Bukkit.broadcastMessage("debug_percent");
					BarAPI_api.sendBarAll(Prefix.cd + StockInt.CountdownMessage, curPercent);
				} else {
					ActionBarAPI_api.send(Prefix.cd + StockInt.CountdownMessage);
				}
			} else {
				CalculateTimer();
			}
		} else {
			CalculateTimer();
		}
		if (c == -2)
			StockInt.CountdownLength = -2;
		else
			StockInt.CountdownLength = c;

	}

	public static void CalculateTimer() {
		long c = StockInt.CountdownLength;
		String displayMsg = Function.calTime(c);
		if (c == 5) {
			Function.pling(2);
		} else if (c == 4) {
			Function.pling((float) 1.8);
		} else if (c == 3) {
			Function.pling((float) 1.6);
		} else if (c == 2) {
			Function.pling((float) 1.4);
		} else if (c == 1) {
			Function.pling((float) 1.2);
		} else if (c == 0) {
			Function.pling((float) 0);
		} else if (c == -1) {
			if (StockInt.BarAPIHook == true) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				BarAPI_api.removeBarAll();
			}
		}

		float curPercent = (c*100 / StockInt.CountdownStartLength);
		if (c >= 0) {
			if (StockInt.BarAPIHook == true)
				BarAPI_api.sendBarAll(Prefix.cd + displayMsg, (float) curPercent);
			else
				ActionBarAPI_api.send(Prefix.cd + displayMsg);
		}


	}

}
