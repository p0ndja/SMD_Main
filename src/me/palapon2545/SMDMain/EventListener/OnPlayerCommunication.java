package me.palapon2545.SMDMain.EventListener;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.palapon2545.SMDMain.Core.AFK;
import me.palapon2545.SMDMain.Core.Rank;
import me.palapon2545.SMDMain.Function.ActionBarAPI_api;
import me.palapon2545.SMDMain.Function.Function;
import me.palapon2545.SMDMain.Library.Prefix;
import me.palapon2545.SMDMain.Library.StockInt;
import me.palapon2545.SMDMain.Main.pluginMain;

public class OnPlayerCommunication implements Listener {

	pluginMain pl;

	public OnPlayerCommunication(pluginMain pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		String message = event.getMessage();
		String message2 = message.replaceAll("%", "%%");
		String messagem = message2.replaceAll("&", Prefix.Ampersand);
		String message1 = " " + messagem;
		Player player = event.getPlayer();
		String playerName = player.getName();
		File userdata = new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		String rank = playerData.getString("rank");
		String muteis = playerData.getString("mute.is");
		String mutere = playerData.getString("mute.reason");
		if (muteis.equalsIgnoreCase("true")) {
			player.sendMessage(ChatColor.BLUE + "Chat> " + ChatColor.GRAY + "You have been muted.");
			player.sendMessage(ChatColor.BLUE + "Chat> " + ChatColor.YELLOW + "Reason: " + ChatColor.GRAY + mutere);
			Function.no(player);
			event.setCancelled(true);
		} else if (StockInt.blockLogin.contains(playerName)) {
			event.setCancelled(true);
		} else {
			for (Player p : Bukkit.getOnlinePlayers()) {
				Function.egg(p, 0.5f);
			}
			
			String RankDisplay;
			if (rank.equalsIgnoreCase("default"))
				RankDisplay = Rank.Default;
			else if (rank.equalsIgnoreCase("staff"))
				RankDisplay = Rank.Staff;
			else if (rank.equalsIgnoreCase("vip"))
				RankDisplay = Rank.Vip;
			else if (rank.equalsIgnoreCase("helper"))
				RankDisplay = Rank.Helper;
			else if (rank.equalsIgnoreCase("admin"))
				RankDisplay = Rank.Admin;
			else if (rank.equalsIgnoreCase("owner"))
				RankDisplay = Rank.Owner;
			else if (rank.equalsIgnoreCase("builder"))
				RankDisplay = Rank.Builder;
			else
				RankDisplay = Rank.Default;

			event.setFormat(RankDisplay + playerName + ChatColor.WHITE + message1);
		}

		AFK.noLongerAFK(player);
		
	}

	@EventHandler
	public void playerPerformedCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		//Bukkit.broadcastMessage(" " + playerName + " run_command " + event.getMessage());
		File userdata = new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + playerName);
		File f = new File(userdata, File.separator + "config.yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		String freeze = playerData.getString("freeze");
		if (freeze.equalsIgnoreCase("true")) {
			event.setCancelled(true);
			ActionBarAPI_api.send(player, ChatColor.AQUA + "You're " + ChatColor.BOLD + "FREEZING");
		}
		String[] i = event.getMessage().split(" ");
		if (StockInt.blockLogin.contains(playerName)) {
			if (i[0].equalsIgnoreCase("/qwerty") || i[0].equalsIgnoreCase("/l") || i[0].equalsIgnoreCase("/login")
					|| i[0].equalsIgnoreCase("/reg") || i[0].equalsIgnoreCase("/register")) {
				// NOTHING
			} else {
				event.setCancelled(true);
			}
		}

		AFK.noLongerAFK(player);

	}
}
