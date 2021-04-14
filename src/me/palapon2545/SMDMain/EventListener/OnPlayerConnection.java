package me.palapon2545.SMDMain.EventListener;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.palapon2545.SMDMain.Core.AFK;
import me.palapon2545.SMDMain.Core.Rank;
import me.palapon2545.SMDMain.Function.Function;
import me.palapon2545.SMDMain.Library.Prefix;
import me.palapon2545.SMDMain.Library.StockInt;
import me.palapon2545.SMDMain.Main.pluginMain;

public class OnPlayerConnection implements Listener {

	pluginMain pl;

	public OnPlayerConnection(pluginMain pl) {
		this.pl = pl;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		File userdata = new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		if (!f.exists() || !player.hasPlayedBefore()) {
			try {
				File userfiles = new File(
						StockInt.pluginDir + File.separator + "/PlayerDatabase/" + playerName + "/HomeDatabase");
				if (!userfiles.exists()) {
					userfiles.mkdirs();
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			try {
				playerData.createSection("rank");
				playerData.set("rank", "default");
				playerData.createSection("warn");
				playerData.set("warn", 0);
				playerData.createSection("mute");
				playerData.set("mute.is", false);
				playerData.set("mute.reason", "none");
				playerData.createSection("freeze");
				playerData.set("freeze", false);
				playerData.createSection("uuid");
				playerData.set("uuid", player.getUniqueId().toString());
				playerData.createSection("money");
				playerData.set("money", 0);
				playerData.createSection("Quota");
				playerData.set("Quota.TPR", 0);
				playerData.set("Quota.LuckyClick", 0);
				playerData.set("Quota.Home", 0);
				playerData.createSection("Invisible");
				playerData.set("Invisible", false);
				playerData.createSection("Security");
				playerData.set("Security.password", "none");
				playerData.set("Security.email", "none");
				playerData.createSection("gamemode");
				playerData.set("gamemode", 0);
				playerData.createSection("god");
				playerData.set("god", false);
				pl.getConfig().set("redeem.player." + playerName, false);
				pl.getConfig().set("free_item." + playerName, false);
				pl.getConfig().set("event.queuelist." + playerName, false);
				pl.saveConfig();
				playerData.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (f.exists()) {
			if (playerData.getBoolean("Invisible")) {
				player.sendMessage(Prefix.server + "You're now " + ChatColor.AQUA + "invisible.");
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.hasPermission("main.seeinvisible") || p.isOp() || p.hasPermission("main.*"))
						p.showPlayer(this.pl, player);
					else
						p.hidePlayer(this.pl, player);
				}
			}
			try {
				playerData.createSection("uuid");
				playerData.set("uuid", player.getUniqueId().toString());
				playerData.set("Security.freeze", true);
				playerData.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			int countwarn = playerData.getInt("warn");
			if (countwarn > 0) {
				player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "ALERT!" + ChatColor.RED
						+ " You have been warned " + ChatColor.YELLOW + countwarn + " time(s).");
				player.sendMessage(ChatColor.RED + "If you get warned 3 time, You will be " + ChatColor.DARK_RED
						+ ChatColor.BOLD + "BANNED.");
			}

			Rank.setDisplay(player);
			event.setJoinMessage(Prefix.j + player.getDisplayName());

		}

		String evs = pl.getConfig().getString("event.queuelist." + playerName);
		if (evs == null || evs.isEmpty()) {
			pl.getConfig().set("event.queuelist." + playerName, false);
			pl.saveConfig();
		}

		if (StockInt.LoginFeature == true) {
			StockInt.blockLogin.add(playerName);
			player.setGameMode(GameMode.SPECTATOR);
		}

		try {
			playerData.set("login.count", 0);
			playerData.set("WarpState", 0);
			playerData.save(f);
		} catch (IOException e) {
			e.printStackTrace();
			Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
		}

		File tempFile = new File(StockInt.pluginDir + File.separator + "temp.yml");
		FileConfiguration tempData = YamlConfiguration.loadConfiguration(tempFile);
		try {
			tempData.set("chat_last_send." + playerName, null);
			tempData.set("Teleport." + playerName, "None");
			tempData.save(tempFile);
		} catch (IOException e) {
			e.printStackTrace();
			Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
		}

		player.sendMessage("");
		String version = pl.getDescription().getVersion();
		player.sendMessage(ChatColor.BOLD + "SMDMain's Patch Version: " + version);
		player.sendMessage("");

		AFK.AFKCount.put(playerName, (long) 0);


		String spawn = pl.getConfig().getString("spawn");
		if (spawn != null && StockInt.spawnOnJoin == true) {
			Double x = pl.getConfig().getDouble("spawn" + "." + "spawn" + ".x");
			Double y = pl.getConfig().getDouble("spawn" + "." + "spawn" + ".y");
			Double z = pl.getConfig().getDouble("spawn" + "." + "spawn" + ".z");
			float yaw = (float) pl.getConfig().getDouble("spawn" + "." + "spawn" + ".yaw");
			float pitch = (float) pl.getConfig().getDouble("spawn" + "." + "spawn" + ".pitch");
			String world = pl.getConfig().getString("spawn" + "." + "spawn" + ".world");
			World p = Bukkit.getWorld(world);
			if (p == null) {
				player.sendMessage(Prefix.portal + "Spawn location not found! (Wrong world)");
				Function.no(player);
			}
			Location loc = new Location(p, x, y, z);
			loc.setPitch(pitch);
			loc.setYaw(yaw);
			player.teleport(loc);
			player.sendMessage(Prefix.portal + "Teleported to " + ChatColor.YELLOW + "Spawn" + ChatColor.GRAY + ".");

			Function.egg(player, 0);
		}

		if (Bukkit.getServer().getOnlinePlayers().size() == 1) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb fill pause");
			player.sendMessage("");
			player.sendMessage("Because you're the first player since last player offline.");
			player.sendMessage("The server need to load all chunks data to make your gameplay smooth.");
			player.sendMessage(
					"This will cause " + ChatColor.GOLD + ChatColor.BOLD + "A LAG" + ChatColor.RESET + " for a while");
			player.sendMessage("");
		}
	}

	@EventHandler
	public void onPlayerLeft(PlayerQuitEvent event) {
		
		Player player = event.getPlayer();
		
		if (StockInt.voteToRestart.contains(player.getName())) StockInt.voteToRestart.remove(player.getName());
		
		String playerName = player.getName();
		File userdata = new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		
		event.setQuitMessage(Prefix.l + player.getDisplayName());

		int g = 0;
		GameMode gm = player.getGameMode();
		if (gm == GameMode.CREATIVE)
			g = 1;
		else if (gm == GameMode.ADVENTURE)
			g = 2;
		else if (gm == GameMode.SPECTATOR)
			g = 3;

		File tempFile = new File(StockInt.pluginDir + File.separator + "temp.yml");
		FileConfiguration tempData = YamlConfiguration.loadConfiguration(tempFile);
		try {
			tempData.set("chat_last_send." + playerName, null);
			tempData.set("Teleport." + playerName, "None");
			tempData.save(tempFile);
		} catch (IOException e) {
			e.printStackTrace();
			Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
		}

		pl.getConfig().set("event.queuelist." + playerName, false);
		pl.getConfig().set("gamemode." + playerName, g);
		pl.saveConfig();

		if (StockInt.LoginFeature == true) {
			StockInt.blockLogin.remove(playerName);
			player.setGameMode(GameMode.SPECTATOR);
		}
		
		if (AFK.AFKListName.contains(playerName)) {
			AFK.noLongerAFK(player);	
		}
		
		if (Bukkit.getServer().getOnlinePlayers().size() <= 1) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "save-all");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb fill pause");
		}
	}

}
