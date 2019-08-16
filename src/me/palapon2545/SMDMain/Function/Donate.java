package me.palapon2545.SMDMain.Function;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.palapon2545.SMDMain.Library.StockInt;

public class Donate {
	
	public static long money;
	
	public static void displayMessage(Player player) {
		player.sendMessage("Thank you for " + ChatColor.GOLD + ChatColor.BOLD + "participating" + ChatColor.RESET + "on " + ChatColor.GREEN + "donating :)");
		player.sendMessage("I'm feel glad on your kindness " + ChatColor.LIGHT_PURPLE + "<3");
		player.sendMessage("");
		player.sendMessage("You can donate to support this server by:");
		player.sendMessage(ChatColor.GOLD + "-" + ChatColor.WHITE + ChatColor.BOLD + " True" + ChatColor.GOLD + ChatColor.BOLD + "Wallet" + ChatColor.RESET + ": " + ChatColor.AQUA + "090-8508007");
		player.sendMessage(ChatColor.GOLD + "-" + ChatColor.WHITE + ChatColor.BOLD + " Prompt" + ChatColor.BLUE + ChatColor.BOLD + "Pay" + ChatColor.RESET + ": " + ChatColor.AQUA + "090-8508007");
		player.sendMessage(ChatColor.GOLD + "-" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + " SCB Bank" + ChatColor.RESET + ": " + ChatColor.AQUA + "551-442288-3 " + ChatColor.WHITE + "(นายพลภณ สุนทรภาส)");
		player.sendMessage("");
		
		long a = StockInt.moneyDonated;
		long b = StockInt.moneyTargeted;

		player.sendMessage("Now we got " + ChatColor.AQUA + ChatColor.BOLD + a + ChatColor.RESET + " from our target " + ChatColor.GOLD + ChatColor.BOLD + b + ChatColor.RESET + ".");
		if (a/b >= 1) {
			player.sendMessage("Thank you everyone for donating");
		} else {
			player.sendMessage("We need more " + ChatColor.LIGHT_PURPLE + (b-a) + ChatColor.RESET + " to reach the target!"); 
		}
		
		player.sendMessage("⬛⬛⬛⬛⬛⬛⬛⬛");
		
		player.sendMessage("*If you have donated, please send a transcript to Facebook: ");
		player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Palapon Soontornpas" + ChatColor.ITALIC + "( https://m.me/p0ndja )"); 
		
		
		
	}

}
