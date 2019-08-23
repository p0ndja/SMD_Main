package me.palapon2545.SMDMain.Core;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.palapon2545.SMDMain.Library.Prefix;
import me.palapon2545.SMDMain.Library.StockInt;

public class Login {

	public static HashMap<String,Long> LoginCount = new HashMap<String,Long>();
	
	public static void loginTask() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			String playerName = p.getName();
			if (StockInt.blockLogin.contains(playerName)) {
				if (Login.LoginCount.get(playerName) == 20) {
					p.kickPlayer(
							"Login Timeout (60 Seconds), Please re-join and try again.\nIf you forget password please contact at Fanpage\nhttps://www.facebook.com/mineskymc");
				} else {
					p.sendMessage("");
					p.sendMessage(Prefix.server + "Please login or register!");
					p.sendMessage(Prefix.type + " - /register [password] [email]");
					p.sendMessage(Prefix.type + " - /login [password]");
					p.sendMessage("");
					Login.LoginCount.put(playerName, Login.LoginCount.get(playerName) + 1);
				}
			}
		}
	}

}
