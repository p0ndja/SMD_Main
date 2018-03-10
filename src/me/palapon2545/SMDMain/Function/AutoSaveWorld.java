package me.palapon2545.SMDMain.Function;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import me.palapon2545.SMDMain.Library.Prefix;

public class AutoSaveWorld {
	
	public static boolean save() {
		int player = Bukkit.getServer().getOnlinePlayers().size();
		if (player > 0) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage(Prefix.sv + ChatColor.AQUA + "World and Player data has been saved.");
				p.saveData();
			}
			for (World w : Bukkit.getWorlds()) {
				w.save();
			}
		}
		return false;
	}
	
}
