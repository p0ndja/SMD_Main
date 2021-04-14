package me.palapon2545.SMDMain.Core;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Money extends JavaPlugin {

	static String coin = " Coins ";

	public static boolean tranfer(Player payer, Player receiver, long amount) {		
		long payerMoney = Long.parseLong(PlayerDatabase.get(payer, "money").toString());
		long receiverMoney = Long.parseLong(PlayerDatabase.get(receiver, "money").toString());
		if (payerMoney < amount) return false;
		PlayerDatabase.set(payer, "money", payerMoney - amount);
		PlayerDatabase.set(receiver, "money", receiverMoney + amount);
		return true;
	}

	public static boolean take(Player payer, long amount) {
		long payerMoney = Long.parseLong(PlayerDatabase.get(payer, "money").toString());
		if (payerMoney < amount)
			return false;
		PlayerDatabase.set(payer, "money", payerMoney - amount);
		return true;
	}

	public static Long get(Player caller) {
		return Long.parseLong(PlayerDatabase.get(caller, "money").toString());
	}

	public static boolean give(Player receiver, long amount) {
		long payerMoney = Long.parseLong(PlayerDatabase.get(receiver, "money").toString());
		PlayerDatabase.set(receiver, "money", payerMoney + amount);
		return true;
	}

}
