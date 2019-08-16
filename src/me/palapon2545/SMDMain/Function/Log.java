package me.palapon2545.SMDMain.Function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.palapon2545.SMDMain.Library.Prefix;
import me.palapon2545.SMDMain.Library.StockInt;

public class Log extends JavaPlugin {

	static String newLine = System.getProperty("line.separator");

	public static void logData(String logTopic, Player player, String dataToLog) {
		checkLogExist(logTopic, player);

		String playerName = player.getName();
		File logDir = new File(StockInt.pluginDir, File.separator + "PlayerDatabase/" + playerName);
		File logFile = new File(logDir, File.separator + logTopic + ".log");

		try {
			FileWriter fwriter = new FileWriter(logFile.toString(), true);
			fwriter.write(dataToLog + newLine);
			fwriter.close();
		} catch (IOException e) {
			Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
			e.printStackTrace();
		}

	}

	public static void checkLogExist(String logTopic, Player player) {
		String playerName = player.getName();
		File logDir = new File("plugins/SMDMain/", File.separator + "PlayerDatabase/" + playerName);
		File logFile = new File(logDir, File.separator + logTopic + ".log");
		if (logFile.exists() == false) {
			try {
				FileWriter fwriter = new FileWriter(logFile.toString(), true);
				fwriter.write("#This is a '" + logTopic + "' log file." + newLine);
				fwriter.close();
			} catch (IOException e) {
				Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
				e.printStackTrace();
			}
		} else {
			// File already created
		}

	}

	public static void readLog(String logTopic, Player player) {
		checkLogExist(logTopic, player);

		String playerName = player.getName();
		File logDir = new File("plugins/SMDMain/", File.separator + "PlayerDatabase/" + playerName);
		File logFile = new File(logDir, File.separator + logTopic + ".log");
		try {
			player.sendMessage(Prefix.database + "loading file " + logTopic + ".log");
			FileInputStream fstream = new FileInputStream(logFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				if (!strLine.startsWith("#"))
					player.sendMessage(strLine);
			}
			fstream.close();
		} catch (Exception e) {
			e.printStackTrace();
			Bukkit.broadcastMessage(Prefix.database + Prefix.database_error);
			Bukkit.broadcastMessage(Prefix.database + e.getMessage());
		}
	}
}
