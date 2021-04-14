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
import org.bukkit.inventory.ItemStack;

import me.palapon2545.SMDMain.Core.AFK;
import me.palapon2545.SMDMain.Function.ActionBarAPI_api;
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

		if (event.getPlayer().isGliding())
			event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(0.66f));
		

		Player player = event.getPlayer();
		String playerName = player.getName();
		File userdata = new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		String freeze = playerData.getString("freeze");
		if (freeze.equalsIgnoreCase("true")) {
			event.setCancelled(true);
			ActionBarAPI_api.send(player, ChatColor.AQUA + "You're " + ChatColor.BOLD + "FREEZING");
			player.setAllowFlight(true);
		}
		if (StockInt.blockLogin.contains(playerName)) {
			event.setCancelled(true);
		}

		AFK.noLongerAFK(player);

	}

	@EventHandler
	public void onPlayerCreatePortal(PortalCreateEvent event) {
		// if(StockInt.privateServerPondJa)event.setCancelled(false);
	}

	@EventHandler
	public void onPlayerBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		File userdata = new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		String freeze = playerData.getString("freeze");
		if (freeze.equalsIgnoreCase("true")) {
			event.setCancelled(true);
			ActionBarAPI_api.send(player, ChatColor.AQUA + "You're " + ChatColor.BOLD + "FREEZING");
		}
		if (StockInt.blockLogin.contains(playerName)) {
			event.setCancelled(true);
		}

		AFK.noLongerAFK(player);

	}

	@EventHandler
	public void onPlayerPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		File userdata = new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		String freeze = playerData.getString("freeze");
		if (StockInt.blockLogin.contains(playerName)) {
			event.setCancelled(true);
		}
		if (freeze.equalsIgnoreCase("true")) {
			event.setCancelled(true);
			ActionBarAPI_api.send(player, ChatColor.AQUA + "You're " + ChatColor.BOLD + "FREEZING");
		}

		AFK.noLongerAFK(player);

	}

}
