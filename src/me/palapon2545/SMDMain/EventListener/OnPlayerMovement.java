package me.palapon2545.SMDMain.EventListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.world.PortalCreateEvent;

import me.palapon2545.SMDMain.Function.ActionBarAPI;
import me.palapon2545.SMDMain.Function.Blockto113;
import me.palapon2545.SMDMain.Library.Prefix;
import me.palapon2545.SMDMain.Library.StockInt;
import me.palapon2545.SMDMain.Main.pluginMain;

@SuppressWarnings("deprecation")
public class OnPlayerMovement implements Listener {

	pluginMain pl;

	public OnPlayerMovement(pluginMain pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (StockInt.pleaseDropItemBeforeChat.contains(event.getPlayer().getName())) {
			event.getPlayer().sendMessage("You're now able to play server!");
			StockInt.pleaseDropItemBeforeChat.remove(event.getPlayer().getName());
		}
		if (StockInt.blockLogin.contains(event.getPlayer().getName()))
			event.setCancelled(true);

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (StockInt.blockLogin.contains(event.getPlayer().getName())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		File userdata = new File(pl.getDataFolder(), File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		String freeze = playerData.getString("freeze");
		if (freeze.equalsIgnoreCase("true")) {
			event.setCancelled(true);
			ActionBarAPI.send(player, ChatColor.AQUA + "You're " + ChatColor.BOLD + "FREEZING");
			player.setAllowFlight(true);
		}
		if (StockInt.blockLogin.contains(playerName)) {
			event.setCancelled(true);
		}

		if (StockInt.afkListName.contains(playerName)) {
			File tempFile = new File(pl.getDataFolder() + File.separator + "temp.yml");
			FileConfiguration tempData = YamlConfiguration.loadConfiguration(tempFile);
			try {
				tempData.set("afk_level." + playerName, -1);
				tempData.save(tempFile);
				pl.noLongerAFKLevel(player);
			} catch (IOException e) {
				e.printStackTrace();
				Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
			}
		}
		File tempFile = new File(pl.getDataFolder() + File.separator + "temp.yml");
		FileConfiguration tempData = YamlConfiguration.loadConfiguration(tempFile);
		try {
			tempData.set("afk_level." + playerName, 0);
			tempData.save(tempFile);
		} catch (IOException e) {
			e.printStackTrace();
			Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
		}
	}

	@EventHandler
	public void onPortalCreate(PortalCreateEvent event) {
		// if(StockInt.privateServerPondJa)event.setCancelled(false);
	}

	@EventHandler
	public void onPlayerBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		File userdata = new File(pl.getDataFolder(), File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		String freeze = playerData.getString("freeze");
		if (freeze.equalsIgnoreCase("true")) {
			event.setCancelled(true);
			ActionBarAPI.send(player, ChatColor.AQUA + "You're " + ChatColor.BOLD + "FREEZING");
		}
		if (StockInt.blockLogin.contains(playerName)) {
			event.setCancelled(true);
		}
		if (StockInt.afkListName.contains(playerName)) {
			File tempFile = new File(pl.getDataFolder() + File.separator + "temp.yml");
			FileConfiguration tempData = YamlConfiguration.loadConfiguration(tempFile);
			try {
				tempData.set("afk_level." + playerName, -1);
				tempData.save(tempFile);
			} catch (IOException e) {
				e.printStackTrace();
				Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
			}
		}
		File tempFile = new File(pl.getDataFolder() + File.separator + "temp.yml");
		FileConfiguration tempData = YamlConfiguration.loadConfiguration(tempFile);
		try {
			tempData.set("afk_level." + playerName, 0);
			tempData.save(tempFile);
		} catch (IOException e) {
			e.printStackTrace();
			Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {

		Date date = new Date();
		String Time = new SimpleDateFormat("HH:mm:ss").format(date);
		String month = new SimpleDateFormat("dd/MM/YYYY").format(date);

		Player player = event.getEntity();
		Location loc = player.getLocation();
		Block b = loc.getBlock();
		if (b.getType() == Material.AIR) {
			b.setType(Blockto113.SIGN_POST.bukkitblock());
			Sign a = (Sign) b.getState();
			a.setLine(0, ChatColor.BOLD + "--[RIP]--");
			a.setLine(1, player.getName());
			a.setLine(2, month);
			a.setLine(3, Time);
			a.update();
		}

		player.chat("Oh NO! I'm dead at " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ());
	}

	@EventHandler
	public void onPlayerPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		File userdata = new File(pl.getDataFolder(), File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		String freeze = playerData.getString("freeze");
		if (StockInt.blockLogin.contains(playerName)) {
			event.setCancelled(true);
		}
		if (freeze.equalsIgnoreCase("true")) {
			event.setCancelled(true);
			ActionBarAPI.send(player, ChatColor.AQUA + "You're " + ChatColor.BOLD + "FREEZING");
		}
		if (StockInt.afkListName.contains(playerName)) {
			File tempFile = new File(pl.getDataFolder() + File.separator + "temp.yml");
			FileConfiguration tempData = YamlConfiguration.loadConfiguration(tempFile);
			try {
				tempData.set("afk_level." + playerName, -1);
				tempData.save(tempFile);
			} catch (IOException e) {
				e.printStackTrace();
				Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
			}
		}
		File tempFile = new File(pl.getDataFolder() + File.separator + "temp.yml");
		FileConfiguration tempData = YamlConfiguration.loadConfiguration(tempFile);
		try {
			tempData.set("afk_level." + playerName, 0);
			tempData.save(tempFile);
		} catch (IOException e) {
			e.printStackTrace();
			Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
		}
	}

}
