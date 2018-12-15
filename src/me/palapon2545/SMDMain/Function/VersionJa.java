package me.palapon2545.SMDMain.Function;

import org.bukkit.Bukkit;

import me.palapon2545.SMDMain.Library.StockInt;

public class VersionJa {

	public static void main() {
		String ver = Bukkit.getServer().getVersion();
		if (ver.contains("1.8"))
			StockInt.ServerVersion = 1;
		else if (ver.contains("1.9") || ver.contains("1.10") || ver.contains("1.11") || ver.contains("1.12"))
			StockInt.ServerVersion = 2;
		else if (ver.contains("1.13"))
			StockInt.ServerVersion = 3;
		else
			StockInt.ServerVersion = 0;
	}
}
