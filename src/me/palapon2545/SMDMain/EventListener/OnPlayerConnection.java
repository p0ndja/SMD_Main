package me.palapon2545.SMDMain.EventListener;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.palapon2545.SMDMain.Function.Function;
import me.palapon2545.SMDMain.Function.Sound18to113;
import me.palapon2545.SMDMain.Function.Sound18to19;
import me.palapon2545.SMDMain.Library.Prefix;
import me.palapon2545.SMDMain.Library.Rank;
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
				File userfiles = new File(StockInt.pluginDir + File.separator + "/PlayerDatabase/" + playerName + "/HomeDatabase");
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
			Boolean invi = playerData.getBoolean("Invisible");
			if (invi) {
				player.sendMessage(Prefix.server + "You're now " + ChatColor.AQUA + "invisible.");
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.hasPermission("main.seeinvisible") || p.isOp() || p.hasPermission("main.*")) {
						p.showPlayer(player);
					} else {
						p.hidePlayer(player);
					}
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
			String rank = playerData.getString("rank");
			int countwarn = playerData.getInt("warn");
			if (countwarn > 0) {
				player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "ALERT!" + ChatColor.RED
						+ " You have been warned " + ChatColor.YELLOW + countwarn + " time(s).");
				player.sendMessage(ChatColor.RED + "If you get warned 3 time, You will be " + ChatColor.DARK_RED
						+ ChatColor.BOLD + "BANNED.");
			}

			String RankDisplay;
			if (rank.equalsIgnoreCase("default")) {
				RankDisplay = Rank.Default;
			} else if (rank.equalsIgnoreCase("staff")) {
				RankDisplay = Rank.Staff;
			} else if (rank.equalsIgnoreCase("vip")) {
				RankDisplay = Rank.Vip;
			} else if (rank.equalsIgnoreCase("helper")) {
				RankDisplay = Rank.Helper;
			} else if (rank.equalsIgnoreCase("admin")) {
				RankDisplay = Rank.Admin;
			} else if (rank.equalsIgnoreCase("owner")) {
				RankDisplay = Rank.Owner;
			} else if (rank.equalsIgnoreCase("builder")) {
				RankDisplay = Rank.Builder;
			} else {
				RankDisplay = Rank.Default;
			}
			player.setDisplayName(RankDisplay + playerName);
			player.setPlayerListName(RankDisplay + playerName);
			event.setJoinMessage(Prefix.j + RankDisplay + playerName);

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

		try {
			tempData.createSection("afk_level");
			tempData.set("afk_level." + playerName, 0);
			tempData.save(tempFile);
		} catch (IOException e) {
			e.printStackTrace();
			Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
		}

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

		//StockInt.pleaseDropItemBeforeChat.add(playerName);

	}

	@EventHandler
	public void onPlayerLeft(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		File userdata = new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		String rank = playerData.getString("rank");
		String RankDisplay;
		if (rank.equalsIgnoreCase("default")) {
			RankDisplay = Rank.Default;
		} else if (rank.equalsIgnoreCase("staff")) {
			RankDisplay = Rank.Staff;
		} else if (rank.equalsIgnoreCase("vip")) {
			RankDisplay = Rank.Vip;
		} else if (rank.equalsIgnoreCase("helper")) {
			RankDisplay = Rank.Helper;
		} else if (rank.equalsIgnoreCase("admin")) {
			RankDisplay = Rank.Admin;
		} else if (rank.equalsIgnoreCase("owner")) {
			RankDisplay = Rank.Owner;
		} else if (rank.equalsIgnoreCase("builder")) {
			RankDisplay = Rank.Builder;
		} else {
			RankDisplay = Rank.Default;
		}
		event.setQuitMessage(Prefix.l + RankDisplay + playerName);

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

		if (StockInt.afkListName.contains(playerName)) {
			try {
				tempData.set("afk_level." + playerName, -1);
				tempData.save(tempFile);
			} catch (IOException e) {
				e.printStackTrace();
				Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
			}
		} else {
			try {
				tempData.set("afk_level." + playerName, 0);
				tempData.save(tempFile);
			} catch (IOException e) {
				e.printStackTrace();
				Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
			}
		}

		int n = Bukkit.getServer().getOnlinePlayers().size();
		if (n == 0 || n < 0)
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "save-all");
		else
			return;

		if (StockInt.pleaseDropItemBeforeChat.contains(playerName))
			StockInt.pleaseDropItemBeforeChat.remove(playerName);
	}

}
